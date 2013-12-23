/**
 * Name: TnT.java Created: 9 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.listeners;

import com.communitysurvivalgames.thesurvivalgames.managers.ArenaManager;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener {

    // Instantly exploding TnT, see description on dev.bukkit.org.
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (ArenaManager.getManager().isInGame(event.getPlayer())) {
            if (event.getBlock().getType().equals(Material.TNT)) {
                event.getBlock().setType(Material.AIR);
                event.getPlayer().getWorld().spawnEntity(event.getBlock().getLocation(), EntityType.PRIMED_TNT);
            }
        }
    }
}
