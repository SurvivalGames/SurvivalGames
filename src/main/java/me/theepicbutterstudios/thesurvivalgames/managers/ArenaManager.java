package me.theepicbutterstudios.thesurvivalgames.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.theepicbutterstudios.thesurvivalgames.TheSurvivalGames;

public class ArenaManager{

    public Map<String, Location> locs = new HashMap<String, Location>();
    public static ArenaManager am = new ArenaManager();
    Map<String, ItemStack[]> inv = new HashMap<String, ItemStack[]>();
    Map<String, ItemStack[]> armor = new HashMap<String, ItemStack[]>();
    List<Arena> arenas = new ArrayList<Arena>();
    int arenaSize = 0;

    /**
     * Initialize the singleton with a SurvivalGames plugin field
     * 
     * @param sg TheSurvivalGames plugin reference
     */

    static TheSurvivalGames plugin;
    public ArenaManager(TheSurvivalGames sg) {
        plugin = sg;
    }

    /**
     * 
     * The constructor for a new reference of the singleton
     * 
     */

    protected ArenaManager(){}

    /**
     * Gets the reference of the singlton
     * 
     * @return The reference of the ArenaManager
     */ 

    public static ArenaManager getManager(){
        return am;
    }

    /**
     * Gets an arena from an integer ID 
     * 
     * @param i The ID to get the Arena from
     * @return The arena from which the ID represents. May be null.
     */

    public Arena getArena(int i){
        for(Arena a : arenas){
            if(a.getId() == i){
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

    public void addPlayer(Player p, int i){
        Arena a = getArena(i);
        if(a == null){
            p.sendMessage("Invalid arena!");
            return;
        }

        a.getPlayers().add(p.getName());
        inv.put(p.getName(), p.getInventory().getContents());
        armor.put(p.getName(), p.getInventory().getArmorContents());

        p.getInventory().setArmorContents(null);
        p.getInventory().clear();

        p.teleport(a.spawn);
    }

    /**
     * Removes the player from an arena
     * 
     * @param p The player to remove from an arena
     */ 

    public void removePlayer(Player p){
        Arena a = null;
        for(Arena arena : arenas){
            if(arena.getPlayers().contains(p.getName())){
                a = arena;
            }
        }
        if(a == null || !a.getPlayers().contains(p.getName())){
            p.sendMessage("Invalid operation!");
            return;
        }

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
     * @param l The location the arena spawn will be at
     * @return The arena that was created
     */ 

    /* Work on this later. Reading the README on how to setup public Arena createArena(Location l){
        int num = arenaSize + 1;
        arenaSize++;

        Arena a = new Arena(l, num);
        arenas.add(a);

        plugin.getConfig().set("Arenas." + num, serializeLoc(l));
        List<Integer> list = plugin.getConfig().getIntegerList("Arenas.Arenas");
        list.add(num);
        plugin.getConfig().set("Arenas.Arenas", list);
        plugin.saveConfig();

        return a;
    }

    /**
     * Stores an existing arena in the list, for example after reloads
     * 
     * @param l The location teh arena spawn will be at
     * @return The arena that was created
     */ 

    public Arena reloadArena(Location l) {
        int num = arenaSize + 1;
        arenaSize++;
 
        Arena a = new Arena(l, num);
        arenas.add(a);
 
        return a;
    } */
    
    /**
     * Removes an arena from memory
     * 
     * @param i The ID of the arena to be removed
     */ 
    
    public void removeArena(int i) {
        Arena a = getArena(i);
        if(a == null) {
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

    public boolean isInGame(Player p){
        for(Arena a : arenas){
            if(a.getPlayers().contains(p.getName()))
                return true;
        }
        return false;
    }
    
    /**
     * 
     * Loads the game into memory after a shutdown or a relaod
     * 
     */

    /* public void loadGames(){
        arenaSize = 0;      

        if(plugin.getConfig().getIntegerList("Arenas.Arenas").isEmpty()){
            return;
        }
                
        for(int i : plugin.getConfig().getIntegerList("Arenas.Arenas")){
            reloadArena(i);
            a.id = i;
        }
    } */
    
    /**
     * Serializeds a location to a string
     * 
     * @param l The location to serialize
     * @return The serialized location
     */
    
    public String serializeLoc(Location l){
        return l.getWorld().getName()+","+l.getBlockX()+","+l.getBlockY()+","+l.getBlockZ();
    }
    
    /**
     * Gets a location from a string
     * 
     * @param The string to deserialize
     * @return The location represented from the string
     */
    
    public Location deserializeLoc(String s){
        String[] st = s.split(",");
        return new Location(Bukkit.getWorld(st[0]), Integer.parseInt(st[1]), Integer.parseInt(st[2]), Integer.parseInt(st[3]));
    }
}
