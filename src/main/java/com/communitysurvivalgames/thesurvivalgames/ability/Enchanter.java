package com.communitysurvivalgames.thesurvivalgames.ability;

import src.main.java.com.communitysurvivalgames.thesurvivalgames.util.FireworkEffectPlayer;

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
				FireworkEffect fEffect = FireworkEffect.builder().flicker(false).withColor(Color.PURPLE).withFade(Color.NAVY).with(Type.BURST).trail(false).build();
				try {
					FireworkEffectPlayer.getFireworkEffectPlayer().playFirework(event.getPlayer().getWorld(), event.getPlayer().getLocation(), fEffect);
				} catch (Exception e) {
					//If the firework dosen't work... to bad 
				}
			}
		}
	}
}
