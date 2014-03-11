package com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg;

import src.main.java.com.communitysurvivalgames.thesurvivalgames.command.subcommands.SubCommand;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.objects.PlayerData;

public class UserCommand implements SubCommand {

    @Override
    public void execute(String cmd, Player p, String[] args) {
        // TODO Something is getting Null Pointered here every single time -
        // probably SQL save function, can't be sure
        PlayerData data = SGApi.getPlugin().getPlayerData(Bukkit.getPlayer(args[1]));
       if (cmd.equalsIgnoreCase("points") && p.hasPermission("sg.points")) {
            if (args[0].equalsIgnoreCase("set")) {
                data.setPoints(Integer.parseInt(args[2]));
                SGApi.getPlugin().setPlayerData(data);
          } else if (args[0].equalsIgnoreCase("add")) {
                data.setPoints(data.getPoints() + Integer.parseInt(args[2]));
                SGApi.getPlugin().setPlayerData(data);
        } else if (args[0].equalsIgnoreCase("remove")) {
                data.setPoints(data.getPoints() - Integer.parseInt(args[2]));
                SGApi.getPlugin().setPlayerData(data);
    }
        } else if (cmd.equalsIgnoreCase("rank")) {
            if (args[0].equalsIgnoreCase("set")) {
                data.setRank(args[2].toUpperCase());
                SGApi.getPlugin().setPlayerData(data);
            }
      }
    }

}
