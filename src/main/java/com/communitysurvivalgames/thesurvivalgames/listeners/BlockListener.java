/**
 * Name: BlockListener.java 
 * Created: 9 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.listeners;

import src.main.java.com.communitysurvivalgames.thesurvivalgames.managers.SGApi;

public class BlockListener implements Listener {

	final List<Material> allowed;
	static List<Block> breakable;

	public BlockListener() {

		allowed = new ArrayList<>();
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
		if (!event.getPlayer().hasPermission("sg.build") || !(event.getBlock().getType() == Material.DIAMOND_ORE)) {
			event.setCancelled(true);
		}
	}
}
