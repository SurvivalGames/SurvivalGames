package me.theepicbutterstudios.thesurvivalgames.command.subcommand;

import me.theepicbutterstudios.thesurvivalgames.command.SubCommand;

public class Help implements SubCommand {

    public void execute(String cmd, Player p, String[] args) {
        if(cmd.equalsIgnoreCase("help") {
            if(args.length == 0) {
                //display page one of help
            } else if(args.length >= 1) {
                int page = 0;
                try {
                    page = Integer.parseInt(args[0]);
                } catch(NumberFormatException e) {
                    p.sendMessage(/*error prefix*/ + "That help page doesn't exist");
                }
                //send help page for number page
            }
        }
    }

}
