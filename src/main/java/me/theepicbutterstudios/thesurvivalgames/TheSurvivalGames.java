/**
 * Name: TheSurvivalGames.java
 * Created: 19 Nov 2013
 * Edited 23 Nov 2013
 *
 * @version 1.0.0
 */

package me.theepicbutterstudios.thesurvivalgames;

//What the heck is "com.java"...
import com.java.util.logging.Logger;

import me.theepicbutterstudios.thesurvivalgames.command.CommandHandler;
import me.theepicbutterstudios.thesurvivalgames.command.subcommands.Help;
import me.theepicbutterstudios.thesurvivalgames.handlers.ArenaHandler;

import org.bukkit.plugin.java.JavaPlugin;

public class TheSurvivalGames extends JavaPlugin  {

    public void onEnable() {
        registerAll();
        new ArenaManager(this);

        //Please erase the [The Survival Games], getLogger() auto puts that in the front...
        getLogger().info("[The Survival Games] has been enabled!");
        getLogger().info("[The Survival Games] is a community project, join at http://dev.bukkit.org/bukkit-plugins/the-survival-games/");
        saveDefaultConfig();
    }

    public void onDisable() {
        //Same for here, remove the [The Survival Games] part
        getLogger().info("[The Survival Games] was disabled.")
    }

    public void registerAll() {
        //register all commands and listeners
        getCommand("sg").setExecutor(new CommandHandler());
        CommandHandler.register("help", new Help());
    }
}
