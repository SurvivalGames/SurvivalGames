/**
 * Name: CreateCommand.java Created: 25 November 2013 Edited: 7 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.command.subcommands;

import com.communitysurvivalgames.thesurvivalgames.command.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.ArenaManager;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;
import org.bukkit.entity.Player;

public class CreateCommand implements SubCommand {

	/**
	 * The create command. DO NOT CALL DIRECTLY. Only use in CommandHandler
	 *
	 * @param cmd  The command that was executed
	 * @param p    The player that executed the command
	 * @param args The arguments after the command
	 */
	@Override
	public void execute(String cmd, Player p, String[] args) {
		if (cmd.equalsIgnoreCase("create") && p.hasPermission("sg.create")) {
			if (args[0].equalsIgnoreCase("custom")) {
				SGArena a = ArenaManager.getManager().createArena(p, p.getWorld().getName());
				p.sendMessage(ArenaManager.getManager().prefix + I18N.getLocaleString("CREATING_ARENA") + " " + a.getId());
			} else if (args[0].equalsIgnoreCase("download")) {
				
			} else if (args[0].equalsIgnoreCase("import")) {
				
			}

		}
	}
}
