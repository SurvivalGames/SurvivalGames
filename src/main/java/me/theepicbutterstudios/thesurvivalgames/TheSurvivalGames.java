package me.theepicbutterstudios.thesurvivalgames;

import com.gmail.woodyc40.arenaapi.*;

import org.bukkit.plugin.java.JavaPlugin;

public class TheSurvivalGames extends JavaPlugin  {

    public void onEnable() {
        new ArenaManager(this);
    }

    public void onDisable() {

    }

}
