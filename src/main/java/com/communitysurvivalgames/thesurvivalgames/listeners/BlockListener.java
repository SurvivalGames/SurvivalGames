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
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockPlace(BlockPlaceEvent event) {
		if (ArenaManager.getManager().isInGame(event.getPlayer())) {
			if (event.getBlock().getType().equals(Material.TNT)) {
				event.getPlayer().getWorld().spawnEntity(event.getBlock().getLocation(), EntityType.PRIMED_TNT);
			}
			event.setCancelled(true);
		}

		if (!event.getPlayer().hasPermission("sg.build")) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event) {
		if (!event.getPlayer().hasPermission("sg.build")) {
			event.setCancelled(true);
		}
	}
}
