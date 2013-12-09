/**
 * Name: ChatCommand.java
 * Created: 8 December 2013
 *
 * @version 1.0.0
 */
package me.theepicbutterstudios.thesurvivalgames.command.subcommands.party;

import java.util.UUID;

import org.bukkit.entity.Player;

import me.theepicbutterstudios.thesurvivalgames.command.SubCommand;
import me.theepicbutterstudios.thesurvivalgames.managers.PartyManager;

public class ChatCommand implements SubCommand {

	/**
	 * Switches chat mode from party chat to global chat and vice versa for player
	 * @param player The player executing the command
	 */

	public void execute(String cmd, Player player, String args[]) {
		if (cmd.equalsIgnoreCase("chat")) {
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
}
