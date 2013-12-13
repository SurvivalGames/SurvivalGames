/**
 * Name: StartCommand.java
 * Created: 10 December 2013
 *
 * @version 1.0.0
 */
package me.theepicbutterstudios.thesurvivalgames.command.subcommands;

import me.theepicbutterstudios.thesurvivalgames.command.SubCommand;
import me.theepicbutterstudios.thesurvivalgames.managers.ArenaManager;
import me.theepicbutterstudios.thesurvivalgames.objects.SGArena;
import org.bukkit.entity.Player;

public class StartCommand implements SubCommand {

    /**
     * The start command. DO NOT CALL DIRECTLY. Only use in CommandHandler
     *
     * @param cmd  The command that was executed
     * @param p    The player that executed the command
     * @param args The arguments after the command
     */

    @Override
    public void execute(String cmd, Player p, String[] args) {
        if (cmd.equalsIgnoreCase("start") && args.length == 2 && p.hasPermission("sg.gamestate.start")) {
            int id = 0;
            try {
                id = Integer.parseInt(args[0]);
            } catch (NumberFormatException x) {
                p.sendMessage(ArenaManager.getManager().error + "Invalid arena: " + args[0]);
            }
            SGArena a = ArenaManager.getManager().getArena(id);
            if (a == null) {
                p.sendMessage(ArenaManager.getManager().error + "Thats not a valid arena");
                return;
            }

            if (args[1].equals("pre") && p.hasPermission("sg.gamestate.pregame")) {
                if (!a.getState().equals(SGArena.ArenaState.PRE_GAME) || a.getState().isConvertable(a, SGArena.ArenaState.PRE_GAME)) {
                    p.sendMessage(ArenaManager.getManager().error + "You can't change force anything yet.");
                    return;
                }
                a.setState(SGArena.ArenaState.PRE_GAME);
                p.sendMessage(ArenaManager.getManager().prefix + "Changed the state.");
                return;
            }

            if (args[1].equals("starting") && p.hasPermission("sg.gamestate.starting")) {
                if (!a.getState().equals(SGArena.ArenaState.STARTING_COUNTDOWN) || a.getState().isConvertable(a, SGArena.ArenaState.STARTING_COUNTDOWN)) {
                    p.sendMessage(ArenaManager.getManager().error + "You can't change force anything yet.");
                    return;
                }
                a.setState(SGArena.ArenaState.STARTING_COUNTDOWN);
                p.sendMessage(ArenaManager.getManager().prefix + "Changed the state.");
                return;
            }

            if (args[1].equals("game") && p.hasPermission("sg.gamestate.ingame")) {
                if (!a.getState().equals(SGArena.ArenaState.IN_GAME) || a.getState().isConvertable(a, SGArena.ArenaState.IN_GAME)) {
                    p.sendMessage(ArenaManager.getManager().error + "You can't change force anything yet.");
                    return;
                }
                a.setState(SGArena.ArenaState.IN_GAME);
                p.sendMessage(ArenaManager.getManager().prefix + "Changed the state.");
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
