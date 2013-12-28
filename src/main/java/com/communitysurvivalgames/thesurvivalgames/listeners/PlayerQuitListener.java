/**
 * Name: PlayerQuitListener.java Created: 8 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.listeners;

import com.communitysurvivalgames.thesurvivalgames.managers.ArenaManager;
import com.communitysurvivalgames.thesurvivalgames.managers.PartyManager;
import com.communitysurvivalgames.thesurvivalgames.objects.Party;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

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
            if (ArenaManager.getManager().isInGame(p)) {
                ArenaManager.getManager().removePlayer(p);
            }
            UUID id = PartyManager.getPartyManager().getPlayers().get(p.getName());
            if (id != null) {
                Party party = PartyManager.getPartyManager().getParties().get(id);
                if ((party != null) && (p.getName().equalsIgnoreCase(party.getLeader()))) {
                    PartyManager.endParty(party.getLeader(), id);
                }
            }
        }
    }
}
