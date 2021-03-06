package co.q64.survivalgames.command.subcommands.sg;

import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import co.q64.survivalgames.command.subcommands.SubCommand;

public class RemoveKitSelectionLocationCommand implements SubCommand {

	@Override
	public void execute(String cmd, Player p, String[] args) {
		for (Entity e : p.getNearbyEntities(20, 20, 20)) {
			if (e instanceof EnderCrystal) {
				e.remove();
			}
		}
	}

}
