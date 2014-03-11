/**
 * Name: LeaveCommand.java Created: 8 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.command.subcommands.party;

import src.main.java.com.communitysurvivalgames.thesurvivalgames.command.subcommands.SubCommand;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.managers.PartyManager;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.objects.Party;

public class LeaveCommand implements SubCommand {

    /**
     * Leaves the current party
     *
     * @param sender The player executing the command
     */
    public void execute(String cmd, Player sender, String[] args) {
        if (cmd.equalsIgnoreCase("leave")) {
            UUID id = SGApi.getPartyManager().getPlayers().get(sender.getName());
           if (id != null) {
                Party party = SGApi.getPartyManager().getParties().get(id);
              if (party.getLeader().equalsIgnoreCase(sender.getName())) {
                    PartyManager.endParty(party.getLeader(), id);
                } else {
                    party.removeMember(sender.getName());
                    SGApi.getPartyManager().getPlayers().remove(sender.getName());
                if (party.hasNoMembers()) {
                        PartyManager.endParty(party.getLeader(), id);
                    }
                    for (String member : party.getMembers()) {
                        if (member != null) {
                            Player p = Bukkit.getServer().getPlayer(member);
                            if (p != null) {
                                p.sendMessage(org.bukkit.ChatColor.YELLOW + sender.getName() + I18N.getLocaleString("LEFT_PARTY"));
                            }
                        }
                    }
                    sender.sendMessage(org.bukkit.ChatColor.YELLOW + I18N.getLocaleString("YOU_LEFT_PARTY"));
                }
            } else {
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + I18N.getLocaleString("NO_PARTY_2"));
            }
        }
    }
}
