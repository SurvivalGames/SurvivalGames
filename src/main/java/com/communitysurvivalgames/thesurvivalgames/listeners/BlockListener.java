/**
 * Name: BlockListener.java 
 * Created: 9 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.communitysurvivalgames.thesurvivalgames.exception.ArenaNotFoundException;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;
import com.communitysurvivalgames.thesurvivalgames.rollback.ChangedBlock;

public class BlockListener implements Listener {

	final List<Material> allowed;
	static List<Block> breakable;

	public BlockListener() {

		allowed = new ArrayList<Material>();
		breakable = new ArrayList<Block>();
		for (String s : SGApi.getPlugin().getPluginConfig().getAllowedBlockBreaks()) {
			allowed.add(Material.valueOf(s));
		}
	}

	public static void addBreakable(Block block) {
		breakable.add(block);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockPlace(BlockPlaceEvent event) {
		if (SGApi.getArenaManager().isInGame(event.getPlayer())) {
			if (event.getBlock().getType().equals(Material.TNT)) {
				event.getPlayer().getWorld().spawnEntity(event.getBlock().getLocation(), EntityType.PRIMED_TNT);
			}
			event.setCancelled(true);
		}

		if (!event.getPlayer().hasPermission("sg.build") || !(breakable.contains(event.getBlock()))) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event) {
		if (allowed.contains(event.getBlock().getType())) {
			try {
				SGArena a = SGApi.getArenaManager().getArena(event.getPlayer());
				a.changedBlocks.add(new ChangedBlock(event.getBlock().getWorld().getName(), event.getBlock().getType(), event.getBlock().getData(), Material.AIR, Byte.parseByte(0 + ""), event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ()));
				return;
			} catch (ArenaNotFoundException ignored) {
			}
		}

		if (!event.getPlayer().hasPermission("sg.build") || !(event.getBlock().getType() == Material.DIAMOND_ORE || !event.getPlayer().isOp())) {
			event.setCancelled(true);
		}
	}
}
