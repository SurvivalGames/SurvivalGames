package co.q64.survivalgames.command.subcommands.sg;

import org.bukkit.entity.Player;

import co.q64.survivalgames.command.subcommands.SubCommand;
import co.q64.survivalgames.exception.ArenaNotFoundException;
import co.q64.survivalgames.managers.SGApi;
import co.q64.survivalgames.objects.SGArena;

public class ForceDMCommand implements SubCommand {

	@Override
	public void execute(String cmd, Player p, String[] args) {
		if (p.isOp() || p.hasPermission("sg.start"))  {
			if (args.length > 1){
				try {
					SGArena a = SGApi.getArenaManager().getArena(Integer.parseInt(args[0]));
					SGApi.getTimeManager(a).forceStartDM();
					return;
				} catch (NumberFormatException | ArenaNotFoundException e) {
					p.sendMessage("That's not a valid arena");
				}
			} else{
				try{
					SGArena a = SGApi.getArenaManager().getArena(p);
					SGApi.getTimeManager(a).forceStartDM();
					return;
				} catch (ArenaNotFoundException e){ 
					p.sendMessage("You are not currently in an arena");
				}
			}
		}
	}

}
