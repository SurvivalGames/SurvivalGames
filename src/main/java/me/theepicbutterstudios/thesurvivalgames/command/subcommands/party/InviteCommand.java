package me.theepicbutterstudios.thesurvivalgames.command.subcommands.party;

import java.util.UUID;

import me.theepicbutterstudios.thesurvivalgames.Party;
import me.theepicbutterstudios.thesurvivalgames.managers.PartyManager;

import org.bukkit.Bukkit;

public class InviteCommand {
	public static void execute(org.bukkit.entity.Player sender, String player) {
		UUID partyID = (UUID) PartyManager.getPartyManager().getPlayers().get(sender.getName());
		if (partyID == null) {
			partyID = PartyManager.startParty(sender);

			sendInvite(sender, player, partyID);
		} else if (((Party) PartyManager.getPartyManager().getParties().get(partyID)).hasRoom()) {
			if (((Party) PartyManager.getPartyManager().getParties().get(partyID)).getLeader().equalsIgnoreCase(sender.getName())) {
				sendInvite(sender, player, partyID);
			} else {
				sender.sendMessage(org.bukkit.ChatColor.YELLOW + "You must be the party leader to send invites");
			}
		} else {
			sender.sendMessage(org.bukkit.ChatColor.YELLOW + "Your party is full");
		}
	}

	public static void sendInvite(org.bukkit.entity.Player sender, String player, UUID id) {
		org.bukkit.entity.Player p = Bukkit.getServer().getPlayer(player);
		if (p != null) {
			if (PartyManager.getPartyManager().getPlayers().get(p.getName()) != null) {
				sender.sendMessage(org.bukkit.ChatColor.YELLOW + player + " is already in a party");
				if (((Party) PartyManager.getPartyManager().getParties().get(id)).hasNoMembers()) {
					PartyManager.endParty(sender.getName(), id);
				}
				return;
			}
			if ((PartyManager.getPartyManager().getInvites().containsKey(p.getName())) && (PartyManager.getPartyManager().getInvites().containsValue(id))) {
				sender.sendMessage(org.bukkit.ChatColor.YELLOW + player + " already has a pending invite from you");
				return;
			}

			PartyManager.getPartyManager().getInvites().put(p.getName(), id);
			p.sendMessage(org.bukkit.ChatColor.YELLOW + "You have been invited to a party by " + sender.getName());
			sender.sendMessage(org.bukkit.ChatColor.YELLOW + "You have invited " + player + " to join your party");
		} else {
			sender.sendMessage(org.bukkit.ChatColor.YELLOW + "Could not find player " + player);
			if (((Party) PartyManager.getPartyManager().getParties().get(id)).hasNoMembers()) {
				PartyManager.endParty(sender.getName(), id);
			}
		}
	}
}
