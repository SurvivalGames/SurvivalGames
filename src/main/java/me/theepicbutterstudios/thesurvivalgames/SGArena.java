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

	public int id = 0;
	public Location lobby = null;
	public List<Location> locs = new ArrayList<Location>();
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
	 * Nakes sure that the fields aren't null on startup
	 * 
	 * @param list The locatins for game spawns
	 * @param lob The lobby spawn
	 */

	public void initialize(List<Location> list, Location lob) {
		this.lobby = lob;
		this.locs = list;
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
}
