package com.communitysurvivalgames.thesurvivalgames.util;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;

public class CircleUtil implements Listener {

	static CircleUtil circleUtil = new CircleUtil();

	public static CircleUtil getCircleUtil() {
		return circleUtil;
	}

	public void playFireworkCircle(final Player player, Location fLoc, final FireworkEffect effect, int size, final int distance) {
		Bukkit.getLogger().info("Called firework method");
		int index = 0;
		for (double t = 0; t < 2 * Math.PI; t += Math.toRadians(size)) {
			index += 3;
			Location l = fLoc.add(Math.cos(t) * distance, 0.5, Math.sin(t) * distance);
			Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new PlayFireworkEffect(player, l, effect), index);

		}
	}
	
	public void playFireworkCircle(final Player player, final FireworkEffect effect, int size, final int distance) {
		Bukkit.getLogger().info("Called firework method");
		int index = 0;
		for (double t = 0; t < 2 * Math.PI; t += Math.toRadians(size)) {
			index += 3;
			Location l = player.getLocation().add(Math.cos(t) * distance, 0.5, Math.sin(t) * distance);
			Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new PlayFireworkEffect(player, l, effect), index);

		}
	}

	public void playFireworkRing(final Player player, final FireworkEffect effect, int size, final int distance) {
		Bukkit.getLogger().info("Called firework method");
		int index = 0;
		for (double t = 0; t < 2 * Math.PI; t += Math.toRadians(size)) {
			index += 3;
			Location l = player.getLocation().add(Math.cos(t) * distance, 0.5, Math.sin(t) * distance);
			Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new PlayFireworkEffect(player, l, effect), index);

		}

		for (double t = 0; t < 2 * Math.PI; t += Math.toRadians(size)) {
			index += 3;
			Location l = player.getLocation().add(Math.cos(t) * distance, 0.5, Math.sin(t) * distance);
			Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new PlayFireworkEffect(player, l, effect), index);
		}
	}

	public class PlayFireworkEffect implements Runnable {
		Player player;
		Location fLoc;
		FireworkEffect effect;

		public PlayFireworkEffect(Player player, Location fLoc, FireworkEffect fEffect) {
			this.player = player;
			this.fLoc = fLoc;
			this.effect = fEffect;
		}

		@Override
		public void run() {
			try {
				FireworkEffectPlayer.getFireworkEffectPlayer().playFirework(player.getWorld(), fLoc, effect);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
