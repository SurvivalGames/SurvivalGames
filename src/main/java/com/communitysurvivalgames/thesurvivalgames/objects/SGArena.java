/**
 * Name: SGArena.java Edited: 7 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.objects;

import com.communitysurvivalgames.thesurvivalgames.event.KitGivenEvent;
import com.communitysurvivalgames.thesurvivalgames.kits.Kit;
import com.communitysurvivalgames.thesurvivalgames.kits.KitItem;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.multiworld.SGWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class SGArena {

	private ArenaState currentState;
	private int id = 0;

	public Location lobby = null;
	public SGWorld currentMap;

	public List<String> voted = new ArrayList<>();
	public Map<MapHash, Integer> votes = new HashMap<>();
	public Map<String, Kit> kits = new HashMap<>();

	public int maxPlayers;
	public int minPlayers;

	public final List<String> players = new CopyOnWriteArrayList<>();
	public final List<String> spectators = new CopyOnWriteArrayList<>();

	public void setPlayerKit(Player player, Kit kit) {
		Bukkit.getServer().getPluginManager().callEvent(new KitGivenEvent(player, kit));
		for (KitItem item : kit.getItems()) {
			kits.put(player.getName(), kit);
			player.getInventory().addItem(item.getItem());
		}
	}

	/**
	 * Name: ArenaState.java Edited: 8 December 2013
	 * 
	 * 
	 * @version 1.0.0
	 */
	public enum ArenaState {

		WAITING_FOR_PLAYERS, STARTING_COUNTDOWN, IN_GAME, DEATHMATCH, POST_GAME;

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
	}

	/**
	 * Constructs a new arena based off of a Location and an ID
	 * 
	 * @param id The ID the arena will have
	 */
	public void createArena(int id) {
		this.id = id;
	}

	public SGArena() {
	}

	/**
	 * Makes sure that the fields aren't null on startup
	 *
	 * @param lob The lobby spawn
	 * @param maxPlayers The max players for the arena
	 * @param minPlayers The min players needed for the game to start
	 */
	public void initialize(Location lob, int maxPlayers, int minPlayers) {
		this.lobby = lob;
		this.maxPlayers = maxPlayers;
		this.minPlayers = minPlayers;
	}

	/**
	 * Sends all the players a message
	 * 
	 * @param message The message to send, do not include prefix
	 */
	public void broadcast(String message) {
		for (String s : players) {
			Player p = Bukkit.getServer().getPlayerExact(s);
			if (p != null) {
				p.sendMessage(SGApi.getArenaManager().prefix + message);
			}
		}
	}

	/**
	 * Puts the arena into deathmatch
	 */
	public void dm() {
		int i = 0;
		for (String s : players) {
			Player p;
			if ((p = Bukkit.getServer().getPlayerExact(s)) != null) {
				p.teleport(currentMap.locs.get(i));
				i++;
			}
		}
	}

	/**
	 * Ends the arena
	 */
	public void end() {
		if (players.size() == 1) {
			broadcast(SGApi.getArenaManager().prefix + I18N.getLocaleString("END") + " " + players.get(0));
		} else {
			broadcast(SGApi.getArenaManager().prefix + I18N.getLocaleString("ARENA_END"));
		}

		for (String s : players) {
			Player p;
			if ((p = Bukkit.getServer().getPlayerExact(s)) != null) {
				SGApi.getArenaManager().removePlayer(p);
			}
		}
		for (String s : spectators) {
			Player p;
			if ((p = Bukkit.getServer().getPlayerExact(s)) != null) {
				SGApi.getArenaManager().removePlayer(p);
			}
		}
		voted.clear();
		votes.clear();

		setState(ArenaState.POST_GAME);
		// rollback
		setState(ArenaState.WAITING_FOR_PLAYERS);
		SGApi.getTimeManager(this).countdownLobby(5);
	}

	/**
	 * Sets the state of the SG arena
	 * 
	 * @param state - The new state
	 */
	public void setState(ArenaState state) {
		currentState = state;
	}

	/**
	 * Gets the ID of the arena
	 * 
	 * @return The ID of the arena
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Gets the list of players in the arena
	 * 
	 * @return List of players in the arena
	 */
	public List<String> getPlayers() {
		return this.players;
	}

	/**
	 * Makes a player vote
	 * 
	 * @param p the voter
	 * @param i the map number
	 */
	public void vote(Player p, int i) {
		if (currentState != ArenaState.WAITING_FOR_PLAYERS) {
			p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("NOT_VOTING"));
			return;
		}

		for (Map.Entry<MapHash, Integer> e : votes.entrySet()) {
			if (e.getKey().getId() == i) {
				votes.put(new MapHash(e.getKey().getWorld(), i), votes.get(e.getValue()) + 1);
			}
		}
		voted.add(p.getName());
	}

	/**
	 * Gets the current state of the arena
	 * 
	 * @return The current state
	 */
	public ArenaState getState() {
		return currentState;
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

	public List<String> getSpectators() {
		return spectators;
	}

	public World getArenaWorld() {
		return currentMap.getWorld();
	}

	public Kit getKit(Player p) {
		return kits.get(p.getName());

	}
}
