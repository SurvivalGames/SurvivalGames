package me.theepicbutterstudios.thesurvivalgames.command.subcommands.party;

import java.util.UUID;

import me.theepicbutterstudios.thesurvivalgames.managers.PartyManager;

public class ChatCommand {
	public static void execute(org.bukkit.entity.Player player) {
		UUID id = (UUID) PartyManager.getPartyManager().getPlayers().get(player.getName());
		if (id != null) {
			if (PartyManager.getPartyManager().getPartyChat().contains(player.getName())) {
				PartyManager.getPartyManager().getPartyChat().remove(player.getName());
				player.sendMessage(org.bukkit.ChatColor.YELLOW + "You are no longer party chatting");
			} else {
				PartyManager.getPartyManager().getPartyChat().add(player.getName());
				player.sendMessage(org.bukkit.ChatColor.YELLOW + "You are now party chatting");
			}
		} else {
			player.sendMessage(org.bukkit.ChatColor.YELLOW + "You must be in a party to use party chat");
		}
	}
}
