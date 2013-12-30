/**
 * Name: LeaveCommand.java
 * Created: 29 December 2013
 *
 * @version 1.0.0
 */ 
package com.communitysurvivalgames.thesurvivalgames.command.subcommands;

import com.communitysurvivalgames.thesurvivalgames.command.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.ArenaManager;

import org.bukkit.entity.Player;

public class LeaveCommand implements SubCommand {

    @Override
    public void execute(Player p, String cmd, String[] args) {
        if(cmd.equalsIgnoreCase("leave")) {
            if(ArenaManager.getManager().isInGame(p)) {
                ArenaManager.getManager().removePlayer(p);
                p.sendMessage(ArenaManager.getManager().prefix + I18N.getLocaleString("LEFT_ARENA"));
            } else {
                p.sendMessage(ArenaManager.getManager().error + I18N.getLocaleString("LOL_NOPE"));
            }
        }
    }

}
