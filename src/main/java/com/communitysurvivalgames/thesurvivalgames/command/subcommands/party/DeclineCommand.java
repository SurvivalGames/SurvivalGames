/**
 * Name: DeclineCommand.java Created: 8 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.command.subcommands.party;

import com.communitysurvivalgames.thesurvivalgames.command.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.PartyManager;
import com.communitysurvivalgames.thesurvivalgames.objects.Party;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Declines an invitation to join a party
 *
 * @param player The player executing the command
 */
public class DeclineCommand implements SubCommand {

    public void execute(String cmd, Player sender, String[] args) {
        if (cmd.equalsIgnoreCase("decline")) {
            UUID id = (UUID) PartyManager.getPartyManager().getInvites().get(sender.getName());
            if (id != null) {
                Party party = (Party) PartyManager.getPartyManager().getParties().get(id);
                if (party != null) {
                    Player player = Bukkit.getServer().getPlayer(party.getLeader());
                    if (player != null) {
                        player.sendMessage(org.bukkit.ChatColor.YELLOW + sender.getName() + I18N.getLocaleString("HAS_DECLINED"));
                    }
                    if (party.hasNoMembers()) {
                        PartyManager.endParty(party.getLeader(), id);
                        sender.sendMessage(org.bukkit.ChatColor.YELLOW + I18N.getLocaleString("YOU_DECLINED") + party.getLeader() + I18N.getLocaleString("INVITATION"));
                    }
                }
                PartyManager.getPartyManager().getInvites().remove(sender.getName());
            } else {
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + I18N.getLocaleString("NO_INVITE"));
            }

        }
    }
}
