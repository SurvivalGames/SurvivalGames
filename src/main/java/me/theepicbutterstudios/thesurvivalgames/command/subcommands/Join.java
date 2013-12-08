/**
 * Name: Join.java
 * Created: 7 December 2013
 *
 * @version 1.0.0
 */

package me.theepicbutterstudios.thesurvivalgames.command.subcommands;

import me.theepicbutterstudios.thesurvivalgames.command.SubCommand;
import me.theepicbutterstudios.thesurvivalgames.managers.ArenaManager;

import org.bukkit.entity.Player;

public class Join implements SubCommand {

	/**
	 * The join command. DO NOT CALL DIRECTLY. Only use in CommandHandler
	 *
	 * @param cmd The command that was executed
	 * @param p The player that executed the command
	 * @param args The arguments after the command
	 */

	@Override
	public void execute(String cmd, Player p, String[] args) {
		if (cmd.equalsIgnoreCase("join") && args.length >= 1) {
			int id = 0;
			try {
				id = Integer.parseInt(args[0]);
			} catch (NumberFormatException x) {
				p.sendMessage(ArenaManager.getManager().error + "Invalid arena: " + args[0]);
				return;
			}

			ArenaManager.getManager().addPlayer(p, id);
			return;
		}
	}
}
