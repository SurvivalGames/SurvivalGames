/**
 * Name: SGArena.java Edited: 7 December 2013
 *
 * @version 1.0.0
 */

package co.q64.paradisesurvivalgames.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;

import co.q64.paradisesurvivalgames.locale.I18N;
import co.q64.paradisesurvivalgames.managers.SGApi;
import co.q64.paradisesurvivalgames.multiworld.SGWorld;
import co.q64.paradisesurvivalgames.net.SendWebsocketData;
import co.q64.paradisesurvivalgames.rollback.ChangedBlock;
import co.q64.paradisesurvivalgames.util.EconUtil;
import co.q64.paradisesurvivalgames.util.FireworkUtil;

public class SGArena {

	/**
	 * Name: ArenaState.java Edited: 8 December 2013
	 *
	 * @version 1.0.0
	 */
	public enum ArenaState {

		DEATHMATCH("Deathmatch", "DEATHMATCH"), IN_GAME("In Game", "IN_GAME"), POST_GAME("Restarting", "POST_GAME"), PRE_COUNTDOWN("Starting soon", "PRE_COUNTDOWN"), STARTING_COUNTDOWN("Starting Countdown", "STARTING_COUNTDOWN"), WAITING_FOR_PLAYERS("Waiting for players", "WAITING_FOR_PLAYERS");

		String name;
		String trueName;

		ArenaState(String s, String t) {
			name = s;
			trueName = t;
		}

		public String getTrueName() {
			return trueName;
		}

