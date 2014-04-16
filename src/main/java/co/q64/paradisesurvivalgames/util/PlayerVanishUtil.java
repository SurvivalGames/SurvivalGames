package co.q64.paradisesurvivalgames.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import co.q64.paradisesurvivalgames.TheSurvivalGames;

public class PlayerVanishUtil {

	public static void hideAll(final Player player) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(TheSurvivalGames.class), new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < Bukkit.getServer().getOnlinePlayers().length; i++) {
					Bukkit.getServer().getOnlinePlayers()[i].hidePlayer(player);
				}
			}
		});

	}

	public static void showAll(final Player player) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(TheSurvivalGames.class), new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < Bukkit.getServer().getOnlinePlayers().length; i++) {
					Bukkit.getServer().getOnlinePlayers()[i].showPlayer(player);
				}
			}
		});

	}
}
