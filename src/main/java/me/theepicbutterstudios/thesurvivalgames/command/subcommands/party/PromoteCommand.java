/**
 * Name: PromoteCommand.java
 * Created: 8 December 2013
 *
 * @version 1.0.0
 */
package me.theepicbutterstudios.thesurvivalgames.command.subcommands.party;

import java.util.UUID;

import me.theepicbutterstudios.thesurvivalgames.Party;
import me.theepicbutterstudios.thesurvivalgames.managers.PartyManager;

import org.bukkit.Bukkit;

public class PromoteCommand {
	
	/**
	 * Promotes another player to party leader if the executer is the current leader
	 * @param player The player executing the command
	 */
	
	public static void execute(org.bukkit.entity.Player sender, String player) {
		UUID id = (UUID) PartyManager.getPartyManager().getPlayers().get(sender.getName());
		if (id != null) {
			Party party = (Party) PartyManager.getPartyManager().getParties().get(id);
			if (party.getLeader().equalsIgnoreCase(sender.getName())) {
				for (String member : party.getMembers()) {
					if ((member != null) && (member.equalsIgnoreCase(player))) {
						org.bukkit.entity.Player p = Bukkit.getServer().getPlayer(player);
						if (p != null) {
							String oldLeader = party.getLeader();
							party.setLeader(p.getName());
							party.removeMember(p.getName());
							party.addMember(oldLeader);
							sender.sendMessage(org.bukkit.ChatColor.YELLOW + "You have promoted " + p.getName() + " to leader");
							p.sendMessage(org.bukkit.ChatColor.YELLOW + sender.getName() + " has promoted you to leader");
						} else {
							sender.sendMessage(org.bukkit.ChatColor.YELLOW + player + " is not online");
						}
						return;
					}
				}

				sender.sendMessage(org.bukkit.ChatColor.YELLOW + player + " is not in your party");
			} else {
				sender.sendMessage(org.bukkit.ChatColor.YELLOW + "You must be the party leader to promote another player");
			}
		} else {
			sender.sendMessage(org.bukkit.ChatColor.YELLOW + "You are not in a party");
		}
	}
}
