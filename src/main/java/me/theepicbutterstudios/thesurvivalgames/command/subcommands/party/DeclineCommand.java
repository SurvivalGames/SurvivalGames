package me.theepicbutterstudios.thesurvivalgames.command.subcommands.party;

import java.util.UUID;

import me.theepicbutterstudios.thesurvivalgames.Party;
import me.theepicbutterstudios.thesurvivalgames.managers.PartyManager;

import org.bukkit.Bukkit;

public class DeclineCommand {
	public static void execute(org.bukkit.entity.Player sender) {
		UUID id = (UUID) PartyManager.getPartyManager().getInvites().get(sender.getName());
		if (id != null) {
			Party party = (Party) PartyManager.getPartyManager().getParties().get(id);
			if (party != null) {
				org.bukkit.entity.Player player = Bukkit.getServer().getPlayer(party.getLeader());
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
