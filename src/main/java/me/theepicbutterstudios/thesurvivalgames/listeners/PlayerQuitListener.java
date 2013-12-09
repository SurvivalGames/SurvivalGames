package me.theepicbutterstudios.thesurvivalgames.listeners;

import java.util.UUID;

import me.theepicbutterstudios.thesurvivalgames.Party;
import me.theepicbutterstudios.thesurvivalgames.managers.PartyManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;


public class PlayerQuitListener implements Listener {
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		org.bukkit.entity.Player p = event.getPlayer();
		if (p != null) {
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
