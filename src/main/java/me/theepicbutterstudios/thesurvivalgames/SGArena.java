/**
 * Name: SGArena.java
 * Edited: 7 December 2013
 *
 * @version 1.0.0
 */

package me.theepicbutterstudios.thesurvivalgames;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class SGArena {

	ArenaState currentState;
	public int id = 0;
	public Location lobby = null;
	public List<Location> locs = new ArrayList<Location>();
	public int maxPlayers;
	public int minPlayers;
	List<String> players = new ArrayList<String>();

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
	 * @param maxPlayers The max players for the arena
	 * @param minPlayers The min players needed for the game to start
	 */

	public void initialize(List<Location> list, Location lob, int maxPlayers, int minPlayers) {
		this.lobby = lob;
		this.locs = list;
		this.maxPlayers = maxPlayers;
		this.minPlayers = minPlayers;
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
}
