/**
 * Name: PlayerQuitListener.java
 * Created: 8 December 2013
 *
 * @version 1.0.0
 */
package me.theepicbutterstudios.thesurvivalgames.listeners;

import java.util.UUID;

import me.theepicbutterstudios.thesurvivalgames.Party;
import me.theepicbutterstudios.thesurvivalgames.managers.ArenaManager;
import me.theepicbutterstudios.thesurvivalgames.managers.PartyManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

	/**
	 * Detects when a player quits, and if that player is the party leader, the party will disband
	 * Also removes the player from the arena if they are in game
	 * @param event The event being called
	 */

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		org.bukkit.entity.Player p = event.getPlayer();
		if (p != null) {
			if (ArenaManager.getManager().isInGame(p)) {
				ArenaManager.getManager().removePlayer(p);
			}
			UUID id = (UUID) PartyManager.getPartyManager().getPlayers().get(p.getName());
			if (id != null) {
				Party party = (Party) PartyManager.getPartyManager().getParties().get(id);
				if ((party != null) && (p.getName().equalsIgnoreCase(party.getLeader()))) {
					PartyManager.endParty(party.getLeader(), id);
				}
			}
		}
	}
}
