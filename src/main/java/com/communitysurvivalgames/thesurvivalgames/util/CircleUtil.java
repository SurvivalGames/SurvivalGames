package com.communitysurvivalgames.thesurvivalgames.util;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;

public class CircleUtil {
	public static void playFireworkCircle(final Player player, final FireworkEffect effect, int size, final int distance) {
		Bukkit.getLogger().info("Called firework method");
		int index = 0;
		for (double t = 0; t < 2 * Math.PI; t += Math.toRadians(size)) {
			index += 3;
			Location l = player.getLocation().add(Math.cos(t) * distance, 0.5, Math.sin(t) * distance);
			final Location fLoc = l;
			Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {
				@Override
				public void run() {
					Bukkit.getLogger().info("Got location" + fLoc.toString());
					try {
						FireworkEffectPlayer.getFireworkEffectPlayer().playFirework(player.getWorld(), fLoc, effect);
						Bukkit.getLogger().info("Played it!");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}, index);

		}
	}
}
