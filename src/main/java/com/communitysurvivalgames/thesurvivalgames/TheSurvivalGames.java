/**
 * Name: TheSurvivalGames.java Created: 19 November 2013 Edited: 9 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames;

import src.main.java.com.communitysurvivalgames.thesurvivalgames.ability.Archer;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.ability.Crafter;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.ability.Enchanter;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.ability.Knight;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.ability.Notch;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.ability.Pacman;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.ability.Skeleton;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.ability.Toxicologist;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.ability.Zelda;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.command.CommandHandler;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.command.PartyCommandHandler;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.command.standalone.SponsorCommand;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.command.standalone.TpxCommand;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.ChatCommand;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.DeclineCommand;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.InviteCommand;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.ListCommand;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.PromoteCommand;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.CreateCommand;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.RemoveCommand;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.SetCommand;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.SetKitSelectionLocationCommand;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.StartCommand;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.StopCommand;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.TestCommand;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.UserCommand;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.VoteCommand;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.configs.ArenaConfigTemplate;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.configs.ConfigTemplate;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.configs.ManagerConfigTemplate;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.configs.WorldConfigTemplate;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.kits.KitItem;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.listeners.BlockListener;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.listeners.ChatListener;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.listeners.EntityDamageListener;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.listeners.EntityInteractListener;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.listeners.ItemDropListener;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.listeners.MoveListener;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.listeners.PlayerQuitListener;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.listeners.SetupListener;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.managers.ArenaManager;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.multiworld.SGWorld;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.objects.JSign;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.objects.PlayerData;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.objects.SGArena;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.proxy.BungeecordListener;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.runnables.Scoreboard;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.util.DoubleJump;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.util.LocationChecker;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.util.SerializedLocation;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.util.ThrowableSpawnEggs;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.util.items.CarePackage;

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

		new File(getDataFolder(), "maps").mkdirs();
		new File(getDataFolder(), "arenas").mkdirs();

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

		saveResource("ArenaManager.yml", false);
		ConfigTemplate<ArenaManager> configTemplate = new ManagerConfigTemplate();
		configTemplate.deserialize();

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
			Bukkit.getLogger().info("Attemping to save arena: " + arena.toString());
			ConfigTemplate<SGArena> configTemplate = new ArenaConfigTemplate(arena);
			configTemplate.serialize();
		}

		for (SGWorld world : SGApi.getMultiWorldManager().getWorlds()) {
			Bukkit.getLogger().info("Attempting to save world: " + world);
			ConfigTemplate<SGWorld> configTemplate = new WorldConfigTemplate(world);
			configTemplate.serialize();
		}

		SGApi.getScheduler().shutdownAll();
	}

	void registerAll() {
		getCommand("sg").setExecutor(new CommandHandler());
		getCommand("party").setExecutor(new PartyCommandHandler());
		
		//I want the user based commands (ex. /kit /vote /sponsor) to not have the /sg prefix. Looks neater.   - Quantum64
		getCommand("sponsor").setExecutor(new SponsorCommand());
		getCommand("tpx").setExecutor(new TpxCommand());

		CommandHandler.register("help", new com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.HelpCommand());
		CommandHandler.register("create", new CreateCommand());
		CommandHandler.register("remove", new RemoveCommand());
		CommandHandler.register("join", new com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.JoinCommand());
		CommandHandler.register("leave", new com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.LeaveCommand());
		CommandHandler.register("user", new UserCommand());
		CommandHandler.register("createlobby", new SetCommand());
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
		pm.registerEvents(new Enchanter(), this);
		pm.registerEvents(new Knight(), this);
		pm.registerEvents(new Notch(), this);
		pm.registerEvents(new Pacman(), this);
		pm.registerEvents(new Skeleton(), this);
		pm.registerEvents(new Toxicologist(), this);
		pm.registerEvents(new Zelda(), this);


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
