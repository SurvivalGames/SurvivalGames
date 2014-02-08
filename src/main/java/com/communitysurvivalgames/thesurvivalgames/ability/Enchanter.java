package com.communitysurvivalgames.thesurvivalgames.ability;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class Enchanter extends SGAbility implements Listener {

	public Enchanter() {
		super(6);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (this.hasAbility(player)) {
			if (event.getPlayer().getItemInHand().getType() == Material.ENCHANTMENT_TABLE) {
                Inventory inv = Bukkit.getServer().createInventory(null, InventoryType.ENCHANTING);
                event.getPlayer().openInventory(inv);
                event.getPlayer().getInventory().remove(Material.ENCHANTMENT_TABLE);
			}
		}
	}
}
