/**
 * Name: EntityDamageListener.java Created: 7 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.listeners;

import com.communitysurvivalgames.thesurvivalgames.managers.ArenaManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
        if (entity instanceof Snowball) {
            if (event.getEntity() instanceof Player) {
                Player damaged = (Player) event.getEntity();
                if (ArenaManager.getManager().isInGame(damaged)) {
                    damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 2, false));
                    damaged.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1, false));
                }
            }
        }
    }
}
