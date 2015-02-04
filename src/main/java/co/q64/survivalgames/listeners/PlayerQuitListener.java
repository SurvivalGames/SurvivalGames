/**
 * Name: PlayerQuitListener.java Created: 8 December 2013
 *
 * @version 1.0.0
 */

package co.q64.survivalgames.listeners;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import co.q64.survivalgames.managers.PartyManager;
import co.q64.survivalgames.managers.SGApi;
import co.q64.survivalgames.objects.Party;

public class PlayerQuitListener implements Listener {

	/**
	 * Detects when a player quits, and if that player is the party leader, the
	 * party will disband Also removes the player from the arena if they are in
	 * game
	 *
	 * @param event The event being called
	 */
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		org.bukkit.entity.Player p = event.getPlayer();
		if (p != null) {
			if (SGApi.getArenaManager().isInGame(p)) {
				SGApi.getArenaManager().playerDisconnect(p);
			}
			UUID id = SGApi.getPartyManager().getPlayers().get(p.getName());
			if (id != null) {
				Party party = SGApi.getPartyManager().getParties().get(id);
				if ((party != null) && (p.getName().equalsIgnoreCase(party.getLeader()))) {
					PartyManager.endParty(party.getLeader(), id);
				}
			}
		}
		MoveListener.getPlayers().remove(event.getPlayer().getName());
		if (SGApi.getArenaManager().isInGame(event.getPlayer()))
			event.getPlayer().damage(1000L);
		SGApi.getPlugin().getTracker().trackEvent("Player Disconnect", event.getPlayer().getName());
	}
}
