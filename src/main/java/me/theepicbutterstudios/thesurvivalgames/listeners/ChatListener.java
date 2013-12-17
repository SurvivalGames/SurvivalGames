/**
 * Name: ChatListener.java Created: 8 December 2013
 *
 * @version 1.0.0
 */
package me.theepicbutterstudios.thesurvivalgames.listeners;

import me.theepicbutterstudios.thesurvivalgames.managers.PartyManager;
import me.theepicbutterstudios.thesurvivalgames.objects.Party;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;
import java.util.logging.Level;

public class ChatListener implements Listener {

    /**
     * Formats chat and detects if the player is using party chat, if so, it
     * will only send messages to the people in that player's party
     *
     * @param event The event being called
     */
    @EventHandler
    public void onPlayerChat(org.bukkit.event.player.AsyncPlayerChatEvent event) {
        if (PartyManager.getPartyManager().getPartyChat().contains(event.getPlayer().getName())) {
            UUID id = (UUID) PartyManager.getPartyManager().getPlayers().get(event.getPlayer().getName());
            if (id != null) {
                Party party = (Party) PartyManager.getPartyManager().getParties().get(id);
                if (party != null) {
                    String[] members = party.getMembers();
                    int membersNum = members.length;
                    for (int i = 0; i < membersNum; i++) {
                        String member = members[i];
                        if (member != null) {
                            org.bukkit.entity.Player p = org.bukkit.Bukkit.getServer().getPlayer(member);
                            if (p != null) {
                                p.sendMessage(org.bukkit.ChatColor.DARK_AQUA + "[P] " + event.getPlayer().getDisplayName() + org.bukkit.ChatColor.DARK_AQUA + ": " + event.getMessage());
                            }
                        }
                    }
                    org.bukkit.entity.Player p = org.bukkit.Bukkit.getServer().getPlayer(party.getLeader());
                    if (p != null) {
                        p.sendMessage(org.bukkit.ChatColor.DARK_AQUA + "[P] " + event.getPlayer().getDisplayName() + org.bukkit.ChatColor.DARK_AQUA + ": " + event.getMessage());
                    }
                }
                event.setCancelled(true);
                Bukkit.getLogger().log(Level.INFO, "[P] {0}: {1}", new Object[]{event.getPlayer().getDisplayName(), event.getMessage()});

                org.bukkit.entity.Player[] playerList = org.bukkit.Bukkit.getServer().getOnlinePlayers();
                int playersNum = org.bukkit.Bukkit.getServer().getOnlinePlayers().length;
                for (int i = 0; i < playersNum; i++) {
                    org.bukkit.entity.Player p = playerList[i];
                    if ((p.hasPermission("partymanager.admin.spy")) && (!party.hasMember(p.getName()))) {
                        p.sendMessage(org.bukkit.ChatColor.GRAY + "[P] " + event.getPlayer().getName() + ": " + event.getMessage());
                    }
                }
            } else {
                PartyManager.getPartyManager().getPartyChat().remove(event.getPlayer().getName());
            }
        } else {
            // We'll add chat formatting here later, it will include the player's "Rank" weather that be admin or a youtuber, as wall as the player's "Points"
        }
    }
}
