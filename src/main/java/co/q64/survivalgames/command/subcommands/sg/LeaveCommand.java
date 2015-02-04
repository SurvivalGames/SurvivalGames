/**
 * Name: LeaveCommand.java
 * Created: 29 December 2013
 *
 * @version 1.0.0
 */

package co.q64.survivalgames.command.subcommands.sg;

import org.bukkit.entity.Player;

import co.q64.survivalgames.command.subcommands.SubCommand;
import co.q64.survivalgames.exception.ArenaNotFoundException;
import co.q64.survivalgames.locale.I18N;
import co.q64.survivalgames.managers.SGApi;
import co.q64.survivalgames.objects.SGArena;

public class LeaveCommand implements SubCommand {

	@Override
	public void execute(String cmd, Player p, String[] args) {
		if (cmd.equalsIgnoreCase("leave")) {
			if (SGApi.getArenaManager().isInGame(p)) {
				try {
					if (SGApi.getArenaManager().getArena(p).getState().equals(SGArena.ArenaState.WAITING_FOR_PLAYERS) || SGApi.getArenaManager().getArena(p).getState().equals(SGArena.ArenaState.WAITING_FOR_PLAYERS))
						SGApi.getArenaManager().removePlayer(p);
					else
						SGApi.getArenaManager().playerDeathAndLeave(p, SGApi.getArenaManager().getArena(p));
				} catch (ArenaNotFoundException e) {
					p.sendMessage(SGApi.getArenaManager().getError() + I18N.getLocaleString("LOL_NOPE"));
					return;
				}
				p.sendMessage(SGApi.getArenaManager().getPrefix() + I18N.getLocaleString("LEFT_ARENA"));
			} else {
				p.sendMessage(SGApi.getArenaManager().getError() + I18N.getLocaleString("LOL_NOPE"));
			}
		}
	}

}
