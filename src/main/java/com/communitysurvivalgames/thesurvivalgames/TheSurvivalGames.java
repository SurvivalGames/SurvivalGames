/**
 * Name: TheSurvivalGames.java Created: 19 November 2013 Edited: 9 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.communitysurvivalgames.thesurvivalgames.command.CommandHandler;
import com.communitysurvivalgames.thesurvivalgames.command.PartyCommandHandler;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.CreateCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.HelpCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.JoinCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.UserCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.ChatCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.DeclineCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.InviteCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.LeaveCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.ListCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.PromoteCommand;
import com.communitysurvivalgames.thesurvivalgames.listeners.EntityDamageListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.ItemListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.SetupListener;
import com.communitysurvivalgames.thesurvivalgames.local.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.ArenaManager;
import com.communitysurvivalgames.thesurvivalgames.objects.PlayerData;
import com.communitysurvivalgames.thesurvivalgames.runnables.Scoreboard;
import com.communitysurvivalgames.thesurvivalgames.util.DoubleJump;

public class TheSurvivalGames extends JavaPlugin {

	static TheSurvivalGames plugin;

	@Override
	public void onEnable() {
		plugin = this;

		File i18N = new File(getDataFolder(), "I18N.yml");
		if (!i18N.exists()) {
			saveResource("I18N.yml", false);
		}
		FileConfiguration lang = YamlConfiguration.loadConfiguration(i18N);
		
		I18N.setupLocale();
		I18N.setLocale(lang.getString("language"));
		
		//TODO Add more languages!
		saveResource("enUS", true);
		saveResource("idID", true);

		registerAll();
		setupDatabase();
		ArenaManager am = new ArenaManager(this);
		am.loadGames();

		getLogger().info(I18N.getLocaleString("BEEN_ENABLED"));
		getLogger().info(I18N.getLocaleString("COMMUNITY_PROJECT"));
		saveDefaultConfig();
	}

	@Override
	public void onDisable() {
		getLogger().info(I18N.getLocaleString("BEEN_DISABLED"));
	}

	public void registerAll() {
		// register all commands and listeners
		getCommand("sg").setExecutor(new CommandHandler());
		getCommand("party").setExecutor(new PartyCommandHandler());

		CommandHandler.register("help", new HelpCommand());
		CommandHandler.register("create", new CreateCommand());
		CommandHandler.register("join", new JoinCommand());
		CommandHandler.register("user", new UserCommand());

		PartyCommandHandler.register("chat", new ChatCommand());
		PartyCommandHandler.register("decline", new DeclineCommand());
		PartyCommandHandler.register("help", new HelpCommand());
		PartyCommandHandler.register("invite", new InviteCommand());
		PartyCommandHandler.register("join", new JoinCommand());
		PartyCommandHandler.register("leave", new LeaveCommand());
		PartyCommandHandler.register("list", new ListCommand());
		PartyCommandHandler.register("promote", new PromoteCommand());

		PluginManager pm = getServer().getPluginManager();

		pm.registerEvents(new ItemListener(this), this);
		pm.registerEvents(new SetupListener(), this);
		pm.registerEvents(new EntityDamageListener(), this);
		pm.registerEvents(new DoubleJump(this), this);

		Scoreboard.registerScoreboard(this);
	}

	/**
	 * Setup Persistence Databases and Install DDL if there are none
	 */
	private void setupDatabase() {
		File ebean = new File(getDataFolder(), "ebean.properties");
		if (!ebean.exists()) {
			saveResource("ebean.properties", false);
		}
		try {
			getDatabase().find(PlayerData.class).findRowCount();
		} catch (PersistenceException ex) {
			System.out.println("Installing database for " + getDescription().getName() + " due to first time usage");
			installDDL();
		}
	}

	/**
	 * Gets Persistence Database classes WARNING: DO NOT EDIT
	 * 
	 * @return The list of classes for the database
	 */
	@Override
	public List<Class<?>> getDatabaseClasses() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(PlayerData.class);
		return list;
	}

	public PlayerData getPlayerData(Player player) {
		PlayerData data = getDatabase().find(PlayerData.class).where().ieq("playerName", player.getName()).findUnique();
		if (data == null) {
			data = new PlayerData(player);
		}

		return data;
	}

	public void setPlayerData(PlayerData data) {
		getDatabase().save(data);
	}

	/**
	 * Only to be used if 100% needed
	 */
	public static TheSurvivalGames getPlugin() {
		return plugin;
	}
}
