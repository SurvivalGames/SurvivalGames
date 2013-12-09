package me.theepicbutterstudios.thesurvivalgames.ingame;

import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.entity.Player;
import org.bukkit.entity.EntityType;

/* Class TnT
 *
 * Created 9 December 2013
 *
 * Edited never
 *\


public class TnT {
    // Instantly exploding TnT, see description on dev.bukkit.org.
    @EventHandler(priority=EventPriority.NORMAL)
    public void onBlockPlace(BlockPlaceEvent event)
    {
        			 if(event.getBlock().getType().equals(Material.TNT)){
			        	event.getBlock().setType(Material.AIR); 
			        	event.getPlayer().getWorld().spawnEntity(event.getBlock().getLocation(), EntityType.PRIMED_TNT);
			        	return;
					
		}
	}
}
