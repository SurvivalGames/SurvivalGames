/**
 * Name: ArenaManager.java Edited: 7 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.communitysurvivalgames.thesurvivalgames.TheSurvivalGames;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;

public class ArenaManager {

	public final String prefix = ChatColor.DARK_AQUA + "[TheSurvivalGames]" + ChatColor.GOLD;
	public final String error = ChatColor.DARK_AQUA + "[TheSurvivalGames]" + ChatColor.RED;

	private final Map<String, SGArena> creators = new HashMap<String, SGArena>();
	private final Map<String, Location> locs = new HashMap<String, Location>();
	private static final ArenaManager am = new ArenaManager();
	private final Map<String, ItemStack[]> inv = new HashMap<String, ItemStack[]>();
	private final Map<String, ItemStack[]> armor = new HashMap<String, ItemStack[]>();
	private final List<SGArena> arenas = new ArrayList<SGArena>();
	private int arenaSize = 0;

	private static TheSurvivalGames plugin;

	/**
	 * Initialize the singleton with a SurvivalGames plugin field
	 *
	 * @param sg TheSurvivalGames plugin reference
	 */
	public ArenaManager(TheSurvivalGames sg) {
		plugin = sg;
	}

	/**
	 * The constructor for a new reference of the singleton
	 */
	private ArenaManager() {
	}

	/**
	 * Gets the reference of the singlton
	 *
	 * @return The reference of the ArenaManager
	 */
	public static ArenaManager getManager() {
		return am;
	}

	/**
	 * Gets an arena from an integer ID
	 *
	 * @param i The ID to get the Arena from
	 * @return The arena from which the ID represents. May be null.
	 */
	public SGArena getArena(int i) {
		for (SGArena a : arenas) {
			if (a.getId() == i) {
				return a;
			}
		}
		return null;
	}

	public SGArena getArena(Player p) {
		for (SGArena a : arenas) {
			if (a.getPlayers().contains(p.getName())) {
				return a;
			}
		}
		return null;
	}

	/**
	 * Adds a player to the specified arena
	 *
	 * @param p The player to be added
	 * @param i The arena ID in which the player will be added to.
	 */
	public void addPlayer(Player p, int i) {
		SGArena a = getArena(i);
		if (a == null) {
			p.sendMessage("Invalid arena!");
			return;
		}

		if (!a.getState().equals(SGArena.ArenaState.WAITING_FOR_PLAYERS)) {
			// set player to spectator
			return;
		}

		a.getPlayers().add(p.getName());
		inv.put(p.getName(), p.getInventory().getContents());
		armor.put(p.getName(), p.getInventory().getArmorContents());

		p.getInventory().setArmorContents(null);
		p.getInventory().clear();
		p.setExp(0);

		p.teleport(a.lobby);
		
		//Ding!
		for (Player player : plugin.getServer().getOnlinePlayers()){
			player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 1);
		}
	}

	/**
	 * Removes the player from an arena
	 *
	 * @param p The player to remove from an arena
	 */
	public void removePlayer(Player p) {
		SGArena a = null;
		for (SGArena arena : arenas) {
			if (arena.getPlayers().contains(p.getName())) {
				a = arena;
			}
		}
		if (a == null || !a.getPlayers().contains(p.getName())) {
			p.sendMessage("Invalid operation!");
			return;
		}

		if (a.getSpectators().contains(p.getName()))
			a.getSpectators().remove(p.getName());
		a.getPlayers().remove(p.getName());

		p.getInventory().clear();
		p.getInventory().setArmorContents(null);

		p.getInventory().setContents(inv.get(p.getName()));
		p.getInventory().setArmorContents(armor.get(p.getName()));

		inv.remove(p.getName());
		armor.remove(p.getName());
		p.teleport(locs.get(p.getName()));
		locs.remove(p.getName());

		p.setFireTicks(0);
	}

	/**
	 * Creates a new arena
	 *
	 * @param creator The creator attributed with making the arena
	 * @return The arena that was created
	 */
	public void createArena(final Player creator, final String worldName) {
		final int num = arenaSize + 1;
		arenaSize++;

		creator.getInventory().addItem(new ItemStack(Material.BLAZE_ROD));

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(TheSurvivalGames.getPlugin(), new Runnable() {

			@Override
			public void run() {

				SGArena a = new SGArena(num, MultiworldManager.getInstance().createRandomWorld(creator, worldName));
				arenas.add(a);
				a.getPlayers().add(creator.getName());

				creators.put(creator.getName(), a);

				//TODO Create new file configuration with default values here

				plugin.saveConfig();
			}
		});

	}

	public void createArenaFromDownload(final Player creator, final String worldName) throws IOException {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(TheSurvivalGames.getPlugin(), new Runnable() {

			@Override
			public void run() {
				int num = arenaSize + 1;
				arenaSize++;

				SGArena a = null;
				try {
					a = new SGArena(num, MultiworldManager.getInstance().copyFromInternet(creator, worldName));
				} catch (IOException e) {
					e.printStackTrace();
				}
				arenas.add(a);
			}
		});

	}

	public void createArenaFromImport(final Player creator, final String worldName) {

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(TheSurvivalGames.getPlugin(), new Runnable() {

			@Override
			public void run() {
				int num = arenaSize + 1;
				arenaSize++;

				SGArena a = new SGArena(num, MultiworldManager.getInstance().importWorldFromFolder(creator, worldName));
				arenas.add(a);
			}
		});

	}

	/**
	 * Stores an existing arena in the list, for example after reloads
	 *
	 * @param i The location the arena spawn will be at
	 */
	void reloadArena(int i) {
		FileConfiguration arenaConfig = YamlConfiguration.loadConfiguration(new File(Bukkit.getServer().getWorldContainer(), arenas.get(i).getArenaWorld().getName()));
		List<String> spawnLocsString = arenaConfig.getStringList("spawn-points");
		List<Location> spawnLocs = new ArrayList<Location>();
		for (int l = 0; l < spawnLocsString.size(); l++) {
			spawnLocs.add(deserializeLoc(spawnLocsString.get(l)));
		}
		Location lobby = deserializeLoc(arenaConfig.getString("lobby-spawn-point"));
		Location deathmatch = deserializeLoc(arenaConfig.getString("deathmatch-spawn-point"));
		int minPlayers = arenaConfig.getInt("min-players");
		int maxPlayers = arenaConfig.getInt("max-players");
		String arenaName = arenaConfig.getString("arena-name");
		arenas.get(i).initialize(spawnLocs, lobby, deathmatch, maxPlayers, minPlayers, arenaName);

	}

	/**
	 * Removes an arena from memory
	 *
	 * @param i The ID of the arena to be removed
	 */
	public void removeArena(int i) {
		SGArena a = getArena(i);
		if (a == null) {
			return;
		}
		arenas.remove(a);

		plugin.getConfig().set("Arenas." + i, null);
		List<Integer> list = plugin.getConfig().getIntegerList("Arenas.Arenas");
		list.remove(i);
		plugin.getConfig().set("Arenas.Arenas", list);
		plugin.saveConfig();
	}

	/**
	 * Gets whether the player is playing
	 *
	 * @param p The player that will be scanned
	 * @return Whether the player is in a game
	 */
	public boolean isInGame(Player p) {
		for (SGArena a : arenas) {
			if (a.getPlayers().contains(p.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Loads the game into memory after a shutdown or a relaod
	 */
	public void loadGames() {
		arenaSize = 0;

		if (plugin.getConfig().getIntegerList("Arenas.Arenas").isEmpty()) {
			return;
		}

		for (int i : plugin.getConfig().getIntegerList("Arenas.Arenas")) {
			reloadArena(i);
		}
	}

	/**
	 * Gets the HashMap that contains the creators
	 *
	 * @return The HashMap of creators
	 */
	public Map<String, SGArena> getCreators() {
		return creators;
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
}
