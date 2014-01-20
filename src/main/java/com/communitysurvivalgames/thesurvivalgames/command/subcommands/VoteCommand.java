package com.communitysurvivalgames.thesurvivalgames.command.subcommands;

import com.communitysurvivalgames.thesurvivalgames.command.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import org.bukkit.entity.Player;

public class VoteCommand implements SubCommand {

    @Override
    public void execute(String cmd, Player p, String[] args) {
        if((cmd.equalsIgnoreCase("vote") || cmd.equalsIgnoreCase("v")) && args.length == 1) {
            int map;
            try {
                map = Integer.parseInt(args[0]);
            } catch (NumberFormatException x) {
                p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("NOT_NUMBER"));
                return;
            }
            if(SGApi.getArenaManager().isInGame(p)) {
                SGApi.getArenaManager().getArena(p).vote(p, map);
            } else {
                p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("LOL_NOPE"));
            }
        } else if(args.length != 1) {
            p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("INVALID_ARGUMENTS"));
        }
    }

}
