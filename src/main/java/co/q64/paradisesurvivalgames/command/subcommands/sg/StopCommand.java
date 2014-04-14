/**
 * Name: StopCommand.java
 * Created: 2 January 2014
 *
 * @verson 1.0
 */
package co.q64.paradisesurvivalgames.command.subcommands.sg;

import co.q64.paradisesurvivalgames.command.subcommands.SubCommand;
import co.q64.paradisesurvivalgames.exception.ArenaNotFoundException;
import co.q64.paradisesurvivalgames.locale.I18N;
import co.q64.paradisesurvivalgames.managers.SGApi;
import co.q64.paradisesurvivalgames.objects.SGArena;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StopCommand implements SubCommand {
	@Override
	public void execute(String cmd, Player p, String[] args) {
		if(!p.hasPermission("sg.stop") || !p.isOp())
			return;
		if (cmd.equalsIgnoreCase("stop")) {
			int i;
			try {
				i = Integer.parseInt(args[0]);
			} catch (NumberFormatException x) {
				p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("NOT_NUMBER"));
				return;
			}
			SGArena a;
			try {
				a = SGApi.getArenaManager().getArena(i);
			} catch (ArenaNotFoundException e) {
				Bukkit.getLogger().severe(e.getMessage());
				return;
			}
			if (a == null) {
				p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("INVALID_ARENA") + a);
				return;
			}
			a.end();
			p.sendMessage(SGApi.getArenaManager().prefix + I18N.getLocaleString("ARENA_END"));
		} else {
			p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("INVALID_ARGUMENTS"));
		}
	}
}
