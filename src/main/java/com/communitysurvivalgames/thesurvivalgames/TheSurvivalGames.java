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
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.communitysurvivalgames.thesurvivalgames.ability.Archer;
import com.communitysurvivalgames.thesurvivalgames.ability.Crafter;
import com.communitysurvivalgames.thesurvivalgames.ability.Enchanter;
import com.communitysurvivalgames.thesurvivalgames.ability.Knight;
import com.communitysurvivalgames.thesurvivalgames.ability.Pacman;
import com.communitysurvivalgames.thesurvivalgames.ability.Toxicologist;
import com.communitysurvivalgames.thesurvivalgames.ability.Zelda;
import com.communitysurvivalgames.thesurvivalgames.command.CommandHandler;
import com.communitysurvivalgames.thesurvivalgames.command.PartyCommandHandler;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.ChatCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.DeclineCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.InviteCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.ListCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.PromoteCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.CreateCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.RemoveCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.SetCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.SetKitSelectionLocationCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.StartCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.StopCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.TestCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.UserCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.VoteCommand;
import com.communitysurvivalgames.thesurvivalgames.configs.ArenaConfigTemplate;
import com.communitysurvivalgames.thesurvivalgames.configs.ConfigTemplate;
import com.communitysurvivalgames.thesurvivalgames.configs.ManagerConfigTemplate;
import com.communitysurvivalgames.thesurvivalgames.configs.WorldConfigTemplate;
import com.communitysurvivalgames.thesurvivalgames.kits.KitItem;
import com.communitysurvivalgames.thesurvivalgames.listeners.BlockListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.ChatListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.EntityDamageListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.EntityInteractListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.ItemDropListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.MoveListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.PlayerQuitListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.SetupListener;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.ArenaManager;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.multiworld.SGWorld;
import com.communitysurvivalgames.thesurvivalgames.objects.JSign;
import com.communitysurvivalgames.thesurvivalgames.objects.PlayerData;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;
import com.communitysurvivalgames.thesurvivalgames.proxy.BungeecordListener;
import com.communitysurvivalgames.thesurvivalgames.runnables.Scoreboard;
import com.communitysurvivalgames.thesurvivalgames.util.DoubleJump;
import com.communitysurvivalgames.thesurvivalgames.util.LocationChecker;
import com.communitysurvivalgames.thesurvivalgames.util.SerializedLocation;
import com.communitysurvivalgames.thesurvivalgames.util.ThrowableSpawnEggs;
import com.communitysurvivalgames.thesurvivalgames.util.items.CarePackage;

public class TheSurvivalGames extends JavaPlugin {

	private ConfigurationData configurationData;

	@Override
	public void onEnable() {
		ConfigurationSerialization.registerClass(SerializedLocation.class);
		ConfigurationSerialization.registerClass(LocationChecker.class);
        ConfigurationSerialization.registerClass(KitItem.class);

		SGApi.init(this);

		configurationData = new ConfigurationData();

		if (!configurationData.isBungeecordMode() && configurationData.isHub()) {
			Bukkit.getLogger().severe("How do you expect to have a hub server if you're not even running on Bungeecord Mode?");
			getServer().getPluginManager().disablePlugin(this);
		}

		SGApi.getScheduler();

		// TODO Add more languages!
		saveResource("enUS.lang", true);
		saveResource("idID.lang", true);
		saveResource("esES.lang", true);

		setupDatabase();

		File i18N = new File(getDataFolder(), "I18N.yml");
		if (!i18N.exists()) {
			saveResource("I18N.yml", false);
		}

		FileConfiguration lang = YamlConfiguration.loadConfiguration(i18N);

		I18N.setupLocale();
		I18N.setLocale(lang.getString("language"));

		if (getPluginConfig().isBungeecordMode()) {
			getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
			getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeecordListener());
		}

		registerAll();

		//TODO This file in never created and throws null pointers
		//ConfigTemplate<ArenaManager> configTemplate = new ManagerConfigTemplate(new File(getDataFolder().getAbsolutePath() + "/ArenaManager.yml"));
		//configTemplate.deserialize();

		if (!getPluginConfig().isHub())
			SGApi.getArenaManager().loadGames();
		getLogger().info(I18N.getLocaleString("BEEN_ENABLED"));
		getLogger().info(I18N.getLocaleString("COMMUNITY_PROJECT"));
		saveDefaultConfig();

	}

	@Override
	public void onDisable() {
		getLogger().info(I18N.getLocaleString("BEEN_DISABLED"));

		ConfigTemplate<ArenaManager> template = new ManagerConfigTemplate();
		template.serialize();

		for (SGArena arena : SGApi.getArenaManager().getArenas()) {
			ConfigTemplate<SGArena> configTemplate = new ArenaConfigTemplate(arena);
			configTemplate.serialize();
		}

		for (SGWorld world : SGApi.getMultiWorldManager().getWorlds()) {
			ConfigTemplate<SGWorld> configTemplate = new WorldConfigTemplate(world);
			configTemplate.serialize();
		}

		SGApi.getScheduler().shutdownAll();
	}

	void registerAll() {
		getCommand("sg").setExecutor(new CommandHandler());
		getCommand("party").setExecutor(new PartyCommandHandler());

		CommandHandler.register("help", new com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.HelpCommand());
		CommandHandler.register("create", new CreateCommand());
		CommandHandler.register("remove", new RemoveCommand());
		CommandHandler.register("join", new com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.JoinCommand());
		CommandHandler.register("leave", new com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.LeaveCommand());
		CommandHandler.register("user", new UserCommand());
		CommandHandler.register("setlobby", new SetCommand());
		CommandHandler.register("setdeathmatch", new SetCommand());
		CommandHandler.register("setmaxplayers", new SetCommand());
		CommandHandler.register("setchest", new SetCommand());
		CommandHandler.register("setspawn", new SetCommand());
		CommandHandler.register("stop", new StopCommand());
		CommandHandler.register("start", new StartCommand());
		CommandHandler.register("finish", new CreateCommand());
		CommandHandler.register("vote", new VoteCommand());
		CommandHandler.register("test", new TestCommand());
		CommandHandler.register("setkitselectionlocation", new SetKitSelectionLocationCommand());

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
		pm.registerEvents(new ItemDropListener(), this);
		pm.registerEvents(new ThrowableSpawnEggs(), this);
		pm.registerEvents(new EntityInteractListener(), this);

		pm.registerEvents(new Archer(), this);
		pm.registerEvents(new Crafter(), this);
		pm.registerEvents(new Knight(), this);
		pm.registerEvents(new Pacman(), this);
		pm.registerEvents(new Toxicologist(), this);
		pm.registerEvents(new Zelda(), this);
		pm.registerEvents(new Enchanter(), this);

		Scoreboard.registerScoreboard();
		SGApi.getEnchantmentManager().registerAll();
		
		SGApi.getKitManager().loadKits();
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
			getDatabase().find(JSign.class).findRowCount();
		} catch (PersistenceException ex) {
			System.out.println("Installing database for " + getDescription().getName() + " due to first time usage");
			installDDL();
		}

		//SGApi.getSignManager().signs = getDatabase().find(JSign.class).findList();
	}

	/**
	 * Gets Persistence Database classes WARNING: DO NOT EDIT
	 * 
	 * @return The list of classes for the database
	 */
	@Override
	public List<Class<?>> getDatabaseClasses() {
		List<Class<?>> list = new ArrayList<>();
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

}
