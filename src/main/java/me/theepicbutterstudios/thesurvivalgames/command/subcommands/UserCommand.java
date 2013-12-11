package me.theepicbutterstudios.thesurvivalgames.command.subcommands;

import me.theepicbutterstudios.thesurvivalgames.TheSurvivalGames;
import me.theepicbutterstudios.thesurvivalgames.command.SubCommand;
import me.theepicbutterstudios.thesurvivalgames.objects.PlayerData;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UserCommand implements SubCommand {

	@Override
	public void execute(String cmd, Player p, String[] args) {
		PlayerData data = TheSurvivalGames.getPlugin().getPlayerData(Bukkit.getPlayer(args[1]));
		if (cmd.equalsIgnoreCase("points")) {
			if (args[0].equalsIgnoreCase("set")) {
				data.setPoints(Integer.parseInt(args[2]));
			} else if (args[0].equalsIgnoreCase("add")) {
				data.setPoints(data.getPoints() + Integer.parseInt(args[2]));
			} else if (args[0].equalsIgnoreCase("remove")) {
				data.setPoints(data.getPoints() - Integer.parseInt(args[2]));
			}
		} else if (cmd.equalsIgnoreCase("rank")) {
			if (args[0].equalsIgnoreCase("set")) {
				data.setRank(args[2].toUpperCase());
			}
		}
	}

}
