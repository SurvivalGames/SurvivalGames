/**
 * Name: DeclineCommand.java
 * Created: 8 December 2013
 *
 * @version 1.0.0
 */
package me.theepicbutterstudios.thesurvivalgames.command.subcommands.party;

import java.util.UUID;

import me.theepicbutterstudios.thesurvivalgames.Party;
import me.theepicbutterstudios.thesurvivalgames.command.SubCommand;
import me.theepicbutterstudios.thesurvivalgames.managers.PartyManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Declines an invitation to join a party
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
						player.sendMessage(org.bukkit.ChatColor.YELLOW + sender.getName() + " has declined your invitation");
					}
					if (party.hasNoMembers()) {
						PartyManager.endParty(party.getLeader(), id);
						sender.sendMessage(org.bukkit.ChatColor.YELLOW + "You have declined " + party.getLeader() + "'s invitation");
					}
				}
				PartyManager.getPartyManager().getInvites().remove(sender.getName());
			} else {
				sender.sendMessage(org.bukkit.ChatColor.YELLOW + "You do not have a pending invite");
			}

		}
	}
}