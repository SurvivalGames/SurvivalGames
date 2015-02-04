/**
 * Name: SafeEntityListener.java 
 * Created: 29 December 2013
 *
 * @version 1.0.0
 */

package co.q64.survivalgames.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class SafeEntityListener implements Listener {

	private static final List<UUID> safe = new ArrayList<>();

	public static List<UUID> getPlayers() {
		return safe;
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			if (safe.contains(((Player) e.getEntity()).getName())) {
				e.setCancelled(true);
			}
		}
	}

}
