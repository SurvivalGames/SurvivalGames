package com.communitysurvivalgames.thesurvivalgames.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;

public class ItemDropListener implements Listener {
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onItemDrop(PlayerDropItemEvent event) {
		if(event.getItemDrop().getItemStack().containsEnchantment(SGApi.getEnchantmentManager().undroppable));
	}
}
