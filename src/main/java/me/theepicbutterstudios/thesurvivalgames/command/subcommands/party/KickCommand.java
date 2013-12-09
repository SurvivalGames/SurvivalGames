package me.theepicbutterstudios.thesurvivalgames.command.subcommands.party;

import java.util.UUID;

import me.theepicbutterstudios.thesurvivalgames.Party;
import me.theepicbutterstudios.thesurvivalgames.managers.PartyManager;

import org.bukkit.Bukkit;

public class KickCommand {
	public static void execute(org.bukkit.entity.Player sender, String player) {
		UUID id = (UUID) PartyManager.getPartyManager().getPlayers().get(sender.getName());
		if (id != null) {
			Party party = (Party) PartyManager.getPartyManager().getParties().get(id);
			if (party.getLeader().equalsIgnoreCase(sender.getName())) {
				for (String members : party.getMembers()) {
					if ((members != null) && (members.equalsIgnoreCase(player))) {
						party.removeMember(player);
						PartyManager.getPartyManager().getPlayers().remove(player);
						sender.sendMessage(org.bukkit.ChatColor.YELLOW + player + " was kicked from the party");
						org.bukkit.entity.Player p = Bukkit.getServer().getPlayer(player);
						if (p != null) {
							p.sendMessage(org.bukkit.ChatColor.YELLOW + "You were kicked from the party");
						}
						if (party.hasNoMembers()) {
							PartyManager.endParty(sender.getName(), id);
						}
						for (String member : party.getMembers()) {
							if (member != null) {
								org.bukkit.entity.Player play = Bukkit.getServer().getPlayer(member);
								if (play != null) {
									play.sendMessage(org.bukkit.ChatColor.YELLOW + player + " was kicked from the party");
								}
							}
						}
						return;
					}
				}

				sender.sendMessage(org.bukkit.ChatColor.YELLOW + "Player " + player + " is not in your party");
			} else {
				sender.sendMessage(org.bukkit.ChatColor.YELLOW + "You must be the party leader to kick another player");
			}
		} else {
			sender.sendMessage(org.bukkit.ChatColor.YELLOW + "You are not in a party");
		}
	}
}
