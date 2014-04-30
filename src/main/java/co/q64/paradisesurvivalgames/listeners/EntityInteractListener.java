package co.q64.paradisesurvivalgames.listeners;

import org.bukkit.entity.EnderCrystal;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import co.q64.paradisesurvivalgames.exception.ArenaNotFoundException;
import co.q64.paradisesurvivalgames.managers.SGApi;

public class EntityInteractListener implements Listener {
	@EventHandler(priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEntityEvent event) {
		if (event.getRightClicked() instanceof EnderCrystal) {
			SGApi.getKitManager().displayDefaultKitSelectionMenu(event.getPlayer());
		}
		try {
			if (SGApi.getArenaManager().getArena(event.getPlayer()).getSpectators().contains(event.getPlayer().getUniqueId())){
				event.setCancelled(true);
			}
		} catch (ArenaNotFoundException e){}
	}
}
