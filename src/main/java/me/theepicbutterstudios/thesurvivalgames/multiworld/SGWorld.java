/**
 * Name: SGWorld.java
 * Created: 16 December 2013
 *
 * @version 1.0.0
 */
package me.theepicbutterstudios.thesurvivalgames.multiworld;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

public class World {

    String name;
    WorldCreator wc;

    public World(String name) {
        if(Bukkit.getServer().getWorld(name) != null) 
            return;

        this.name = name;

        wc = new WorldCreator(name);
        wc.enviroment(World.Enviroment.NORMAL);
        wc.type(WorldType.NORMAL);
    }

    public void create() {
        wc.createWorld();
    }

    public void remove() {}

}
