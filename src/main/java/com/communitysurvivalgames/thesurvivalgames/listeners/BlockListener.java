/**
 * Name: BlockListener.java 
 * Created: 9 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.listeners;

import java.util.ArrayList;
import java.util.List;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {
    // TODO This is a list of blocks that can be broken in game
    // Might need to list each type of leaf or grass will have to check will
    // impliment later
    final List<Material> allowed;

    public BlockListener() {

        allowed = new ArrayList<>();
        for (String s : SGApi.getPlugin().getPluginConfig().getAllowedBlockBreaks()) {
            allowed.add(Material.valueOf(s));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (SGApi.getArenaManager().isInGame(event.getPlayer())) {
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
