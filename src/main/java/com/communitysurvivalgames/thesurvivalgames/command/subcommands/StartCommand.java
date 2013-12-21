/**
 * Name: StartCommand.java Created: 10 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.command.subcommands;

import org.bukkit.entity.Player;

import com.communitysurvivalgames.thesurvivalgames.command.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.local.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.ArenaManager;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;

public class StartCommand implements SubCommand {

    /**
     * The start command. DO NOT CALL DIRECTLY. Only use in CommandHandler
     *
     * @param cmd The command that was executed
     * @param p The player that executed the command
     * @param args The arguments after the command
     */
    @Override
    public void execute(String cmd, Player p, String[] args) {
        if (cmd.equalsIgnoreCase("start") && args.length == 2 && p.hasPermission("sg.gamestate.start")) {
            int id = 0;
            try {
                id = Integer.parseInt(args[0]);
            } catch (NumberFormatException x) {
                p.sendMessage(ArenaManager.getManager().error + I18N.getLocaleString("INVALID_ARENA") + args[0]);
            }
            SGArena a = ArenaManager.getManager().getArena(id);
            if (a == null) {
                p.sendMessage(ArenaManager.getManager().error + I18N.getLocaleString("NOT_VALID"));
                return;
            }

            if (args[1].equals("pre") && p.hasPermission("sg.gamestate.pregame")) {
                if (!a.getState().equals(SGArena.ArenaState.PRE_GAME) || a.getState().isConvertable(a, SGArena.ArenaState.PRE_GAME)) {
                    p.sendMessage(ArenaManager.getManager().error + I18N.getLocaleString("CANT_FORCE"));
                    return;
                }
                a.setState(SGArena.ArenaState.PRE_GAME);
                p.sendMessage(ArenaManager.getManager().prefix + I18N.getLocaleString("CHANGED_STATE"));
                return;
            }

            if (args[1].equals("starting") && p.hasPermission("sg.gamestate.starting")) {
                if (!a.getState().equals(SGArena.ArenaState.STARTING_COUNTDOWN) || a.getState().isConvertable(a, SGArena.ArenaState.STARTING_COUNTDOWN)) {
                    p.sendMessage(ArenaManager.getManager().error + I18N.getLocaleString("CANT_FORCE"));
                    return;
                }
                a.setState(SGArena.ArenaState.STARTING_COUNTDOWN);
                p.sendMessage(ArenaManager.getManager().prefix + I18N.getLocaleString("CHANGED_STATE"));
                return;
            }

            if (args[1].equals("game") && p.hasPermission("sg.gamestate.ingame")) {
                if (!a.getState().equals(SGArena.ArenaState.IN_GAME) || a.getState().isConvertable(a, SGArena.ArenaState.IN_GAME)) {
                    p.sendMessage(ArenaManager.getManager().error + I18N.getLocaleString("CANT_FORCE"));
                    return;
                }
                a.setState(SGArena.ArenaState.IN_GAME);
                p.sendMessage(ArenaManager.getManager().prefix + I18N.getLocaleString("CHANGED_STATE"));
                return;
            }

            if (args[1].equals("dm") && p.hasPermission("sg.gamestate.dm")) {
                /* TODO the death match
                 if (!a.getState().equals(SGArena.ArenaState.IN_GAME) || a.getState().isConvertable(a, SGArena.ArenaState.IN_GAME)) {
                 p.sendMessage(ArenaManager.getManager().error + "You can't change force anything yet.");
                 return;
                 }
                 a.setState(SGArena.ArenaState.IN_GAME);
                 p.sendMessage(ArenaManager.getManager().prefix + "Changed the state.");
                 return;
                 */
            }
        }
    }

}
