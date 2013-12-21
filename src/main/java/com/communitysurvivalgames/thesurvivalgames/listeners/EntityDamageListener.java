/**
 * Name: EntityDamageListener.java Created: 7 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.listeners;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.communitysurvivalgames.thesurvivalgames.TheSurvivalGames;
import com.communitysurvivalgames.thesurvivalgames.managers.ArenaManager;

public class EntityDamageListener implements Listener {

	/**
	 * Listens for a player being hit by a snow ball, gives player Slowness II
	 * for 30 seconds
	 *
	 * @param event - The EntityDamageByEntityEvent event
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		Entity entity = event.getDamager();
		if (event.getEntity() instanceof Player) {
			Player damaged = (Player) event.getEntity();
			if (ArenaManager.getManager().isInGame(damaged)) {
				if (entity instanceof Snowball) {
					event.setDamage(3);
					damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 2, false));
					damaged.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1, false));
				}
				if ((damaged.getHealth() - event.getDamage()) <= 0) {
					event.setCancelled(true);
					damaged.setHealth(20);
					damaged.setVelocity(new Vector(0, 0, 0.5));
					damaged.setGameMode(GameMode.CREATIVE);
					damaged.setAllowFlight(true);
					damaged.setFlying(true);
					damaged.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 1, false)); //Just for effect
					if (entity instanceof Player) {
						Player damager = (Player) entity;
						ArenaManager.getManager().getArena(damager).broadcast(ChatColor.translateAlternateColorCodes('&', "&e&l" + damaged.getDisplayName() + " &r&6was killed by &e&l" + damager.getDisplayName() + " &r&6with a &e&l" + damager.getInventory().getItemInHand()));
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
				TheSurvivalGames.getPlugin().getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&e&l" + damaged.getDisplayName() + " &r&6somehow managed to die whilst in the lobby because of &e&l" + event.getDamager()));
			}
		}
	}
}
