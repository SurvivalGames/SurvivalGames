/**
 * Name: HelpCommand.java
 * Created: 8 December 2013
 *
 * @version 1.0.0
 */
package me.theepicbutterstudios.thesurvivalgames.command.subcommands.party;

import me.theepicbutterstudios.thesurvivalgames.command.SubCommand;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpCommand implements SubCommand {

	/**
	 * Displays the help for the party commands
	 * @param player The player executing the command
	 * @param args The page of help to be shown
	 */

	public void execute(String cmd, Player sender, String[] args) {
		if ((args.length == 0) || (cmd.equalsIgnoreCase("help")) || (cmd.equalsIgnoreCase("?"))) {
			staticExecute(sender, args);
		}
	}

	public static void staticExecute(Player sender, String[] args) {

		String help = ChatColor.YELLOW + "";
		if ((args.length == 0) || ((args.length == 1) && (args[0].equalsIgnoreCase("help")))) {
			help = "Party Manager Help\n";
			help = help + "------------------\n";
			help = help + "- /sg party invite : Invites a player to your party\n";
			help = help + "- /sg party join : Joins the party you were invited to\n";
			help = help + "- /sg party leave : Leaves your current party\n";
			help = help + "- /sg party decline: Declines your current party invite\n";
			help = help + "- /sg party list: Displays your current party members\n";
			help = help + "- /sg party kick: Removes a player from your party\n";
			help = help + "- /sg party promote: Promotes another player to party leader\n";
			help = help + "- /sg party chat: Toggles whether or not to speak in party chat only\n";
			help = help + "For more information on a commant type /sg party help <command>";
		} else if ((args[0].equalsIgnoreCase("help")) || (args[0].equalsIgnoreCase("?"))) {
			if (args[1].equalsIgnoreCase("invite")) {
				help = "Party Invite Help\n";
				help = help + "-----------------\n";
				help = help + "Usage: /party invite <player>\n";
				help = help + "Invites the specified player to join your party.\n";
				help = help + "You must be the party leader, or not part of a party to use this command. This will start a new party if you are not in one.\n";
			}
			if (args[1].equalsIgnoreCase("join")) {
				help = "Party Join Help\n";
				help = help + "---------------\n";
				help = help + "Usage: /party join\n";
				help = help + "Accepts your most recently received party invite.\n";
				help = help + "You may not use this command if you are in a party.\n";
			}
			if (args[1].equalsIgnoreCase("leave")) {
				help = "Party Leave Help\n";
				help = help + "----------------\n";
				help = help + "Usage: /party leave\n";
				help = help + "Leaves your current party.\n";
				help = help + "You must be in a party to use this command. Your current party will be disbanded if you are the leader, or the last remaining non-leader.\n";
			}
			if (args[1].equalsIgnoreCase("decline")) {
				help = "Party Decline Help\n";
				help = help + "-----------------\n";
				help = help + "Usage: /party decline\n";
				help = help + "Declines your pending party invite.\n";
				help = help + "You must have a pending invite to use this command.\n";
			}
			if (args[1].equalsIgnoreCase("list")) {
				help = "Party List Help\n";
				help = help + "---------------\n";
				help = help + "Usage: /party list\n";
				if (sender.hasPermission("partymanager.admin.list")) {
					help = help + "OR\n";
					help = help + "Usage: /party list <playername>\n";
				}

				help = help + "Lists your current party members and their status.\n";
				help = help + "You must be in a party to use this command. The party leader's name is gold, online members are white, and offline members are gray.\n";
			}
			if (args[1].equalsIgnoreCase("kick")) {
				help = "Party Kick Help\n";
				help = help + "---------------\n";
				help = help + "Usage: /party kick <player>\n";
				help = help + "Removes the specified player from the party\n";
				help = help + "You must be the party leader to use this command\n";
			}
			if (args[1].equalsIgnoreCase("promote")) {
				help = "Party Promote Help\n";
				help = help + "------------------\n";
				help = help + "Usage: /party promote <player>\n";
				help = help + "Promotes the specified player to party leader\n";
				help = help + "You must be the party leader to use this command\n";
			}
			if (args[1].equalsIgnoreCase("chat")) {
				help = "Party Promote Help\n";
				help = help + "------------------\n";
				help = help + "Usage: /party chat\n";
				help = help + "Toggles whether or not you are speaking in party chat\n";
				help = help + "You must be in a party to talk in part chat\n";
			}
		}
		sender.sendMessage(help);
	}
}
