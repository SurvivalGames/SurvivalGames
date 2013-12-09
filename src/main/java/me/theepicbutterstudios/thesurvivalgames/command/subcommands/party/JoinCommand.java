package me.theepicbutterstudios.thesurvivalgames.command.subcommands.party;

import java.util.UUID;

import me.theepicbutterstudios.thesurvivalgames.Party;
import me.theepicbutterstudios.thesurvivalgames.managers.PartyManager;

import org.bukkit.Bukkit;

public class JoinCommand {
	public static void execute(org.bukkit.entity.Player sender) {
		UUID partyID = (UUID) PartyManager.getPartyManager().getInvites().get(sender.getName());
		if (partyID != null) {
			Party party = (Party) PartyManager.getPartyManager().getParties().get(partyID);
			if (party != null) {
				if (((Party) PartyManager.getPartyManager().getParties().get(partyID)).hasRoom()) {
					party.addMember(sender.getName());
					PartyManager.getPartyManager().getPlayers().put(sender.getName(), partyID);
					sender.sendMessage(org.bukkit.ChatColor.YELLOW + "You have joined " + party.getLeader() + "'s party");
					org.bukkit.entity.Player player = Bukkit.getServer().getPlayer(party.getLeader());
					player.sendMessage(org.bukkit.ChatColor.YELLOW + sender.getName() + " has joined your party");
					for (String member : party.getMembers()) {
						if (member != null) {
							org.bukkit.entity.Player p = Bukkit.getServer().getPlayer(member);
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
