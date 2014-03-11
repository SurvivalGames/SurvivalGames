/**
 * Name: PlayerQuitListener.java Created: 8 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.listeners;

import src.main.java.com.communitysurvivalgames.thesurvivalgames.managers.PartyManager;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.objects.Party;

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
                SGApi.getArenaManager().removePlayer(p);
            }
            UUID id = SGApi.getPartyManager().getPlayers().get(p.getName());
            if (id != null) {
                Party party = SGApi.getPartyManager().getParties().get(id);
                if ((party != null) && (p.getName().equalsIgnoreCase(party.getLeader()))) {
                    PartyManager.endParty(party.getLeader(), id);
                }
            }
        }
    }
}
