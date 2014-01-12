package com.communitysurvivalgames.thesurvivalgames;

import com.communitysurvivalgames.thesurvivalgames.signs.SignLayout;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigurationData {
    private final TheSurvivalGames plugin;
    private FileConfiguration config;
    private final Map<String, SignLayout> signLayouts = new HashMap<String, SignLayout>();

    public ConfigurationData(TheSurvivalGames plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    void loadConfig() {
        this.plugin.saveDefaultConfig();
        this.plugin.reloadConfig();
        this.config = this.plugin.getConfig();
        loadLayouts();
    }

    public void reloadConfig() {
        this.signLayouts.clear();
        loadConfig();
    }

    private void loadLayouts() {
        ConfigurationSection layouts = this.config.getConfigurationSection("signs.layouts");
        for (String layout : layouts.getKeys(false)) {
            ConfigurationSection cs = layouts.getConfigurationSection(layout);
            String join = cs.getString("join");
            String cantJoin = cs.getString("cantJoin");
            List<String> lines = cs.getStringList("layout");
            boolean teleport = cs.getBoolean("teleport");
            SignLayout signLayout = new SignLayout(layout, join, cantJoin, lines, teleport);
            this.signLayouts.put(layout, signLayout);
        }
    }

    public SignLayout getLayout(String layout) {
        return this.signLayouts.get(layout);
    }

    public TheSurvivalGames getPlugin() {
        return this.plugin;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    //CONFIG VALUES START HERE

    public boolean allowDoubleJump() {
        return this.config.getBoolean("allow-double-jump");
    }

    public boolean allowDoubleJumpIG() {
        return this.config.getBoolean("allow-double-jump-IG");
    }

    public boolean doBloodEffect() {
        return this.config.getBoolean("blood-effect");
    }

    public int getStartingPoints() {
        return this.config.getInt("starting-points");
    }

    public List<String> getWelcomeMessage() {
        return this.config.getStringList("welcome-message");
    }

}
