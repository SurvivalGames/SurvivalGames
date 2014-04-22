/**
 * Name: TheSurvivalGames.java Created: 19 November 2013 Edited: 9 December 2013
 *
 * @version 1.0.0
 */

package co.q64.paradisesurvivalgames;

import java.io.File;
import java.io.IOException;
import java.net.BindException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import co.q64.paradisesurvivalgames.ability.Archer;
import co.q64.paradisesurvivalgames.ability.Crafter;
import co.q64.paradisesurvivalgames.ability.Enchanter;
import co.q64.paradisesurvivalgames.ability.Knight;
import co.q64.paradisesurvivalgames.ability.Notch;
import co.q64.paradisesurvivalgames.ability.Pacman;
import co.q64.paradisesurvivalgames.ability.Pig;
import co.q64.paradisesurvivalgames.ability.Skeleton;
import co.q64.paradisesurvivalgames.ability.Toxicologist;
import co.q64.paradisesurvivalgames.ability.Zelda;
import co.q64.paradisesurvivalgames.command.CommandHandler;
import co.q64.paradisesurvivalgames.command.PartyCommandHandler;
import co.q64.paradisesurvivalgames.command.standalone.SponsorCommand;
import co.q64.paradisesurvivalgames.command.standalone.TpxCommand;
import co.q64.paradisesurvivalgames.command.subcommands.party.ChatCommand;
import co.q64.paradisesurvivalgames.command.subcommands.party.DeclineCommand;
import co.q64.paradisesurvivalgames.command.subcommands.party.InviteCommand;
import co.q64.paradisesurvivalgames.command.subcommands.party.ListCommand;
import co.q64.paradisesurvivalgames.command.subcommands.party.PromoteCommand;
import co.q64.paradisesurvivalgames.command.subcommands.sg.AdminCommand;
import co.q64.paradisesurvivalgames.command.subcommands.sg.CreateCommand;
import co.q64.paradisesurvivalgames.command.subcommands.sg.RemoveCommand;
import co.q64.paradisesurvivalgames.command.subcommands.sg.RemoveKitSelectionLocationCommand;
import co.q64.paradisesurvivalgames.command.subcommands.sg.SetCommand;
import co.q64.paradisesurvivalgames.command.subcommands.sg.SetKitSelectionLocationCommand;
import co.q64.paradisesurvivalgames.command.subcommands.sg.StartCommand;
import co.q64.paradisesurvivalgames.command.subcommands.sg.StopCommand;
import co.q64.paradisesurvivalgames.command.subcommands.sg.TestCommand;
import co.q64.paradisesurvivalgames.command.subcommands.sg.UserCommand;
import co.q64.paradisesurvivalgames.command.subcommands.sg.VoteCommand;
import co.q64.paradisesurvivalgames.configs.ArenaConfigTemplate;
import co.q64.paradisesurvivalgames.configs.ConfigTemplate;
import co.q64.paradisesurvivalgames.configs.ManagerConfigTemplate;
import co.q64.paradisesurvivalgames.configs.WorldConfigTemplate;
import co.q64.paradisesurvivalgames.kits.KitItem;
import co.q64.paradisesurvivalgames.listeners.BlockListener;
import co.q64.paradisesurvivalgames.listeners.ChatListener;
import co.q64.paradisesurvivalgames.listeners.ChestListener;
import co.q64.paradisesurvivalgames.listeners.EntityDamageListener;
import co.q64.paradisesurvivalgames.listeners.EntityInteractListener;
import co.q64.paradisesurvivalgames.listeners.ItemDropListener;
import co.q64.paradisesurvivalgames.listeners.MobSpawnListener;
import co.q64.paradisesurvivalgames.listeners.MoveListener;
import co.q64.paradisesurvivalgames.listeners.PlayerLoginListener;
import co.q64.paradisesurvivalgames.listeners.PlayerQuitListener;
import co.q64.paradisesurvivalgames.listeners.SetupListener;
import co.q64.paradisesurvivalgames.listeners.SignListener;
import co.q64.paradisesurvivalgames.listeners.SoundEffectsListener;
import co.q64.paradisesurvivalgames.locale.I18N;
import co.q64.paradisesurvivalgames.managers.ArenaManager;
import co.q64.paradisesurvivalgames.managers.ItemManager;
import co.q64.paradisesurvivalgames.managers.SGApi;
import co.q64.paradisesurvivalgames.multiworld.SGWorld;
import co.q64.paradisesurvivalgames.net.WebsocketServer;
import co.q64.paradisesurvivalgames.objects.PlayerData;
import co.q64.paradisesurvivalgames.objects.SGArena;
import co.q64.paradisesurvivalgames.proxy.BungeecordListener;
import co.q64.paradisesurvivalgames.rollback.ChangedBlock;
import co.q64.paradisesurvivalgames.runnables.Scoreboard;
import co.q64.paradisesurvivalgames.tracking.AnalyticsConfigData;
import co.q64.paradisesurvivalgames.tracking.JGoogleAnalyticsTracker;
import co.q64.paradisesurvivalgames.tracking.JGoogleAnalyticsTracker.GoogleAnalyticsVersion;
import co.q64.paradisesurvivalgames.tracking.PluginMetrics;
import co.q64.paradisesurvivalgames.util.DoubleJump;
import co.q64.paradisesurvivalgames.util.LocationChecker;
import co.q64.paradisesurvivalgames.util.SerializedLocation;
import co.q64.paradisesurvivalgames.util.ThrowableSpawnEggs;
import co.q64.paradisesurvivalgames.util.items.CarePackage;
import co.q64.paradisesurvivalgames.util.items.RailGun;

