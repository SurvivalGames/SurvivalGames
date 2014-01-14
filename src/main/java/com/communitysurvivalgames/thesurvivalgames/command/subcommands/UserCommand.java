package com.communitysurvivalgames.thesurvivalgames.command.subcommands;

import com.communitysurvivalgames.thesurvivalgames.TheSurvivalGames;
import com.communitysurvivalgames.thesurvivalgames.command.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.objects.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UserCommand implements SubCommand {

    @Override
    public void execute(String cmd, Player p, String[] args) {
        // TODO Something is getting Null Pointered here every single time -
        // probably SQL save function, can't be sure
        PlayerData data = TheSurvivalGames.getPlugin(TheSurvivalGames.class).getPlayerData(Bukkit.getPlayer(args[1]));
        if (cmd.equalsIgnoreCase("points") && p.hasPermission("sg.points")) {
            if (args[0].equalsIgnoreCase("set")) {
                data.setPoints(Integer.parseInt(args[2]));
                TheSurvivalGames.getPlugin(TheSurvivalGames.class).setPlayerData(data);
            } else if (args[0].equalsIgnoreCase("add")) {
                data.setPoints(data.getPoints() + Integer.parseInt(args[2]));
                TheSurvivalGames.getPlugin(TheSurvivalGames.class).setPlayerData(data);
            } else if (args[0].equalsIgnoreCase("remove")) {
                data.setPoints(data.getPoints() - Integer.parseInt(args[2]));
                TheSurvivalGames.getPlugin(TheSurvivalGames.class).setPlayerData(data);
            }
        } else if (cmd.equalsIgnoreCase("rank")) {
            if (args[0].equalsIgnoreCase("set")) {
                data.setRank(args[2].toUpperCase());
                TheSurvivalGames.getPlugin(TheSurvivalGames.class).setPlayerData(data);
            }
        }
    }

}
