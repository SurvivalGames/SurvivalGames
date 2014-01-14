package com.communitysurvivalgames.thesurvivalgames.util;

import com.communitysurvivalgames.thesurvivalgames.TheSurvivalGames;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

// NOT NOT EDIT THIS FILE RELICUM IS RECODING IT //
public class PlayerVanishUtil {

    public static void hideAll(final SGArena arena, final Player player) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(TheSurvivalGames.getPlugin(TheSurvivalGames.class), new Runnable() {
           @Override
            public void run() {
                for (int i = 0; i < arena.getPlayers().size(); i++) {
                    if (!player.hasPermission("sg.seedeadplayers"))
                        Bukkit.getPlayer(arena.getPlayers().get(i)).hidePlayer(player);
                }
            }
        });

    }

    public static void showAll(final Player player) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(TheSurvivalGames.getPlugin(TheSurvivalGames.class), new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < Bukkit.getServer().getOnlinePlayers().length; i++) {
                    Bukkit.getServer().getOnlinePlayers()[i].showPlayer(player);
                }
            }
        });

    }
}
