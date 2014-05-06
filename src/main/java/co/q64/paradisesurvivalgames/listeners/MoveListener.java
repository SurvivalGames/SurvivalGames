/**
 * Name: MoveListener.java
 * Created: 29 December 2013
 *
 * @version 1.0.0
 */

package co.q64.paradisesurvivalgames.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import co.q64.paradisesurvivalgames.exception.ArenaNotFoundException;
import co.q64.paradisesurvivalgames.locale.I18N;
import co.q64.paradisesurvivalgames.managers.SGApi;
import co.q64.paradisesurvivalgames.objects.SGArena;

public class MoveListener implements Listener {

	private static final List<UUID> list = new ArrayList<>();

	public static List<UUID> getPlayers() {
		return list;
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Location q = e.getFrom();
		Location w = e.getTo();

		if (list.contains(e.getPlayer().getUniqueId()) && (q.getBlockX() != w.getBlockX() || q.getBlockZ() != w.getBlockZ())) {
			e.setTo(e.getFrom());
		}

		//try {
		//	if (SGApi.getArenaManager().isInGame(e.getPlayer()) && SGApi.getArenaManager().getArena(e.getPlayer()).getState() == SGArena.ArenaState.DEATHMATCH && (Math.abs(e.getPlayer().getLocation().distanceSquared(SGApi.getMultiWorldManager().worldForName(SGApi.getArenaManager().getArena(e.getPlayer()).getArenaWorld().getName()).getCenter())) >= 0.5)) {
		//		e.setTo(e.getFrom());
		//		e.getPlayer().sendMessage(SGApi.getArenaManager().getPrefix() + I18N.getLocaleString("NOT_HAPPY"));
		//	}
		//} catch (ArenaNotFoundException ignored) {}
	}

}
