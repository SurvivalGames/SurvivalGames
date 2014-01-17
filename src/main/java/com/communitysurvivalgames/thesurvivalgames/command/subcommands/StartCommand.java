/**
 * Name: StartCommand.java Created: 10 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.command.subcommands;

import com.communitysurvivalgames.thesurvivalgames.command.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.exception.ArenaNotFoundException;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.ArenaManager;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
            SGArena a;
            try {
                a = ArenaManager.getManager().getArena(id);
            } catch (ArenaNotFoundException e) {
                Bukkit.getLogger().severe(e.getMessage());
                return;
            }

            if (args[1].equals("starting") && p.hasPermission("sg.gamestate.starting")) {
                if (!a.getState().equals(SGArena.ArenaState.STARTING_COUNTDOWN) || a.getState().isConvertable(a, SGArena.ArenaState.STARTING_COUNTDOWN)) {
                    p.sendMessage(ArenaManager.getManager().error + I18N.getLocaleString("CANT_FORCE"));
                    return;
                }
                a.setState(SGArena.ArenaState.STARTING_COUNTDOWN);
                SGApi.getTimeManager().countdownLobby(1);
                p.sendMessage(ArenaManager.getManager().prefix + I18N.getLocaleString("CHANGED_STATE"));
                return;
            }

            if (args[1].equals("game") && p.hasPermission("sg.gamestate.ingame")) {
                if (!a.getState().equals(SGArena.ArenaState.IN_GAME) || a.getState().isConvertable(a, SGArena.ArenaState.IN_GAME)) {
                    p.sendMessage(ArenaManager.getManager().error + I18N.getLocaleString("CANT_FORCE"));
                    return;
                }
                a.setState(SGArena.ArenaState.IN_GAME);
                SGApi.getTimeManager().countdown();
                p.sendMessage(ArenaManager.getManager().prefix + I18N.getLocaleString("CHANGED_STATE"));
                return;
            }

            if (args[1].equals("dm") && p.hasPermission("sg.gamestate.dm")) {
                if (!a.getState().equals(SGArena.ArenaState.DEATHMATCH) || a.getState().isConvertable(a, SGArena.ArenaState.DEATHMATCH)) {
                    p.sendMessage(ArenaManager.getManager().error + I18N.getLocaleString("CANT_FORCE"));
                    return;
                }
                a.setState(SGArena.ArenaState.DEATHMATCH);
                p.sendMessage(ArenaManager.getManager().prefix + I18N.getLocaleString("CHANGED_STATE"));
                return;
            }

            if (args[1].equals("dm") && p.hasPermission("sg.gamestate.dm")) {
                if (!a.getState().equals(SGArena.ArenaState.IN_GAME) || a.getState().isConvertable(a, SGArena.ArenaState.IN_GAME)) {
                    p.sendMessage(ArenaManager.getManager().error + "You can't change force anything yet.");
                    return;
                }
                a.setState(SGArena.ArenaState.DEATHMATCH);
                SGApi.getTimeManager().countdownDm();
                p.sendMessage(ArenaManager.getManager().prefix + I18N.getLocaleString("CHANGED_STATE"));
            }
        }
    }

}
