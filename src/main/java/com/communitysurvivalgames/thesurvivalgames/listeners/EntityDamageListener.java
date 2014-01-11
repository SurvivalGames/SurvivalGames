/**
 * Name: EntityDamageListener.java Created: 7 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.communitysurvivalgames.thesurvivalgames.TheSurvivalGames;
import com.communitysurvivalgames.thesurvivalgames.exception.ArenaNotFoundException;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.ArenaManager;
import com.communitysurvivalgames.thesurvivalgames.util.PlayerVanishUtil;

public class EntityDamageListener implements Listener {

	/**
	 * Listens for a player being hit by a snow ball, gives player Slowness II
	 * for 30 seconds
	 *
	 * @param event - The EntityDamageByEntityEvent event
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamageByEntity(final EntityDamageByEntityEvent event) {
		if (TheSurvivalGames.getPlugin().getPluginConfig().doBloodEffect()) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(TheSurvivalGames.getPlugin(), new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < event.getDamage(); i++) {
						event.getDamager().getWorld().playEffect(event.getEntity().getLocation().add(0.0D, 0.8D, 0.0D), Effect.STEP_SOUND, Material.REDSTONE_WIRE);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
						}
					}
				}
			});
		}

		Entity entity = event.getDamager();
		if (event.getEntity() instanceof Player) {
			Player damaged = (Player) event.getEntity();
			if (ArenaManager.getManager().isInGame(damaged)) {
				if (entity instanceof Snowball) {
					event.setDamage(3);
					damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 2, false));
					damaged.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1, false));
				}
				if (entity instanceof Egg) {
					event.setDamage(16);
					damaged.getWorld().strikeLightning(damaged.getLocation());
					damaged.getWorld().strikeLightning(damaged.getLocation());
				}
				if ((damaged.getHealth() - event.getDamage()) <= 0) {
					event.setCancelled(true);
					damaged.setHealth(20);
					damaged.setVelocity(new Vector(0, 0, 0.5));
					damaged.setGameMode(GameMode.CREATIVE);
					damaged.setAllowFlight(true);
					damaged.setFlying(true);
					damaged.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 1, false)); //Just for effect

					for (int i = 0; i < 4; i++)
						fireworkIt(event.getDamager().getLocation());

					if (entity instanceof Player) {
						Player damager = (Player) entity;
						try {
							ArenaManager.getManager().getArena(damager).broadcast(ChatColor.translateAlternateColorCodes('&', "&e&l" + damaged.getDisplayName() + " &r&6" + I18N.getLocaleString("KILLED_BY") + " &e&l" + damager.getDisplayName() + " &r&6" + I18N.getLocaleString("WITH_A") + " &e&l" + damager.getInventory().getItemInHand()));
						} catch (ArenaNotFoundException e) {
							e.printStackTrace();
						}
					}

					try {
						PlayerVanishUtil.hideAll(ArenaManager.getManager().getArena(damaged), damaged);
					} catch (ArenaNotFoundException e) {
						e.printStackTrace();
					}
				}
				return;
			}
			if ((damaged.getHealth() - event.getDamage()) <= 0) {
				event.setCancelled(true);
				damaged.setHealth(20);
				damaged.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 1, false));
				damaged.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 40, 1, false));
				damaged.setVelocity(new Vector(0, 0, 0.5));
				TheSurvivalGames.getPlugin().getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&e&l" + damaged.getDisplayName() + " &r&6" + I18N.getLocaleString("FAIL") + " &e&l" + event.getDamager()));
			}
		}
	}

	public void fireworkIt(Location loc) {

		//Spawn the Firework, get the FireworkMeta.
		Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		FireworkMeta fwm = fw.getFireworkMeta();

		//Our random generator
		Random r = new Random();

		//Get the type
		int rt = r.nextInt(5) + 1;
		Type type = Type.BALL;
		if (rt == 1)
			type = Type.BALL;
		if (rt == 2)
			type = Type.BALL_LARGE;
		if (rt == 3)
			type = Type.BURST;
		if (rt == 4)
			type = Type.CREEPER;
		if (rt == 5)
			type = Type.STAR;

		//Get our random colors   
		int r1i = r.nextInt(255) + 1;
		int r2i = r.nextInt(255) + 1;
		int r3i = r.nextInt(255) + 1;
		Color c1 = Color.fromRGB(r1i, r2i, r3i);
		Color c2 = Color.fromRGB(r1i, r2i, r3i);

		//Create our effect with this
		FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();

		//Then apply the effect to the meta
		fwm.addEffect(effect);

		//Generate some random power and set it
		int rp = r.nextInt(2) + 1;
		fwm.setPower(rp);

		//Then apply this to our rocket
		fw.setFireworkMeta(fwm);

	}
}
