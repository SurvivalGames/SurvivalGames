package co.q64.survivalgames.command.subcommands.sg;

import org.bukkit.entity.Player;

import co.q64.survivalgames.command.subcommands.SubCommand;
import co.q64.survivalgames.exception.ArenaNotFoundException;
import co.q64.survivalgames.locale.I18N;
import co.q64.survivalgames.managers.SGApi;

public class VoteCommand implements SubCommand {

	@Override
	public void execute(String cmd, Player p, String[] args) {
		if ((cmd.equalsIgnoreCase("vote") || cmd.equalsIgnoreCase("v"))) {
			int map;
			try {
				map = Integer.parseInt(args[0]);
			} catch (NumberFormatException x) {
				p.sendMessage(SGApi.getArenaManager().getError() + I18N.getLocaleString("NOT_NUMBER"));
				return;
			}
			if (SGApi.getArenaManager().isInGame(p)) {
				try {
					SGApi.getArenaManager().getArena(p).vote(p, map);
				} catch (ArenaNotFoundException e) {
					e.printStackTrace();
				}
			} else {
				p.sendMessage(SGApi.getArenaManager().getError() + I18N.getLocaleString("LOL_NOPE"));
			}
		}
	}

}
