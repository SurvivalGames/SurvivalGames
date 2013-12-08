/**
 * Name: TheSurvivalGames.java
 * Created: 19 November 2013
 * Edited: 7 December 2013
 *
 * @version 1.0.0
 */

package me.theepicbutterstudios.thesurvivalgames;

import me.theepicbutterstudios.thesurvivalgames.command.CommandHandler;
import me.theepicbutterstudios.thesurvivalgames.command.subcommands.Create;
import me.theepicbutterstudios.thesurvivalgames.command.subcommands.Help;
import me.theepicbutterstudios.thesurvivalgames.command.subcommands.Join;
import me.theepicbutterstudios.thesurvivalgames.listeners.EntityDamageListener;
import me.theepicbutterstudios.thesurvivalgames.listeners.ItemListener;
import me.theepicbutterstudios.thesurvivalgames.listeners.SetupListener;
import me.theepicbutterstudios.thesurvivalgames.managers.ArenaManager;

import org.bukkit.plugin.java.JavaPlugin;

public class TheSurvivalGames extends JavaPlugin {

	public void onEnable() {
		registerAll();
		ArenaManager am = new ArenaManager(this);
		am.loadGames();

		getLogger().info("§ahas been enabled!");
		getLogger().info("§1is a community project, join at http://dev.bukkit.org/bukkit-plugins/the-survival-games/");
		saveDefaultConfig();
	}

	public void onDisable() {
		getLogger().info("was disabled.");
	}

	public void registerAll() {
		// register all commands and listeners
		getCommand("sg").setExecutor(new CommandHandler());
		CommandHandler.register("help", new Help());
		CommandHandler.register("create", new Create());
		CommandHandler.register("join", new Join());

                PluginManager pm = getServer().getPluginManager();

                pm.registerEvents(new ItemListener(), this);
		pm.registerEvents(new SetupListener(), this);
		pm.registerEvents(new EntityDamageListener(), this);
	}

}
