/**
 * Name: LeaveCommand.java
 * Created: 29 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg;

import com.communitysurvivalgames.thesurvivalgames.command.subcommands.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import org.bukkit.entity.Player;

public class LeaveCommand implements SubCommand {

    @Override
    public void execute(String cmd, Player p, String[] args) {
        if (cmd.equalsIgnoreCase("leave")) {
            if (SGApi.getArenaManager().isInGame(p)) {
                SGApi.getArenaManager().removePlayer(p);
                p.sendMessage(SGApi.getArenaManager().prefix + I18N.getLocaleString("LEFT_ARENA"));
            } else {
                p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("LOL_NOPE"));
            }
        }
    }

}
