/**
 * Name: PartyManager.java Edited: 8 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.managers;

import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.objects.Party;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PartyManager {

    private static final PartyManager pm = new PartyManager();
    private static final java.util.Map<String, java.util.UUID> players = new HashMap<>();
    private static final java.util.Map<java.util.UUID, Party> parties = new HashMap<>();
    private static final java.util.Map<String, java.util.UUID> invites = new HashMap<>();
    private static final Set<String> partyChat = new HashSet<>();
    private static int partySize;

    /**
     * Gets the list of players in parties
     *
     * @return THe list of players
     */
    public java.util.Map<String, java.util.UUID> getPlayers() {
        return players;
    }

    /**
     * Gets the list of parties
     *
     * @return List of parties
     */
    public java.util.Map<java.util.UUID, Party> getParties() {
        return parties;
    }

    /**
     * Gets the list of current party invites
     *
     * @return The list of invites
     */
    public java.util.Map<String, java.util.UUID> getInvites() {
        return invites;
    }

    /**
     * Gets the list of usernames of players currently in party chat
     *
     * @return List of usernames as a Set
     */
    public Set<String> getPartyChat() {
        return partyChat;
    }

    /**
     * @param player The player starting the party
     * @return UUID The UUID (Unique ID) of the new party
     */
    public static java.util.UUID startParty(org.bukkit.entity.Player player) {
        Party party = new Party(player.getName());
        players.put(player.getName(), party.getID());
        parties.put(party.getID(), party);
        if (player != null) { //TODO problem-an NPE would be thrown before the statement is reached
            player.sendMessage(ChatColor.YELLOW + I18N.getLocaleString("PARTY_CREATED"));
        }
        return party.getID();
    }

    /**
     * Ends a party
     *
     * @param name Name of the player ending the party
     * @param id   The UUID of the party to end
     */
    public static void endParty(String name, java.util.UUID id) {
        Party party = parties.get(id);

        for (String members : party.getMembers()) {
            if (members != null) {
                org.bukkit.entity.Player player = Bukkit.getServer().getPlayer(members);
                if (player != null) {
                    player.sendMessage(ChatColor.YELLOW + I18N.getLocaleString("PARTY_CREATED"));
                }
                players.remove(members);
            }
        }
        party.removeAll();
        org.bukkit.entity.Player player = Bukkit.getServer().getPlayer(name);
        if (player != null) {
            player.sendMessage(ChatColor.YELLOW + I18N.getLocaleString("PARTY_CREATED"));
        }
        players.remove(name);
        parties.remove(id);
    }

    /**
     * Gets the max size of a party
     *
     * @return The max size
     */
    public static int getMaxPartySize() {
        return partySize; //TODO Right now this will null pointer, this will be a config value when we get around to a config loader
    }

    /**
     * Gets the party members of the party the player is in
     *
     * @param p The player to get the members of
     * @return null if the player is not in a party, a String separated by
     *         commas of usernames
     */
    public String getParty(org.bukkit.entity.Player p) {
        java.util.UUID id = players.get(p.getName());
        if (id != null) {
            Party party = parties.get(id);
            if (party != null) {
                String partyMembers = party.getLeader();
                for (String member : party.getMembers()) {
                    if (member != null) {
                        partyMembers = partyMembers + "," + member;
                    }
                }
                return partyMembers;
            }

            return null;
        }

        return null;
    }

    /**
     * Gets if the player is the party leader
     *
     * @param p The player to be checked
     * @return If the player is the leader
     */
    public boolean isPartyLeader(org.bukkit.entity.Player p) {
        java.util.UUID id = players.get(p.getName());
        if (id != null) {
            Party party = parties.get(id);
            if (party != null) {
                if (party.getLeader().equalsIgnoreCase(p.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the Party Manager
     *
     * @return The singleton of the party manager
     */
    public static PartyManager getPartyManager() {
        return pm;
    }
}
