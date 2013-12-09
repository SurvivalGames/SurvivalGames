/**
 * Name: TheSurvivalGames.java
 * Created: 19 November 2013
 * Edited: 7 December 2013
 *
 * @version 1.0.0
 */

package me.theepicbutterstudios.thesurvivalgames;

import me.theepicbutterstudios.thesurvivalgames.command.CommandHandler;
import me.theepicbutterstudios.thesurvivalgames.command.subcommands.CreateCommand;
import me.theepicbutterstudios.thesurvivalgames.command.subcommands.HelpCommand;
import me.theepicbutterstudios.thesurvivalgames.command.subcommands.JoinCommand;
import me.theepicbutterstudios.thesurvivalgames.listeners.EntityDamageListener;
import me.theepicbutterstudios.thesurvivalgames.listeners.ItemListener;
import me.theepicbutterstudios.thesurvivalgames.listeners.SetupListener;
import me.theepicbutterstudios.thesurvivalgames.managers.ArenaManager;
import me.theepicbutterstudios.thesurvivalgames.runnables.MainScoreboard;

import org.bukkit.plugin.PluginManager;
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
		CommandHandler.register("help", new HelpCommand());
		CommandHandler.register("create", new CreateCommand());
		CommandHandler.register("join", new JoinCommand());

		PluginManager pm = getServer().getPluginManager();

		pm.registerEvents(new ItemListener(this), this);
		pm.registerEvents(new SetupListener(), this);
		pm.registerEvents(new EntityDamageListener(), this);
		
		MainScoreboard.registerScoreboard(this);
	}

}
