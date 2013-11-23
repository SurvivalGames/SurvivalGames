/**
 * Name: TheSurvivalGames.java
 * 
 * Version: 1.0
 *
 * Edited: 11 November 2013
 */

package me.theepicbutterstudios.thesurvivalgames;

import com.gmail.woodyc40.arenaapi.*;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main class of TheSurvivalGames plugin, register events, commands and files
 *
 * @author Communtity
 * @version 1.0
 */

public class TheSurvivalGames extends JavaPlugin  {

    public void onEnable() {
        new ArenaManager(this);
        ArenaManager.getManager().loadGames();

        getLogger().info("is a community project, join at http://dev.bukkit.org/bukkit-plugins/the-survival-games/");
    }

    public void onDisable() {}
}
