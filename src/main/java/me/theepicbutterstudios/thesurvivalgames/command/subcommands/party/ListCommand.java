/**
 * Name: ListCommand.java
 * Created: 8 December 2013
 *
 * @version 1.0.0
 */
package me.theepicbutterstudios.thesurvivalgames.command.subcommands.party;

import java.util.UUID;

import me.theepicbutterstudios.thesurvivalgames.Party;
import me.theepicbutterstudios.thesurvivalgames.managers.PartyManager;

import org.bukkit.Bukkit;

public class ListCommand {
	
	/**
	 * Lists the current members of your party
	 * @param player The player executing the command
	 */
	
	public static void execute(org.bukkit.entity.Player sender) {
		UUID id = (UUID) PartyManager.getPartyManager().getPlayers().get(sender.getName());
		if (id != null) {
			Party party = (Party) PartyManager.getPartyManager().getParties().get(id);
			String list = org.bukkit.ChatColor.GOLD + party.getLeader() + " ";
			for (String member : party.getMembers()) {
				if (member != null) {
					org.bukkit.entity.Player player = Bukkit.getServer().getPlayer(member);
					if (player == null) {
						list = list + org.bukkit.ChatColor.DARK_GRAY + member + " ";
					} else {
						list = list + org.bukkit.ChatColor.WHITE + member + " ";
					}
				}
			}

			sender.sendMessage(list);
		} else {
			sender.sendMessage(org.bukkit.ChatColor.YELLOW + "You are not in a party");
		}
	}

	
	/**
	 * Lists the members of another player's party
	 * @param sender The player executing the command
	 * @param args The player's username of the party who you want to list the members of
	 */
	
	public static void execute(org.bukkit.entity.Player sender, String args) {
		org.bukkit.entity.Player p = Bukkit.getServer().getPlayer(args);
		if (p != null) {
			UUID id = (UUID) PartyManager.getPartyManager().getPlayers().get(p.getName());
			if (id != null) {
				Party party = (Party) PartyManager.getPartyManager().getParties().get(id);
				String list = org.bukkit.ChatColor.GOLD + party.getLeader() + " ";
				for (String member : party.getMembers()) {
					if (member != null) {
						org.bukkit.entity.Player player = Bukkit.getServer().getPlayer(member);
						if (player == null) {
							list = list + org.bukkit.ChatColor.DARK_GRAY + member + " ";
						} else {
							list = list + org.bukkit.ChatColor.WHITE + member + " ";
						}
					}
				}

				sender.sendMessage(list);
			} else {
				sender.sendMessage(org.bukkit.ChatColor.YELLOW + args + " is not in a party");
			}
		} else {
			sender.sendMessage(org.bukkit.ChatColor.YELLOW + args + " is not online");
		}
	}
}
