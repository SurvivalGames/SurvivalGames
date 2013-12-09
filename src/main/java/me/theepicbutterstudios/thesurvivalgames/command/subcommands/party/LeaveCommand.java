/**
 * Name: LeaveCommand.java
 * Created: 8 December 2013
 *
 * @version 1.0.0
 */
package me.theepicbutterstudios.thesurvivalgames.command.subcommands.party;

import java.util.UUID;

import me.theepicbutterstudios.thesurvivalgames.Party;
import me.theepicbutterstudios.thesurvivalgames.managers.PartyManager;

import org.bukkit.Bukkit;

public class LeaveCommand {
	
	/**
	 * Leaves the current party
	 * @param player The player executing the command
	 */
	
	public static void execute(org.bukkit.entity.Player sender) {
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
						org.bukkit.entity.Player p = Bukkit.getServer().getPlayer(member);
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
