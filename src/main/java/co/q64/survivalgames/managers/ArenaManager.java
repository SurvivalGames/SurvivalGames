/**
 * Name: ArenaManager.java Edited: 7 December 2013
 *
 * @version 1.0.0
 */

package co.q64.survivalgames.managers;

import co.q64.survivalgames.configs.ArenaConfigTemplate;
import co.q64.survivalgames.configs.ConfigTemplate;
import co.q64.survivalgames.configs.WorldConfigTemplate;
import co.q64.survivalgames.exception.ArenaNotFoundException;
import co.q64.survivalgames.io.DownloadMap;
import co.q64.survivalgames.locale.I18N;
import co.q64.survivalgames.multiworld.SGWorld;
import co.q64.survivalgames.net.SendWebsocketData;
import co.q64.survivalgames.objects.SGArena;
import co.q64.survivalgames.util.PlayerVanishUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ArenaManager {

	private final List<SGArena> arenas = new ArrayList<>();
	private final Map<String, ItemStack[]> armor = new HashMap<>();
	private final Map<String, SGWorld> creators = new HashMap<>();
	private final String error = ChatColor.DARK_AQUA + "[TheSurvivalGames]" + ChatColor.RED;
	private final Map<String, ItemStack[]> inv = new HashMap<>();
	private final Map<String, Location> locs = new HashMap<>();
	private final String prefix = ChatColor.DARK_AQUA + "[TheSurvivalGames]" + ChatColor.GOLD;

	/**
	 * The constructor for a new reference of the singleton
	 */
	public ArenaManager() {
	}

	/**
	 * Adds a player to the specified arena
	 *
	 * @param p The player to be added
	 * @param i The arena ID in which the player will be added to.
	 */
	public void addPlayer(Player p, int i) {
		SGArena a;
		try {
			a = getArena(i);
		} catch (ArenaNotFoundException e) {
			Bukkit.getLogger().severe(e.getMessage());
			return;
		}

		if (isInGame(p)) {
			p.sendMessage(getError() + I18N.getLocaleString("NOT_JOINABLE"));
			return;
		}

		if (!a.getState().equals(SGArena.ArenaState.WAITING_FOR_PLAYERS) && !a.getState().equals(SGArena.ArenaState.PRE_COUNTDOWN)) {
			a.getSpectators().add(p.getUniqueId());
			p.teleport(a.getCurrentMap().locs.get(0));
			p.setGameMode(GameMode.CREATIVE);
			p.setCanPickupItems(false);
			p.setAllowFlight(true);
			p.setFlying(true);
			p.setFoodLevel(20);
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			ItemManager.getInstance().getItem("spec-item").givePlayerItem(p);
			p.setExp(0);
			PlayerVanishUtil.hideAll(p);
			
			return;
		}
		if(a.getPlayers().size() >= a.getMaxPlayers()){
			p.sendMessage("Sorry, but that arena is full!");
			return;
		}
		if (a.getState().equals(SGArena.ArenaState.PRE_COUNTDOWN)) {
			//a.broadcastVotes();
			p.sendMessage(getPrefix() + "Type in /sg vote <ID> to vote for a map.");
			ItemManager.getInstance().getItem("vote-item").givePlayerItem(p);
		}

		if (SGApi.getPlugin().getPluginConfig().getUseServers()) {
			p.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮");
			p.sendMessage(ChatColor.AQUA + "");
			p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Want to here LIVE music, announcers, " + "and sound effects?");
			p.sendMessage(ChatColor.AQUA + "");
			p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Click this link:");
			p.sendMessage(ChatColor.WHITE + "" + ChatColor.UNDERLINE + "http://sg.q64.co/sg/index" + ".html?name=" + p.getName() + "&session=" + SGApi.getPlugin().getPluginConfig().getServerIP());
			p.sendMessage(ChatColor.AQUA + "");
			p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Simply leave your browser window open in the " + "background, turn up your speakers, and we'll do the rest!");
			p.sendMessage(ChatColor.AQUA + "");
			p.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮");

			//TODO Temp
		}

		a.getPlayers().add(p.getUniqueId());
		for (UUID s : a.getPlayers()) {
			Player player = Bukkit.getPlayer(s);
			SendWebsocketData.updateArenaStatusForPlayer(player);
		}
		for (UUID s : a.getSpectators()) {
			Player player = Bukkit.getPlayer(s);
			SendWebsocketData.updateArenaStatusForPlayer(player);
		}
		getInv().put(p.getName(), p.getInventory().getContents());
		getArmor().put(p.getName(), p.getInventory().getArmorContents());

		p.getInventory().setArmorContents(null);
		p.getInventory().clear();
		p.setExp(0);

		getLocs().put(p.getName(), p.getLocation());
		p.teleport(a.getLobby());
		p.setExhaustion(0);
		p.setGameMode(GameMode.SURVIVAL);
		healPlayer(p);
		// Ding!
		for (Player player : SGApi.getPlugin().getServer().getOnlinePlayers()) {
			player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 1);
		}

		SendWebsocketData.stopMusic(p);
		SendWebsocketData.playMusicToPlayer(p, SendWebsocketData.getRandomMusic("lobby-music"));

		if (a.getPlayers().size() == a.getMinPlayers() && !a.isCountdown()) {
			a.setCountdown(true);
			SGApi.getTimeManager(a).countdownLobby(2);
		}
	}

	/**
	 * Creates a lobby
	 */
	public SGArena createLobby(Player p) {
		SGArena a = new SGArena();
		int s = getArenas().size();
		s += 1;
		a.createArena(s);
		a.setLobby(p.getLocation());
		getArenas().add(a);
		a.setMinPlayers(2);
		a.setMaxPlayers(24);
		a.restart();
		return a;
	}

	/**
	 * Creates a new arena
	 *
	 * @param creator The creator attributed with making the arena
	 */
	public void createWorld(final Player creator, final String worldName, final String display) {
		creator.getInventory().addItem(new ItemStack(Material.BLAZE_ROD));

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {
			@Override
			public void run() {
				// todo this is only a temp solution to create a new map
				SGWorld world = SGApi.getMultiWorldManager().createWorld(worldName, display);
				creator.teleport(new Location(world.getWorld(), world.getWorld().getSpawnLocation().getX(), world.getWorld().getSpawnLocation().getY(), world.getWorld().getSpawnLocation().getZ()));
				getCreators().put(creator.getName(), world);
			}
		});

	}

	public void createWorldFromDownload(final Player creator, final String worldName, final String displayName) {

		new DownloadMap(creator, worldName).begin();

	}

	public void createWorldFromImport(final Player creator, final String worldName, final String displayName) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {
			@Override
			public void run() {
				SGApi.getMultiWorldManager().importWorldFromFolder(creator, worldName, displayName);
			}
		});

	}

	public Block deserializeBlock(String st) {
		String[] s = st.split(":");
		return deserializeLoc(serializeLoc(new Location(Bukkit.getServer().getWorld(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]), Integer.parseInt(s[4])))).getBlock();
	}

	/**
	 * Gets a location from a string
	 *
	 * @param s The string to deserialize
	 * @return The location represented from the string
	 */
	public Location deserializeLoc(String s) {
		String[] st = s.split(",");
		return new Location(Bukkit.getWorld(st[0]), Integer.parseInt(st[1]), Integer.parseInt(st[2]), Integer.parseInt(st[3]));
	}

	/**
	 * Gets an arena from an integer ID
	 *
	 * @param i The ID to get the Arena from
	 * @return The arena from which the ID represents. May be null.
	 * @throws ArenaNotFoundException
	 */
	public SGArena getArena(int i) throws ArenaNotFoundException {
		for (SGArena a : getArenas()) {
			if (a.getId() == i) {
				return a;
			}
		}
		throw new ArenaNotFoundException("Could not find given arena with given ID: " + i);
	}

	public SGArena getArena(Player p) throws ArenaNotFoundException {
		for (SGArena a : getArenas()) {
			if (a.getPlayers().contains(p.getUniqueId())) {
				return a;
			}
			if (a.getSpectators().contains(p.getUniqueId())) {
				return a;
			}
		}
		throw new ArenaNotFoundException("Could not find given arena with given Player: " + p.getDisplayName());
	}

	/**
	 * Get the arenas
	 *
	 * @return the ArrayList of arenas
	 */
	public List<SGArena> getArenas() {
		return arenas;
	}

	public Map<String, ItemStack[]> getArmor() {
		return armor;
	}

	/**
	 * Gets the HashMap that contains the creators
	 *
	 * @return The HashMap of creators
	 */
	public Map<String, SGWorld> getCreators() {
		return creators;
	}

	public String getError() {
		return error;
	}

	public Map<String, ItemStack[]> getInv() {
		return inv;
	}

	public Map<String, Location> getLocs() {
		return locs;
	}

	public String getPrefix() {
		return prefix;
	}

	/**
	 * Heal a given Player
	 */
	private void healPlayer(final Player p) {
		final double amount = p.getMaxHealth() - p.getHealth();
		final EntityRegainHealthEvent erhe = new EntityRegainHealthEvent(p, amount, RegainReason.CUSTOM);
		SGApi.getPlugin().getServer().getPluginManager().callEvent(erhe);
		if (erhe.isCancelled()) {
			return;
		}

		double newAmount = p.getHealth() + erhe.getAmount();
		if (newAmount > p.getMaxHealth()) {
			newAmount = p.getMaxHealth();
		}

		p.setHealth(newAmount);
		p.setFoodLevel(20);
		p.setFireTicks(0);
		for (PotionEffect effect : p.getActivePotionEffects()) {
			p.removePotionEffect(effect.getType());
		}
	}

	/**
	 * Gets whether the player is playing
	 *
	 * @param p The player that will be scanned
	 * @return Whether the player is in a game
	 */
	public boolean isInGame(Player p) {
		for (SGArena a : getArenas()) {
			if (a.getPlayers().contains(p.getUniqueId())) {
				return true;
			}

			if (a.getSpectators().contains(p.getUniqueId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Loads the game into memory after a shutdown or a relaod
	 */
	public void loadGames() {
		File arenas = new File(SGApi.getPlugin().getDataFolder().getAbsolutePath() + "/arenas/");
		File maps = new File(SGApi.getPlugin().getDataFolder().getAbsolutePath() + "/maps/");

		if (SGApi.getPlugin().getPluginConfig().isBungeecordMode()) {
			if (arenas.listFiles().length > 1) {
				Bukkit.getLogger().severe("You cannot have mutiple arenas on Bungeecord mode");
				Bukkit.getPluginManager().disablePlugin(SGApi.getPlugin());
			}
		}

		if (maps.listFiles().length != 0) {
			for (File file : maps.listFiles()) {
				ConfigTemplate<SGWorld> configTemplate = new WorldConfigTemplate(file);
				SGWorld world = configTemplate.deserialize();
				Bukkit.getLogger().info("Loaded map! " + world.toString());
				SGApi.getMultiWorldManager().getWorlds().add(world);
			}
		}

		if (arenas.listFiles().length != 0) {
			for (File file : arenas.listFiles()) {
				ConfigTemplate<SGArena> configTemplate = new ArenaConfigTemplate(file);
				SGArena arena = configTemplate.deserialize();
				Bukkit.getLogger().info("Loaded arena! " + arena.toString());
				this.getArenas().add(arena);

				arena.restart();
			}
		}
	}

	public void playerDeathAndLeave(Player p, SGArena a) {
		a.deathAndLeave(p);
	}

	/**
	 * Player disconnects in game :/
	 *
	 * We can't get the player's inventory here to shoot out the items.  Oh well.
	 */
	public void playerDisconnect(Player p) {
		try {
			SGApi.getArenaManager().getArena(p).deathWithQuit(p);
		} catch (ArenaNotFoundException e) {}
	}

	public void playerKilled(Player p, SGArena a) {
		a.death(p);
	}

	/**
	 * Removes an arena from memory
	 *
	 * @param i The ID of the arena to be removed
	 */
	public void removeArena(int i) {
		SGArena a;
		try {
			a = getArena(i);
		} catch (ArenaNotFoundException e) {
			Bukkit.getLogger().severe(e.getMessage());
			return;
		}
		getArenas().remove(a);
		new File(SGApi.getPlugin().getDataFolder().getAbsolutePath() + "/arenas/" + i + ".yml").delete();
	}

	/**
	 * Removes the player from an arena
	 *
	 * @param p The player to remove from an arena
	 */
	public void removePlayer(Player p) {
		PlayerVanishUtil.showAll(p);

		try {
			if (this.getArena(p).getState().equals(SGArena.ArenaState.PRE_COUNTDOWN) || this.getArena(p).getState().equals(SGArena.ArenaState.WAITING_FOR_PLAYERS)) {
				SGArena a = getArena(p);
				a.getPlayers().remove(p.getUniqueId());
				if (a.getPlayers().size() < a.getMinPlayers())
					a.restart();
				if (a.getSpectators().contains(p.getUniqueId()))
					a.getSpectators().remove(p.getUniqueId());
				else {
					a.getPlayers().remove(p.getUniqueId());
				}
				p.teleport(Bukkit.getWorld(SGApi.getPlugin().getPluginConfig().getHubWorld()).getSpawnLocation());
				p.setGameMode(GameMode.SURVIVAL);
				p.getActivePotionEffects().clear();
				p.setAllowFlight(false);
				p.setFlying(false);
				p.setCanPickupItems(true);
				p.setHealth(20);
				p.setFoodLevel(20);
				p.getInventory().clear();
				p.getInventory().setArmorContents(null);
				for (PotionEffect effect : p.getActivePotionEffects()) {
					p.removePotionEffect(effect.getType());
				}

				p.setFireTicks(0);
				return;
			}
		} catch (ArenaNotFoundException e) {
			p.teleport(Bukkit.getWorld(SGApi.getPlugin().getPluginConfig().getHubWorld()).getSpawnLocation());
			p.setGameMode(GameMode.SURVIVAL);
			p.getActivePotionEffects().clear();
			p.setAllowFlight(false);
			p.setFlying(false);
			p.setCanPickupItems(true);
			p.setHealth(20);
			p.setFoodLevel(20);

			for (PotionEffect effect : p.getActivePotionEffects()) {
				p.removePotionEffect(effect.getType());
			}

			p.setFireTicks(0);
			SendWebsocketData.stopMusic(p);
		}
		SGArena a = null;
		for (SGArena arena : getArenas()) {
			if (arena.getPlayers().contains(p.getUniqueId()) || arena.getSpectators().contains(p.getUniqueId())) {
				a = arena;
			}
		}

		p.getInventory().clear();
		p.getInventory().setArmorContents(null);

		getInv().remove(p.getName());
		getArmor().remove(p.getName());
		//p.teleport(locs.get(p.getName()));
		p.teleport(Bukkit.getWorld(SGApi.getPlugin().getPluginConfig().getHubWorld()).getSpawnLocation());
		p.setGameMode(GameMode.SURVIVAL);
		p.getActivePotionEffects().clear();
		p.setAllowFlight(false);
		p.setFlying(false);
		p.setCanPickupItems(true);
		p.setHealth(20);
		p.setExhaustion(0);
		p.setFoodLevel(20);
		getLocs().remove(p.getName());

		for (PotionEffect effect : p.getActivePotionEffects()) {
			p.removePotionEffect(effect.getType());
		}

		p.setFireTicks(0);

		if (a == null)
			return;

		if (a.getSpectators().contains(p.getUniqueId()))
			a.getSpectators().remove(p.getUniqueId());
		else {
			a.getPlayers().remove(p.getUniqueId());
		}

		//p.getInventory().setContents(inv.get(p.getName()));
		//p.getInventory().setArmorContents(armor.get(p.getName()));
	}

	public String serializeBlock(Block b) {
		return b.getType() + ":" + serializeLoc(b.getLocation());
	}

	/**
	 * Serializes a location to a string
	 *
	 * @param l The location to serialize
	 * @return The serialized location
	 */
	public String serializeLoc(Location l) {
		return l.getWorld().getName() + "," + l.getBlockX() + "," + l.getBlockY() + "," + l.getBlockZ();
	}
}
