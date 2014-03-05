/**
 * Name: CreateCommand.java Created: 25 November 2013 Edited: 7 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.communitysurvivalgames.thesurvivalgames.command.subcommands.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;

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
		if (SGApi.getPlugin().getPluginConfig().isBungeecordMode()) {
			Bukkit.getLogger().severe("You're running the server in Bungeecord mode, yet you are not running Bungeecord at all... people these days");
		}
		if (cmd.equalsIgnoreCase("create") && p.hasPermission("sg.create")) {
			try {
				if (args[0].equalsIgnoreCase("custom")) {
					SGApi.getArenaManager().createWorld(p, args[1], args[1]);
					p.sendMessage(SGApi.getArenaManager().prefix + I18N.getLocaleString("CREATING_ARENA"));
				} else if (args[0].equalsIgnoreCase("download")) {
					SGApi.getArenaManager().createWorldFromDownload(p, args[1], args[2]);
				} else if (args[0].equalsIgnoreCase("import")) {
					SGApi.getArenaManager().createWorldFromImport(p, args[1], args[2]);
				}
				return;
			} catch (ArrayIndexOutOfBoundsException x) {
				p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("INVALID_ARGUMENTS"));
			}
		}

		if (cmd.equalsIgnoreCase("finish")) {
			SGApi.getArenaManager().getCreators().remove(p.getName());
			p.sendMessage(SGApi.getArenaManager().prefix + I18N.getLocaleString("FINISHED"));
		}
	}
}
