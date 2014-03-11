package com.communitysurvivalgames.thesurvivalgames.ability;


public class Crafter extends SGAbility implements Listener {

	public Crafter() {
		super(3);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (this.hasAbility(player)) {
			if (event.getPlayer().getItemInHand().getType() == Material.WORKBENCH) {
                Inventory inv = Bukkit.getServer().createInventory(null, InventoryType.WORKBENCH);
                event.getPlayer().openInventory(inv);
			}
		}
	}
}
