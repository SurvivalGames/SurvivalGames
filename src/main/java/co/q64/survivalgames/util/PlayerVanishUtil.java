package co.q64.survivalgames.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import co.q64.survivalgames.TheSurvivalGames;

public class PlayerVanishUtil {

	public static void hideAll(final Player player) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(TheSurvivalGames.class), new Runnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					p.hidePlayer(player);
				}
			}
		});

	}

	public static void showAll(final Player player) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(TheSurvivalGames.class), new Runnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					p.showPlayer(player);
				}
			}
		});

	}
}
