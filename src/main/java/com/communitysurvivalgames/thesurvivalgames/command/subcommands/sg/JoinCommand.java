/**
 * Name: JoinCommand.java Created: 7 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg;

import com.communitysurvivalgames.thesurvivalgames.command.subcommands.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import org.bukkit.entity.Player;

public class JoinCommand implements SubCommand {

    /**
     * The join command. DO NOT CALL DIRECTLY. Only use in CommandHandler
     * 
     * @param cmd The command that was executed
     * @param p The player that executed the command
     * @param args The arguments after the command
     */
    @Override
    public void execute(String cmd, Player p, String[] args) {
        if (cmd.equalsIgnoreCase("join") && args.length >= 1 || p.hasPermission("sg.join")) {
            int id;
            try {
                id = Integer.parseInt(args[0]);
            } catch (NumberFormatException x) {
                p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("INVALID_ARENA") + args[0]);
                return;
            }

            SGApi.getArenaManager().addPlayer(p, id);
        } else if (args.length <= 0) {
            p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("CANT_JOIN"));
        }
    }
}