		public boolean isConvertable(SGArena arena, ArenaState a) {
			if (a.equals(WAITING_FOR_PLAYERS)) {
				if (arena.getState().equals(WAITING_FOR_PLAYERS)) {
					return false;
				}
			}

			if (a.equals(STARTING_COUNTDOWN)) {
				if (arena.getState().equals(WAITING_FOR_PLAYERS)) {
					return false;
				} else if (arena.getState().equals(STARTING_COUNTDOWN)) {
					return false;
				}
			}

			if (a.equals(DEATHMATCH)) {
				if (arena.getState().equals(WAITING_FOR_PLAYERS)) {
					return false;
				} else if (arena.getState().equals(STARTING_COUNTDOWN)) {
					return false;
				} else if (arena.getState().equals(IN_GAME)) {
					return false;
				} else if (arena.getState().equals(DEATHMATCH)) {
					return false;
				}
			}

			if (a.equals(IN_GAME)) {
				if (!arena.getState().equals(POST_GAME)) {
					return false;
				}
			}

			return true;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	private List<ChangedBlock> changedBlocks = new ArrayList<>();

	private boolean countdown = false;
	private SGWorld currentMap;

	private ArenaState currentState;
	private int dead;

	private List<DoubleChest> dLooted = new ArrayList<>();
	private boolean firstBlood = false;
	private int id = 0;

	private Map<String, Integer> kills = new HashMap<>();
	private Location lobby = null;

	private List<Chest> looted = new ArrayList<>();

	private int maxPlayers;
	private int minPlayers;
	private final List<UUID> players = new CopyOnWriteArrayList<>();

	private final List<UUID> spectators = new CopyOnWriteArrayList<>();

	private List<String> voted = new ArrayList<>();

	private Map<MapHash, Integer> votes = new HashMap<>();

	public SGArena() {
	}

	public void addKill(Player p) {
		if (getKills().get(p.getName()) == null) {
			getKills().put(p.getName(), 0);
		}
		getKills().put(p.getName(), getKills().get(p.getName()) + 1);

		PlayerData data = SGApi.getPlugin().getPlayerData(p);
		data.addKill();
		SGApi.getPlugin().setPlayerData(data);
		p.sendMessage(ChatColor.GOLD + "Plus 10 points!");
		SGApi.getPlugin().getTracker().trackEvent("Player Kill", p.getName());
		EconUtil.addPoints(p, 10);
	}

	/**
	 * Sends all the players a message
	 *
	 * @param message The message to send, do not include prefix
	 */
	public void broadcast(String message) {
		for (UUID s : getPlayers()) {
			Player p = Bukkit.getServer().getPlayer(s);
			if (p != null) {
				p.sendMessage(SGApi.getArenaManager().getPrefix() + message);
			}
		}

		for (UUID s : getSpectators()) {
			Player p = Bukkit.getServer().getPlayer(s);
			if (p != null) {
				p.sendMessage(SGApi.getArenaManager().getPrefix() + message);
			}
		}
	}

	public void broadcastNoPrefix(String message) {
		for (UUID s : getPlayers()) {
			Player p = Bukkit.getServer().getPlayer(s);
			if (p != null) {
				p.sendMessage(message);
			}
		}

		for (UUID s : getSpectators()) {
			Player p = Bukkit.getServer().getPlayer(s);
			if (p != null) {
				p.sendMessage(message);
			}
		}
	}

	public void broadcastVotes() {
		List<String> voteStrings = new ArrayList<String>();
		for (Map.Entry<MapHash, Integer> entry : getVotes().entrySet()) {
			voteStrings.add(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + entry.getKey().getId() + ": " + ChatColor.AQUA.toString() + entry.getKey().getWorld().getDisplayName() + ChatColor.DARK_RED + ChatColor.BOLD + " --> " + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + entry.getValue());
		}
		Collections.sort(voteStrings);
		for (String s : voteStrings) {
			broadcast(s);
		}
	}

	/**
	 * Constructs a new arena based off of a Location and an ID
	 *
	 * @param id The ID the arena will have
	 */
	public void createArena(int id) {
		this.setId(id);
	}

	public void death(Player p) {
		for (UUID s : getPlayers()) {
			Player player = Bukkit.getPlayer(s);
			SendWebsocketData.updateArenaStatusForPlayer(player);
		}
		for (UUID s : getSpectators()) {
			Player player = Bukkit.getPlayer(s);
			SendWebsocketData.updateArenaStatusForPlayer(player);
		}
		setDead(getDead() + 1);
		getPlayers().remove(p.getUniqueId());
		getSpectators().add(p.getUniqueId());
		SGApi.getPlugin().getTracker().trackEvent("Player Death", p.getName());
		if (getPlayers().size() <= 1)
			end();
	}

	public void deathAndLeave(Player p) {
		setDead(getDead() + 1);
		getPlayers().remove(p.getUniqueId());
		SGApi.getArenaManager().removePlayer(p);
		SGApi.getPlugin().getTracker().trackEvent("Player Death", p.getName());
		if (getPlayers().size() <= 1)
			end();
	}

	public void deathWithQuit(Player p) {
		setDead(getDead() + 1);
		getPlayers().remove(p.getUniqueId());
		if (getPlayers().size() <= 1)
			end();
	}

	/**
	 * Puts the arena into deathmatch
	 */
	public void dm() {
		int i = 0;
		for (UUID s : getPlayers()) {
			Player p;
			if ((p = Bukkit.getServer().getPlayer(s)) != null) {
				p.teleport(getCurrentMap().locs.get(i));
				i++;
			}
		}
	}

	/**
	 * Ends the arena
	 */
	public void end() {
		if (SGApi.getPlugin().getPluginConfig().getUseServers()) {
			for (UUID s : getPlayers()) {
				Player p = Bukkit.getPlayer(s);

				SendWebsocketData.updateArenaStatusForPlayer(p);

			}
			for (UUID s : getSpectators()) {
				Player p = Bukkit.getPlayer(s);

				SendWebsocketData.updateArenaStatusForPlayer(p);

			}
		}
		if (getPlayers().size() == 1) {
			broadcast(I18N.getLocaleString("END") + " " + getPlayers().get(0));
			Player winner = Bukkit.getPlayer(getPlayers().get(0));
			PlayerData data = SGApi.getPlugin().getPlayerData(winner);
			data.addWin();
			SGApi.getPlugin().setPlayerData(data);
			SGApi.getPlugin().getTracker().trackEvent("Player Win", winner.getName());
			EconUtil.addPoints(winner, 100);
			winner.sendMessage(ChatColor.GOLD + "Plus 100 coins!");

			FireworkUtil.getCircleUtil().playFireworkCircle(winner, FireworkEffect.builder().with(Type.BALL).withColor(Color.RED).withColor(Color.GREEN).withColor(Color.BLUE).withColor(Color.YELLOW).withTrail().build(), 10, 10);
		} else {
			broadcast(SGApi.getArenaManager().getPrefix() + I18N.getLocaleString("ARENA_END"));
		}

		Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {

			@Override
			public void run() {
				for (UUID s : getPlayers()) {
					SGApi.getArenaManager().removePlayer(Bukkit.getPlayer(s));
				}
				for (UUID s : getSpectators()) {
					SGApi.getArenaManager().removePlayer(Bukkit.getPlayer(s));
				}
				getVoted().clear();
				getVotes().clear();

				SGApi.getRollbackManager().rollbackArena(getThis());

			}
		}, 200L);

		//Auto restarts after rollback
	}

	public World getArenaWorld() {
		return getCurrentMap().getWorld();
	}

	public List<ChangedBlock> getChangedBlocks() {
		return changedBlocks;
	}

	public SGWorld getCurrentMap() {
		return currentMap;
	}

	public ArenaState getCurrentState() {
		return currentState;
	}

	public int getDead() {
		return dead;
	}

	public List<DoubleChest> getdLooted() {
		return dLooted;
	}

	/**
	 * Gets the ID of the arena
	 *
	 * @return The ID of the arena
	 */
	public int getId() {
		return this.id;
	}

	public Map<String, Integer> getKills() {
		return kills;
	}

	public Location getLobby() {
		return lobby;
	}

	public List<Chest> getLooted() {
		return looted;
	}

