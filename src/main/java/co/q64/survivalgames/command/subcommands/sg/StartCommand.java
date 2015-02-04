/**
 * Name: StartCommand.java Created: 10 December 2013
 *
 * @version 1.0.0
 */

package co.q64.survivalgames.command.subcommands.sg;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import co.q64.survivalgames.command.subcommands.SubCommand;
import co.q64.survivalgames.exception.ArenaNotFoundException;
import co.q64.survivalgames.locale.I18N;
import co.q64.survivalgames.managers.SGApi;
import co.q64.survivalgames.objects.SGArena;

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
			if (args.length > 1){
				try {
					SGArena a = SGApi.getArenaManager().getArena(Integer.parseInt(args[0]));
					a.forceStart();
				} catch (NumberFormatException | ArenaNotFoundException e) {
					p.sendMessage("That's not a valid arena");
				}
			} else{
				try {
					SGArena a = SGApi.getArenaManager().getArena(p);
					a.forceStart();
				} catch (NumberFormatException | ArenaNotFoundException e) {
					p.sendMessage("You are not currently in an arena");
				}
			}
			
		}
	}
}
