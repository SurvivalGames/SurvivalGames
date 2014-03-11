package com.communitysurvivalgames.thesurvivalgames.listeners;

import src.main.java.com.communitysurvivalgames.thesurvivalgames.managers.SGApi;

public class EntityInteractListener implements Listener {
	@EventHandler(priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEntityEvent event) {
		if (event.getRightClicked() instanceof EnderCrystal) {
			SGApi.getKitManager().displayDefaultKitSelectionMenu(event.getPlayer());
		}
	}
}
