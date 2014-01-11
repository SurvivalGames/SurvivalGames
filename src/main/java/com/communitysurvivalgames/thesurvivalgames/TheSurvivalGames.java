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

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.communitysurvivalgames.thesurvivalgames.command.CommandHandler;
import com.communitysurvivalgames.thesurvivalgames.command.PartyCommandHandler;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.CreateCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.SetCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.StartCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.StopCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.UserCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.ChatCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.DeclineCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.InviteCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.ListCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.PromoteCommand;
import com.communitysurvivalgames.thesurvivalgames.listeners.BlockListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.ChatListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.EntityDamageListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.MoveListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.PlayerQuitListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.SetupListener;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.ArenaManager;
import com.communitysurvivalgames.thesurvivalgames.managers.SignManager;
import com.communitysurvivalgames.thesurvivalgames.objects.JSign;
import com.communitysurvivalgames.thesurvivalgames.objects.PlayerData;
import com.communitysurvivalgames.thesurvivalgames.runnables.Scoreboard;
import com.communitysurvivalgames.thesurvivalgames.util.DoubleJump;
import com.communitysurvivalgames.thesurvivalgames.util.items.CarePackage;

public class TheSurvivalGames extends JavaPlugin {

	private static TheSurvivalGames plugin;
	private ConfigurationData configurationData;

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
		saveResource("enUS.lang", true);
		saveResource("idID.lang", true);
		saveResource("esES.lang", true);



		registerAll();
		setupDatabase();
		ArenaManager am = new ArenaManager(this);
		am.loadGames();

		getLogger().info(I18N.getLocaleString("BEEN_ENABLED"));
		getLogger().info(I18N.getLocaleString("COMMUNITY_PROJECT"));
		saveDefaultConfig();

		configurationData = new ConfigurationData(this);
	}

	@Override
	public void onDisable() {
		getLogger().info(I18N.getLocaleString("BEEN_DISABLED"));
	}

	void registerAll() {
		// register all commands and listeners
		getCommand("sg").setExecutor(new CommandHandler());
		getCommand("party").setExecutor(new PartyCommandHandler());

		CommandHandler.register("help", new com.communitysurvivalgames.thesurvivalgames.command.subcommands.HelpCommand());
		CommandHandler.register("create", new CreateCommand());
		CommandHandler.register("join", new com.communitysurvivalgames.thesurvivalgames.command.subcommands.JoinCommand());
		CommandHandler.register("leave", new com.communitysurvivalgames.thesurvivalgames.command.subcommands.LeaveCommand());
		CommandHandler.register("user", new UserCommand());
		CommandHandler.register("setlobby", new SetCommand());
		CommandHandler.register("setdeathmatch", new SetCommand());
		CommandHandler.register("setmaxplayers", new SetCommand());
		CommandHandler.register("setchest", new SetCommand());
		CommandHandler.register("setspawn", new SetCommand());
		CommandHandler.register("stop", new StopCommand());
		CommandHandler.register("start", new StartCommand());

		PartyCommandHandler.register("chat", new ChatCommand());
		PartyCommandHandler.register("decline", new DeclineCommand());
		PartyCommandHandler.register("help", new com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.HelpCommand());
		PartyCommandHandler.register("invite", new InviteCommand());
		PartyCommandHandler.register("join", new com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.JoinCommand());
		PartyCommandHandler.register("leave", new com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.LeaveCommand());
		PartyCommandHandler.register("list", new ListCommand());
		PartyCommandHandler.register("promote", new PromoteCommand());

		PluginManager pm = getServer().getPluginManager();

		pm.registerEvents(new BlockListener(), this);
		pm.registerEvents(new ChatListener(), this);
		pm.registerEvents(new PlayerQuitListener(), this);
		pm.registerEvents(new CarePackage(this), this);
		pm.registerEvents(new MoveListener(), this);
		pm.registerEvents(new SetupListener(), this);
		pm.registerEvents(new EntityDamageListener(), this);
		pm.registerEvents(new DoubleJump(this), this);

		SignManager.getSignManager().signs = getDatabase().find(JSign.class).findList();
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
		list.add(JSign.class);
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

	public ConfigurationData getPluginConfig() {
		return configurationData;
	}

	/**
	 * Only to be used if 100% needed
	 */
	public static TheSurvivalGames getPlugin() {
		return plugin;
	}
}