	/**
	 * Gets the max number of players the arena will hold
	 *
	 * @return Number of players
	 */
	public int getMaxPlayers() {
		return maxPlayers;
	}

	/**
	 * Gets the min number of players for the arena to start
	 *
	 * @return Number of players
	 */
	public int getMinPlayers() {
		return minPlayers;
	}

	/**
	 * Gets the list of players in the arena
	 *
	 * @return List of players in the arena
	 */
	public List<UUID> getPlayers() {
		return this.players;
	}

	public List<UUID> getSpectators() {
		return spectators;
	}

	/**
	 * Gets the current state of the arena
	 *
	 * @return The current state
	 */
	public ArenaState getState() {
		return getCurrentState();
	}

	SGArena getThis() {
		return this;
	}

	public List<String> getVoted() {
		return voted;
	}

	public Map<MapHash, Integer> getVotes() {
		return votes;
	}

	/**
	 * Makes sure that the fields aren't null on startup
	 *
	 * @param lob        The lobby spawn
	 * @param maxPlayers The max players for the arena
	 * @param minPlayers The min players needed for the game to start
	 */
	public void initialize(Location lob, int maxPlayers, int minPlayers) {
		this.setLobby(lob);
		this.setMaxPlayers(maxPlayers);
		this.setMinPlayers(minPlayers);

		restart();
	}

	public boolean isCountdown() {
		return countdown;
	}

	public boolean isFirstBlood() {
		return firstBlood;
	}

	public void restart() {
		this.getPlayers().clear();
		this.getSpectators().clear();
		this.getChangedBlocks().clear();
		this.getLooted().clear();
		this.getdLooted().clear();
		this.getVoted().clear();
		this.getVotes().clear();
		this.setDead(0);
		this.getKills().clear();
		this.setCountdown(false);
		this.setFirstBlood(false);

		SGApi.getTimeManager(this).forceReset();

		this.setState(ArenaState.WAITING_FOR_PLAYERS);
	}

	public void setChangedBlocks(final List<ChangedBlock> changedBlocks) {
		this.changedBlocks = changedBlocks;
	}

	public void setCountdown(final boolean countdown) {
		this.countdown = countdown;
	}

	public void setCurrentMap(final SGWorld currentMap) {
		this.currentMap = currentMap;
	}

	public void setCurrentState(final ArenaState currentState) {
		this.currentState = currentState;
	}

	public void setDead(final int dead) {
		this.dead = dead;
	}

	public void setdLooted(final List<DoubleChest> dLooted) {
		this.dLooted = dLooted;
	}

	public void setFirstBlood(final boolean firstBlood) {
		this.firstBlood = firstBlood;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public void setKills(final Map<String, Integer> kills) {
		this.kills = kills;
	}

	public void setLobby(final Location lobby) {
		this.lobby = lobby;
	}

	public void setLooted(final List<Chest> looted) {
		this.looted = looted;
	}

	public void setMaxPlayers(final int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public void setMinPlayers(final int minPlayers) {
		this.minPlayers = minPlayers;
	}

	/**
	 * Sets the state of the SG arena
	 *
	 * @param state - The new state
	 */
	public void setState(ArenaState state) {
		setCurrentState(state);
	}

	public void setVoted(final List<String> voted) {
		this.voted = voted;
	}

	public void setVotes(final Map<MapHash, Integer> votes) {
		this.votes = votes;
	}

	@Override
	public String toString() {
		return "SGArena.java - Id: " + this.getId() + " State: " + this.getState() + " " + "Players: " + this.getPlayers();
	}

	/**
	 * Makes a player vote
	 *
	 * @param p the voter
	 * @param i the map number
	 */
	public void vote(Player p, int i) {
		if (getCurrentState() != ArenaState.WAITING_FOR_PLAYERS && getCurrentState() != ArenaState.PRE_COUNTDOWN) {
			p.sendMessage(SGApi.getArenaManager().getError() + I18N.getLocaleString("NOT_VOTING"));
			return;
		}

		if (getVoted().contains(p.getName())) {
			p.sendMessage(ChatColor.RED + "You have alredy voted!");
			return;
		}

		MapHash voteWorld = null;
		for (Map.Entry<MapHash, Integer> e : getVotes().entrySet()) {
			if (e.getKey().getId() == i) {
				Bukkit.getLogger().info("Attempting to vote for world: " + e.getKey().getWorld() + " with a value of:" + " " + e.getValue() + " and an input number of: " + i);
				voteWorld = e.getKey();
			}
		}
		if (voteWorld == null)
			return;
		getVotes().put(voteWorld, getVotes().get(voteWorld) + 1);
		this.broadcast(ChatColor.GOLD + p.getDisplayName() + " has voted! Use /vote to cast your vote!");
		broadcastVotes();
		getVoted().add(p.getName());
	}

	public void forceStart() {
		SGApi.getTimeManager(this).forceStart();
	}
}
