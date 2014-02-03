/**
 * Name: CreateCommand.java Created: 25 November 2013 Edited: 7 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg;

import com.communitysurvivalgames.thesurvivalgames.command.subcommands.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.multiworld.SGWorld;
import org.bukkit.entity.Player;

public class CreateCommand implements SubCommand {

	/**
	 * The create command. DO NOT CALL DIRECTLY. Only use in CommandHandler
	 * 
	 * @param cmd The command that was executed
	 * @param p The player that executed the command
	 * @param args The arguments after the command
	 */
	@Override
	public void execute(String cmd, Player p, String[] args) {
		if (cmd.equalsIgnoreCase("create") && p.hasPermission("sg.create")) {
			try {
				if (args[0].equalsIgnoreCase("custom")) {
					SGApi.getArenaManager().createArena(p, args[1], args[2]);
					p.sendMessage(SGApi.getArenaManager().prefix + I18N.getLocaleString("CREATING_ARENA")); // TODO
					// This
					// should
					// be
					// moved,
					// as
					// the
					// creation
					// happens
					// in
					// a
					// Runnable
					// now
				} else if (args[0].equalsIgnoreCase("download")) {
					SGApi.getArenaManager().createArenaFromDownload(p, args[1]);
				} else if (args[0].equalsIgnoreCase("import")) {
					SGApi.getArenaManager().createArenaFromImport(p, args[1]);
				}
				return;
			} catch (ArrayIndexOutOfBoundsException x) {
				p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("INVALID_ARGUMENTS"));
			}
		}

		if (cmd.equalsIgnoreCase("finish")) {
			SGWorld a = SGApi.getArenaManager().getCreators().get(p.getName());
			SGApi.getArenaManager().getCreators().remove(p.getName());
			p.sendMessage(SGApi.getArenaManager().prefix + I18N.getLocaleString("FINISHED"));
		}
	}
}
