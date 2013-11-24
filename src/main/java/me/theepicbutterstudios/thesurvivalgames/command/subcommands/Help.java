package me.theepicbutterstudios.thesurvivalgames.command.subcommands;

import me.theepicbutterstudios.thesurvivalgames.command.SubCommand;
import org.bukkit.entity.Player;

public class Help implements SubCommand {

    /**
     * An example of implementing SubCommand. DO NOT CALL DIRECTLY. Only use in CommandHandler
     *
     * @param cmd The command that was executed
     * @param p The player that executed the command
     * @param args The arguments after the command
     */

    @Override
    public void execute(String cmd, Player p, String[] args) {
        if(cmd.equalsIgnoreCase("help")) {
            if(args.length == 0) {
                //display page one of help
            } else if(args.length >= 1) {
                int page = 0;
                try {
                    page = Integer.parseInt(args[0]);
                } catch(NumberFormatException e) {
                    p.sendMessage(/*error prefix*/ "That help page doesn't exist");
                }
                //send help page for number page
            }
            return; //no need to have booleans, this is a method returns void.
        }
    }

}
