/**
 * Name: LeaveCommand.java Created: 8 December 2013
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

public class LeaveCommand implements SubCommand {

    /**
     * Leaves the current party
     *
     * @param player The player executing the command
     */
    public void execute(String cmd, Player sender, String[] args) {
        if (cmd.equalsIgnoreCase("leave")) {
            UUID id = (UUID) PartyManager.getPartyManager().getPlayers().get(sender.getName());
            if (id != null) {
                Party party = (Party) PartyManager.getPartyManager().getParties().get(id);
                if (party.getLeader().equalsIgnoreCase(sender.getName())) {
                    PartyManager.endParty(party.getLeader(), id);
                } else {
                    party.removeMember(sender.getName());
                    PartyManager.getPartyManager().getPlayers().remove(sender.getName());
                    if (party.hasNoMembers()) {
                        PartyManager.endParty(party.getLeader(), id);
                    }
                    for (String member : party.getMembers()) {
                        if (member != null) {
                            Player p = Bukkit.getServer().getPlayer(member);
                            if (p != null) {
                                p.sendMessage(org.bukkit.ChatColor.YELLOW + sender.getName() + " has left the party");
                            }
                        }
                    }
                    sender.sendMessage(org.bukkit.ChatColor.YELLOW + "You have left the party");
                }
            } else {
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + "You are not in a party");
            }
        }
    }
}
