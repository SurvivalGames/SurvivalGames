/**
 * Name: SGArena.java Edited: 7 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.communitysurvivalgames.thesurvivalgames.managers.ArenaManager;

import java.util.ArrayList;
import java.util.List;

public class SGArena {

	ArenaState currentState;
	public int id = 0;
	
	public Location lobby = null;
	public List<Location> locs = new ArrayList<Location>(0);
	public Location dm = null;
	
	public int maxPlayers;
	public int minPlayers;
	List<String> players = new ArrayList<String>();
	List<String> spectators = new ArrayList<String>();

	/**
	 * Name: ArenaState.java Edited: 8 December 2013
	 *
	 * @version 1.0.0
	 */
	public enum ArenaState {

		WAITING_FOR_PLAYERS, STARTING_COUNTDOWN, PRE_GAME, IN_GAME, POST_GAME;

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

			if (a.equals(PRE_GAME)) {
				if (arena.getState().equals(WAITING_FOR_PLAYERS)) {
					return false;
				} else if (arena.getState().equals(STARTING_COUNTDOWN)) {
					return false;
				} else if (arena.getState().equals(PRE_GAME)) {
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
	public SGArena(int id) {
		this.id = id;
	}

	/**
	 * Makes sure that the fields aren't null on startup
	 *
	 * @param list The locatins for game spawns
	 * @param lob The lobby spawn
	 * @param dm The deathmatch spawn
	 * @param maxPlayers The max players for the arena
	 * @param minPlayers The min players needed for the game to start
	 */
	public void initialize(List<Location> list, Location lob, Location dm,  int maxPlayers, int minPlayers) {
		this.lobby = lob;
		this.dm = dm;
		this.locs = list;
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
				p.sendMessage(ArenaManager.getManager().prefix + message);
			}
		}
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
	 * Adds the next spawn into the list of spawns
	 *
	 * @param loc The location of the spawn
	 */
	public void nextSpawn(Location loc) {
		locs.add(loc);
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
}
