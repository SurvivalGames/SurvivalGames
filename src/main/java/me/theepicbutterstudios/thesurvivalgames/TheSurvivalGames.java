/**
 * Name: TheSurvivalGames.java
 * Created: 19 Nov 2013
 * 
 * @author TheEpicButterStudios calebbfmv jimbo8 xTrollxDudex
 * @version 1.0.0
 */

package me.theepicbutterstudios.thesurvivalgames;

import com.gmail.woodyc40.arenaapi.*;

import org.bukkit.plugin.java.JavaPlugin;

public class TheSurvivalGames extends JavaPlugin  {

    public void onEnable() {
        new ArenaManager(this);
        getLogger().info("[The Survival Games] has been enabled!")
        getLogger().info("[The Survival Games] is a community project, join at http://dev.bukkit.org/bukkit-plugins/the-survival-games/")
    }

    public void onDisable() {
        getLogger().info("[The Survival Games] was disabled.")

    }

}
