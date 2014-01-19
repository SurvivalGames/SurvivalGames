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

import com.communitysurvivalgames.thesurvivalgames.command.CommandHandler;
import com.communitysurvivalgames.thesurvivalgames.command.PartyCommandHandler;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.*;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.*;
import com.communitysurvivalgames.thesurvivalgames.listeners.*;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.objects.Arena;
import com.communitysurvivalgames.thesurvivalgames.objects.JSign;
import com.communitysurvivalgames.thesurvivalgames.objects.PlayerData;
import com.communitysurvivalgames.thesurvivalgames.runnables.Scoreboard;
import com.communitysurvivalgames.thesurvivalgames.util.DoubleJump;
import com.communitysurvivalgames.thesurvivalgames.util.SerializedLocation;
import com.communitysurvivalgames.thesurvivalgames.util.items.CarePackage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TheSurvivalGames extends JavaPlugin {

    private ConfigurationData configurationData;

    @Override
    public void onEnable() {

        ConfigurationSerialization.registerClass(SerializedLocation.class);
        ConfigurationSerialization.registerClass(Arena.class);


       SGApi.init(this);

        configurationData = new ConfigurationData();

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

        registerAll();

        SGApi.getArenaManager().loadGames();
        getLogger().info(I18N.getLocaleString("BEEN_ENABLED"));
        getLogger().info(I18N.getLocaleString("COMMUNITY_PROJECT"));
        saveDefaultConfig();

    }

    @Override
    public void onDisable() {
        getLogger().info(I18N.getLocaleString("BEEN_DISABLED"));
    }

    void registerAll() {
        getCommand("sg").setExecutor(new CommandHandler());
        getCommand("party").setExecutor(new PartyCommandHandler());

        CommandHandler.register("help", new com.communitysurvivalgames.thesurvivalgames.command.subcommands.HelpCommand());
        CommandHandler.register("create", new CreateCommand());
        CommandHandler.register("remove", new RemoveCommand());
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
        CommandHandler.register("finish", new CreateCommand());

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

        // Throws NPE's
        // SGApi.getSignManager().signs =
        // getDatabase().find(JSign.class).findList();
        Scoreboard.registerScoreboard();
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
    }

    /**
     * Gets Persistence Database classes WARNING: DO NOT EDIT
     * 
     * @return The list of classes for the database
     */
    @Override
    public List<Class<?>> getDatabaseClasses() {
        @SuppressWarnings("Convert2Diamond")
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
