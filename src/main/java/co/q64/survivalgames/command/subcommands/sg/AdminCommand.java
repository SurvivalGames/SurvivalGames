package co.q64.survivalgames.command.subcommands.sg;

import org.bukkit.entity.Player;

import co.q64.survivalgames.command.subcommands.SubCommand;
import co.q64.survivalgames.util.gui.AdminMenu;

public class AdminCommand implements SubCommand {

	@Override
	public void execute(String cmd, Player p, String[] args) {
		new AdminMenu().display(p);
	} 

}
