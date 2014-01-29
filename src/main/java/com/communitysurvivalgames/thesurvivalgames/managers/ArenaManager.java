/**
 * Name: ArenaManager.java Edited: 7 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.managers;

import com.communitysurvivalgames.thesurvivalgames.exception.ArenaNotFoundException;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.multiworld.SGWorld;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArenaManager {

    public final String prefix = ChatColor.DARK_AQUA + "[TheSurvivalGames]" + ChatColor.GOLD;
    public final String error = ChatColor.DARK_AQUA + "[TheSurvivalGames]" + ChatColor.RED;
    private final Map<String, SGWorld> creators = new HashMap<>();
    private final Map<String, Location> locs = new HashMap<>();
    private final Map<String, ItemStack[]> inv = new HashMap<>();
    private final Map<String, ItemStack[]> armor = new HashMap<>();
    private final List<SGArena> arenas = new ArrayList<>();
    public int arenaSize = 0;

    /**
     * The constructor for a new reference of the singleton
     */
    public ArenaManager() {
    }

    /**
     * Gets an arena from an integer ID
     * 
     * @param i The ID to get the Arena from
     * @return The arena from which the ID represents. May be null.
     * @throws ArenaNotFoundException
     */
    public SGArena getArena(int i) throws ArenaNotFoundException {
        for (SGArena a : arenas) {
            if (a.getId() == i) {
                return a;
            }
        }
        throw new ArenaNotFoundException("Could not find given arena with given ID: " + i);
    }

    public SGArena getArena(Player p) throws ArenaNotFoundException {
        for (SGArena a : arenas) {
            if (a.getPlayers().contains(p.getName())) {
                return a;
            }
        }
        throw new ArenaNotFoundException("Could not find given arena with given Player: " + p.getDisplayName());
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
            p.sendMessage(error + I18N.getLocaleString("NOT_JOINABLE"));
            return;
        }

        if (a.getState() != null && !a.getState().equals(SGArena.ArenaState.WAITING_FOR_PLAYERS)) {
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

        // Ding!
        for (Player player : SGApi.getPlugin().getServer().getOnlinePlayers()) {
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

        for (PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }

        p.setFireTicks(0);
    }

    /**
     * Creates a lobby
     */
    public void createLobby(Player p) {
        SGArena a = new SGArena();
        arenaSize++;
        a.createArena(arenaSize);

        a.lobby = p.getLocation();

        a.setState(SGArena.ArenaState.WAITING_FOR_PLAYERS);
        SGApi.getTimeManager(a).countdownLobby(5);
    }

    /**
     * Creates a new arena
     * 
     * @param creator The creator attributed with making the arena
     */
    public void createArena(final Player creator, final String worldName) {
        creator.getInventory().addItem(new ItemStack(Material.BLAZE_ROD));

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {

            @Override
            public void run() {
                // todo this is only a temp solution to create a new arena
                SGApi.getMultiWorldManager().createRandomWorld(worldName);
                creators.put(creator.getName(), new SGWorld(worldName, worldName));
                // TODO Create new file configuration with default values here
                SGApi.getPlugin().saveConfig();
            }
        });

    }

    public void createArenaFromDownload(final Player creator, final String worldName) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {
            @Override
            public void run() {
                SGApi.getMultiWorldManager().copyFromInternet(creator, worldName);
            }
        });

    }

    public void createArenaFromImport(final Player creator, final String worldName) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {
            @Override
            public void run() {
                SGApi.getMultiWorldManager().importWorldFromFolder(creator, worldName);
            }
        });

    }

    /**
     * Stores an existing arena in the list, for example after reloads
     * 
     * @param i The location the arena spawn will be at
     */
    private void reloadArena(int i) {
        FileConfiguration arenaConfig = YamlConfiguration.loadConfiguration(new File(Bukkit.getServer().getWorldContainer(), arenas.get(i).getArenaWorld().getName()));
        // TODO ^^ what the
        Location lobby = deserializeLoc(arenaConfig.getString("lobby-spawn-point"));
        int minPlayers = arenaConfig.getInt("min-players");
        int maxPlayers = arenaConfig.getInt("max-players");
        String arenaName = arenaConfig.getString("arena-name");
        arenas.get(i).initialize(lobby, maxPlayers, minPlayers);
    }
    
    /**
     * Reloads a map
     * 
     * @param actualName the name of the world that holds the block
     */
    public void reloadMap(String actualName) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(SGApi.getPlugin().getDataFolder(), actualName + ".yml"));
        List<String> spawnLocsString = config.getStringList("spawn-points");
        List<Location> spawnLocs = new ArrayList<>();
        for (String aSpawnLocsString : spawnLocsString) {
            spawnLocs.add(deserializeLoc(aSpawnLocsString));
        }
        
        List<BlockState> t2 = new ArrayList<>();
        for(String s : config.getStringList("tier2")) {
            t2.add(deserializeBlock(s).getState());
        }
        
        World world = SGApi.getMultiWorldManager().createWorld(actualName);
        SGWorld w = SGApi.getMultiWorldManager().worldForName(world.getName());
        
        w.setDisplayName(config.getString("map-name"));
        w.init(spawnLocs, t2);
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
        arenas.remove(a);

        SGApi.getPlugin().getConfig().set("Arenas." + i, null);
        List<Integer> list = SGApi.getPlugin().getConfig().getIntegerList("Arenas.Arenas");
        list.remove(i);
        SGApi.getPlugin().getConfig().set("Arenas.Arenas", list);
        SGApi.getPlugin().saveConfig();
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

        if (SGApi.getPlugin().getConfig().getIntegerList("Arenas.Arenas").isEmpty()) {
            return;
        }

        for (int i : SGApi.getPlugin().getConfig().getIntegerList("Arenas.Arenas")) {
            reloadArena(i);
        }
        
        if(SGApi.getPlugin().getConfig().getStringList("Arenas.Maps").isEmpty()) {
            return;
        }
        
        for(String s : SGApi.getPlugin().getConfig().getStringList("Arenas.Maps")) {
            reloadMap(s);
        }
    }

    /**
     * Gets the HashMap that contains the creators
     * 
     * @return The HashMap of creators
     */
    public Map<String, SGWorld> getCreators() {
        return creators;
    }
    
    /**
     * Get the arenas
     * 
     * @return the ArrayList of arenas
     */
    public List<SGArena> getArenas() {
        return arenas;
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
    
    public String serializeBlock(Block b) {
        return b.getType() + ":" + serializeLoc(b.getLocation());
    } 

    /**
     * Gets a location from a string
     * 
     * @param s The string to deserialize
     * @return The location represented from the string
     */
    private Location deserializeLoc(String s) {
        String[] st = s.split(",");
        return new Location(Bukkit.getWorld(st[0]), Integer.parseInt(st[1]), Integer.parseInt(st[2]), Integer.parseInt(st[3]));
    }
    
    public Block deserializeBlock(String s) {
        String [] = s.split(":");
        return deserializeLoc(serializeLoc(new Location(Bukkit.getServer().getWorld(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]), Integer.parseInt(s[4])))).getBlock();
    }
}
