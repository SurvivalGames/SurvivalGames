package co.q64.paradisesurvivalgames.command.subcommands.sg;

import org.bukkit.entity.Player;

import co.q64.paradisesurvivalgames.command.subcommands.SubCommand;
import co.q64.paradisesurvivalgames.util.gui.AdminMenu;

public class AdminCommand implements SubCommand {

	@Override
	public void execute(String cmd, Player p, String[] args) {
		new AdminMenu().display(p);
	} 

}