public class TheSurvivalGames extends JavaPlugin {

	private Chat chat = null;
	private ConfigurationData configurationData;
	private Economy econ = null;
	private JGoogleAnalyticsTracker tracker;
	private PluginMetrics tgicnTracker;

	public Chat getChat() {
		return chat;
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
		return list;
	}

	public Economy getEcon() {
		return econ;
	}

	public PlayerData getPlayerData(Player player) {
		PlayerData data = getDatabase().find(PlayerData.class).where().ieq("playerName", player.getName()).findUnique();
		if (data == null) {
			data = new PlayerData(player);
			setPlayerData(data);
		}
		return data;
	}

	public ConfigurationData getPluginConfig() {
		return configurationData;
	}

	public String getPrefix(Player p) {
		return chat.getPlayerPrefix(p);
	}

	public JGoogleAnalyticsTracker getTracker() {
		return tracker;
	}

	@Override
	public void onDisable() {
		getLogger().info(I18N.getLocaleString("BEEN_DISABLED"));

		ConfigTemplate<ArenaManager> template = new ManagerConfigTemplate();
		template.serialize();

		for (SGArena arena : SGApi.getArenaManager().getArenas()) {
			List<ChangedBlock> data = arena.getChangedBlocks();

			for (int i = 0; i < data.size(); i++) {
				Bukkit.getLogger().info("Resetting block: " + data.get(i).getPrevid().toString());
				Location l = new Location(Bukkit.getWorld(data.get(i).getWorld()), data.get(i).getX(), data.get(i).getY(), data.get(i).getZ());
				Block b = l.getBlock();
				b.setType(data.get(i).getPrevid());
				b.setData(data.get(i).getPrevdata());
				b.getState().update();
			}
		}

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
		try {
			tgicnTracker.disable();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onEnable() {
		ConfigurationSerialization.registerClass(SerializedLocation.class);
		ConfigurationSerialization.registerClass(LocationChecker.class);
		ConfigurationSerialization.registerClass(KitItem.class);

		SGApi.init(this);

		saveDefaultConfig();

		configurationData = new ConfigurationData();

		if (!configurationData.isBungeecordMode() && configurationData.isHub()) {
			Bukkit.getLogger().severe("How do you expect to have a hub server if you're not even running on " + "Bungeecord Mode?");
			getServer().getPluginManager().disablePlugin(this);
		}

		new File(getDataFolder(), "maps").mkdirs();
		new File(getDataFolder(), "arenas").mkdirs();

		SGApi.getScheduler();
		SGApi.getSignManager();

		// TODO Add more languages!
		saveResource("locale/enUS.lang", true);
		saveResource("locale/idID.lang", true);
		saveResource("locale/esES.lang", true);

		setupEconomy();
		setupChat();
		setupDatabase();

		File i18N = new File(getDataFolder(), "locale/I18N.yml");
		if (!i18N.exists()) {
			saveResource("locale/I18N.yml", false);
		}

		FileConfiguration lang = YamlConfiguration.loadConfiguration(i18N);

		I18N.setupLocale();
		I18N.setLocale(lang.getString("language"));

		if (getPluginConfig().isBungeecordMode()) {
			getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
			getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeecordListener());
		}

		registerAll();

		File arenaManagerConfig = new File(getDataFolder(), "ArenaManager.yml"); // Added to fix bug #11 which was an "Annoyance" in the console.
		if (!arenaManagerConfig.exists()) {
			saveResource("ArenaManager.yml", false);
		}
		ConfigTemplate<ArenaManager> configTemplate = new ManagerConfigTemplate();
		configTemplate.deserialize();

		if (!getPluginConfig().isHub())
			SGApi.getArenaManager().loadGames();

		if (getPluginConfig().getUseServers()) {
			try {
				WebsocketServer.runServer();
			} catch (BindException e){
				Bukkit.getLogger().warning("Failed to bind port 8887 - if you just used /reload, ignore this error, otherwise be sure to set enable-servers to false in your config, or make sure port 8887 is open and not in use.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		AnalyticsConfigData config = new AnalyticsConfigData("UA-49716599-1");
		config.setUserAgent("Java/" + System.getProperty("java.version") + " : Bukkit/" + Bukkit.getVersion() + " (" + System.getProperty("os.name") + "; " + System.getProperty("os.arch") + ")");
		config.setFlashVersion("9.0 r24");
		tracker = new JGoogleAnalyticsTracker(config, GoogleAnalyticsVersion.V_4_7_2);
		tracker.setEnabled(true);
		tracker.trackEvent("Server Start", "Motd: " + ChatColor.stripColor(Bukkit.getMotd()) + ", " + "Max Players: " + Bukkit.getMaxPlayers() + ", Version: " + Bukkit.getVersion() + " running on " + Bukkit.getBukkitVersion() + ", Java: " + System.getProperty("java.version"));

		try {
			tgicnTracker = new PluginMetrics(this);
			tgicnTracker.enable();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		getLogger().info("ParadiseSurvivalGames has been enabeled!");
	}

	void registerAll() {
		getCommand("sg").setExecutor(new CommandHandler());
		getCommand("party").setExecutor(new PartyCommandHandler());

		//I want the user based commands (ex. /kit /vote /sponsor) to not have the /sg prefix. Looks neater.   -
		// Quantum64
		getCommand("sponsor").setExecutor(new SponsorCommand());
		getCommand("tpx").setExecutor(new TpxCommand());

		CommandHandler.register("help", new co.q64.paradisesurvivalgames.command.subcommands.sg.HelpCommand());
		CommandHandler.register("create", new CreateCommand());
		CommandHandler.register("remove", new RemoveCommand());
		CommandHandler.register("join", new co.q64.paradisesurvivalgames.command.subcommands.sg.JoinCommand());
		CommandHandler.register("leave", new co.q64.paradisesurvivalgames.command.subcommands.sg.LeaveCommand());
		CommandHandler.register("user", new UserCommand());
		CommandHandler.register("createlobby", new SetCommand());
		CommandHandler.register("setdeathmatch", new SetCommand());
		CommandHandler.register("setmaxplayers", new SetCommand());
		CommandHandler.register("setminplayers", new SetCommand());
		CommandHandler.register("setchest", new SetCommand());
		CommandHandler.register("setspawn", new SetCommand());
		CommandHandler.register("stop", new StopCommand());
		CommandHandler.register("start", new StartCommand());
		CommandHandler.register("finish", new CreateCommand());
		CommandHandler.register("vote", new VoteCommand());
		CommandHandler.register("test", new TestCommand());
		CommandHandler.register("admin", new AdminCommand());
		CommandHandler.register("setkitselectionlocation", new SetKitSelectionLocationCommand());
		CommandHandler.register("removekitselectionlocation", new RemoveKitSelectionLocationCommand());

		PartyCommandHandler.register("chat", new ChatCommand());
		PartyCommandHandler.register("decline", new DeclineCommand());
		PartyCommandHandler.register("help", new co.q64.paradisesurvivalgames.command.subcommands.party.HelpCommand());
		PartyCommandHandler.register("invite", new InviteCommand());
		PartyCommandHandler.register("join", new co.q64.paradisesurvivalgames.command.subcommands.party.JoinCommand());
		PartyCommandHandler.register("leave", new co.q64.paradisesurvivalgames.command.subcommands.party.LeaveCommand());
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
		pm.registerEvents(new SignListener(), this);
		pm.registerEvents(new ChestListener(), this);
		pm.registerEvents(new RailGun(), this);
		pm.registerEvents(new MobSpawnListener(), this);
		pm.registerEvents(new PlayerLoginListener(), this);
		pm.registerEvents(new SoundEffectsListener(), this);

		pm.registerEvents(new Archer(), this);
		pm.registerEvents(new Crafter(), this);
		pm.registerEvents(new Enchanter(), this);
		pm.registerEvents(new Knight(), this);
		pm.registerEvents(new Notch(), this);
		pm.registerEvents(new Pacman(), this);
		pm.registerEvents(new Pig(), this);
		pm.registerEvents(new Skeleton(), this);
		pm.registerEvents(new Toxicologist(), this);
		pm.registerEvents(new Zelda(), this);

		Scoreboard.registerScoreboard();
		ItemManager.register();

		SGApi.getEnchantmentManager().registerAll();

		SGApi.getKitManager().loadKits();
	}

	public void setPlayerData(PlayerData data) {
		getDatabase().save(data);
	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
		if (chatProvider != null) {
			chat = chatProvider.getProvider();
		}

		return (chat != null);
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

		//SGApi.getSignManager().signs = getDatabase().find(JSign.class).findList();
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			econ = economyProvider.getProvider();
		}

		return (econ != null);
	}

	public boolean useChat() {
		return (chat != null);
	}

	public boolean useEcon() {
		return (econ != null);
	}
}
