/**
 * Name: InviteCommand.java Created: 8 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.command.subcommands.party;

import com.communitysurvivalgames.thesurvivalgames.command.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.managers.PartyManager;
import com.communitysurvivalgames.thesurvivalgames.objects.Party;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class InviteCommand implements SubCommand {

    /**
     * Invites a player to your party - creates a party if you don't have one
     *
     * @param sender The player executing the command
     * @param player The player to be invited to the party
     */
    public void execute(String cmd, Player sender, String[] args) {
        if ((args.length == 1) && (args[0].equalsIgnoreCase("invite"))) {
            UUID partyID = (UUID) PartyManager.getPartyManager().getPlayers().get(sender.getName());
            if (partyID == null) {
                partyID = PartyManager.startParty(sender);

                sendInvite(sender, args[0], partyID);
            } else if (((Party) PartyManager.getPartyManager().getParties().get(partyID)).hasRoom()) {
                if (((Party) PartyManager.getPartyManager().getParties().get(partyID)).getLeader().equalsIgnoreCase(sender.getName())) {
                    sendInvite(sender, args[0], partyID);
                } else {
                    sender.sendMessage(org.bukkit.ChatColor.YELLOW + "You must be the party leader to send invites");
                }
            } else {
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + "Your party is full");
            }
        }
    }

    /**
     * NOTE: This is a backend function and is not to be used outside of this
     * class
     * <p/>
     * Sends the invite to the player
     *
     * @param sender The player sending the invite
     * @param player The player to receive the invite
     * @param id     The UUID of the player to be invited
     */
    public static void sendInvite(Player sender, String player, UUID id) {
        Player p = Bukkit.getServer().getPlayer(player);
        if (p != null) {
            if (PartyManager.getPartyManager().getPlayers().get(p.getName()) != null) {
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + player + " is already in a party");
                if (((Party) PartyManager.getPartyManager().getParties().get(id)).hasNoMembers()) {
                    PartyManager.endParty(sender.getName(), id);
                }
                return;
            }
            if ((PartyManager.getPartyManager().getInvites().containsKey(p.getName())) && (PartyManager.getPartyManager().getInvites().containsValue(id))) {
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + player + " already has a pending invite from you");
                return;
            }

            PartyManager.getPartyManager().getInvites().put(p.getName(), id);
            p.sendMessage(org.bukkit.ChatColor.YELLOW + "You have been invited to a party by " + sender.getName());
            sender.sendMessage(org.bukkit.ChatColor.YELLOW + "You have invited " + player + " to join your party");
        } else {
            sender.sendMessage(org.bukkit.ChatColor.YELLOW + "Could not find player " + player);
            if (((Party) PartyManager.getPartyManager().getParties().get(id)).hasNoMembers()) {
                PartyManager.endParty(sender.getName(), id);
            }
        }
    }
}
