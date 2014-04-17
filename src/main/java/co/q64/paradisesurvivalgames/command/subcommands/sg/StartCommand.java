/**
 * Name: StartCommand.java Created: 10 December 2013
 *
 * @version 1.0.0
 */

package co.q64.paradisesurvivalgames.command.subcommands.sg;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import co.q64.paradisesurvivalgames.command.subcommands.SubCommand;
import co.q64.paradisesurvivalgames.exception.ArenaNotFoundException;
import co.q64.paradisesurvivalgames.locale.I18N;
import co.q64.paradisesurvivalgames.managers.SGApi;
import co.q64.paradisesurvivalgames.objects.SGArena;

public class StartCommand implements SubCommand {

	/**
	 * The start command. DO NOT CALL DIRECTLY. Only use in CommandHandler
	 *
	 * @param cmd  The command that was executed
	 * @param p    The player that executed the command
	 * @param args The arguments after the command
	 */
	@Override
	public void execute(String cmd, Player p, String[] args) {
		if (p.isOp() || p.hasPermission("sg.start")) {
			try {
				SGArena a = SGApi.getArenaManager().getArena(Integer.parseInt(args[0]));
				a.forceStart();
			} catch (NumberFormatException | ArenaNotFoundException e) {
				p.sendMessage("That's not a valid arena");
			}
				
			
		}
	}
}
