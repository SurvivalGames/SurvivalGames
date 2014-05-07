package co.q64.paradisesurvivalgames.command.standalone;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.q64.paradisesurvivalgames.exception.ArenaNotFoundException;
import co.q64.paradisesurvivalgames.managers.SGApi;
import co.q64.paradisesurvivalgames.objects.SGArena;

public class SponsorCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player p = (Player) sender;
		try {
			SGArena a = SGApi.getArenaManager().getArena(p);
			if (a.getPlayers().contains(p.getUniqueId())) {
				p.sendMessage(ChatColor.RED + "You must be dead to sponsor players");
			} else {
				SGApi.getSponsorManager(a).sponsor(p);
			}
		} catch (ArenaNotFoundException e) {
			p.sendMessage(ChatColor.RED + "You must be in a game to use that");
		}
		return false;
	}

}
