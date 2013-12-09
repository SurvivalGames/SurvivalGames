/**
 * Name: JoinCommand.java
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

public class JoinCommand implements SubCommand{
	
	/**
	 * Joins a party if you have been invited to one
	 * @param player The player executing the command
	 */
	
	public void execute(String cmd, Player sender, String[] args) {
		UUID partyID = (UUID) PartyManager.getPartyManager().getInvites().get(sender.getName());
		if (partyID != null) {
			Party party = (Party) PartyManager.getPartyManager().getParties().get(partyID);
			if (party != null) {
				if (((Party) PartyManager.getPartyManager().getParties().get(partyID)).hasRoom()) {
					party.addMember(sender.getName());
					PartyManager.getPartyManager().getPlayers().put(sender.getName(), partyID);
					sender.sendMessage(org.bukkit.ChatColor.YELLOW + "You have joined " + party.getLeader() + "'s party");
					Player player = Bukkit.getServer().getPlayer(party.getLeader());
					player.sendMessage(org.bukkit.ChatColor.YELLOW + sender.getName() + " has joined your party");
					for (String member : party.getMembers()) {
						if (member != null) {
							Player p = Bukkit.getServer().getPlayer(member);
							if (p != null) {
								p.sendMessage(org.bukkit.ChatColor.YELLOW + sender.getName() + " has joined your party");
							}
						}
					}
				} else {
					sender.sendMessage(org.bukkit.ChatColor.YELLOW + "The party is full");
				}

			} else {
				sender.sendMessage(org.bukkit.ChatColor.YELLOW + "The party does not exist");
			}
			PartyManager.getPartyManager().getInvites().remove(sender.getName());
		} else {
			sender.sendMessage(org.bukkit.ChatColor.YELLOW + "You do not have a pending invite");
		}
	}
}
