/**
 * Name: Help.java
 * Edited: 6 December 2013
 *
 * @version 1.0.0
 */

package me.theepicbutterstudios.thesurvivalgames.command.subcommands;

import me.theepicbutterstudios.thesurvivalgames.command.SubCommand;
import me.theepicbutterstudios.thesurvivalgames.managers.ArenaManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Help implements SubCommand {

	/**
	 * An example of implementing SubCommand. DO NOT CALL DIRECTLY. Only use in CommandHandler
	 *
	 * @param cmd The command that was executed
	 * @param p The player that executed the command
	 * @param args The arguments after the command
	 */

	@Override
	public void execute(String cmd, Player p, String[] args) {
		if (cmd.equalsIgnoreCase("help")) {
			if (args.length == 0) {
				// display page one of help
			} else if (args.length >= 1) {
				int page = 0;
				try {
					page = Integer.parseInt(args[0]);
				} catch (NumberFormatException e) {
					p.sendMessage(ArenaManager.getManager().error + "That help page doesn't exist");
				}

				switch (page) {
				case 0:
					p.sendMessage(ArenaManager.getManager().error + "That's not a real page");
					break;

				case 1:
					sendHelpMessages(p, 1, "/sg create: Creates an arena", "/sg setradius [Arena ID][Radius]: Sets the radius of the arena", "/sg setlobby [Arena ID]: Sets the lobby spawn", "/sg setgamespawn [Number]: Set the spawn ingame for the specific point", "/sg setnextspawn: Set the next spawn in line for the arena");
					break;

				case 2:
					sendHelpMessages(p, 2, "/sg setdeathmatch [Number]: Sets the deathmatch spawn at the point", "/sg setmaxplayers [Number]: Set the max arena capacity", "/sg setchest [T1/T2]: Set a specific chest tier", "/sg join [Arena ID]: Joins an arena", "/sg vote [Number]: Makes you vote for a map");
					break;

				case 3:
					sendHelpMessages(p, 3, "/sg bounty [Player][Amount]: Set a bounty for this player", "/sg sponsor [Player][Amount]: Sponsor a player", "/sg leave: Leaves an arena", "/sg start [Arena ID][State ID]: Puts the game into a defined state", "/sg stop [Arena ID]: Stops the arena and rollsback");
					p.sendMessage(ChatColor.GOLD + "--------------" + ChatColor.DARK_AQUA + "End of help" + ChatColor.DARK_AQUA + "--------------");
					break;

				default:
					p.sendMessage(ArenaManager.getManager().error + "That's not a real page");
				}

			}

			return; // no need to have booleans, this is a method returns void.
		}
	}

	/**
	 * Send specific messages to the player
	 * 
	 * @param p The player to send the help message to
	 * @param page The page number
	 * @param args The help messages separated by a : to display command and help. Limit 5
	 */

	private void sendHelpMessages(Player p, int page, String... args) {
		p.sendMessage(ChatColor.GOLD + "--------------" + ChatColor.DARK_AQUA + "[The Survival Games]" + ChatColor.DARK_AQUA + "--------------");
		p.sendMessage(ChatColor.GOLD + "Page: " + ChatColor.GREEN + page + ChatColor.GOLD + " of 3");
		for (String s : args) {
			String[] split = s.split(": ");

			p.sendMessage(ChatColor.GREEN + split[0] + ChatColor.DARK_AQUA + ": " + ChatColor.GOLD + split[1]);
		}
	}
}
