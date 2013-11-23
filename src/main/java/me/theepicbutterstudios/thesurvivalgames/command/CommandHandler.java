package me.theepicbutterstudios.thesurvivalgames.command;



public class CommandHandler implements CommandExecutor {
    Map<String, SubCommand> cmd = new HashMap<>();

    /**
     * Registers a command with an attached SubCommand class
     *
     * @param command The name of the com
     *
     */
 
    public static void register(String command, SubCommand sc) {
        if(!cmd.containsKey(command)) {
            cmd.put(command, sc);
        }
    }

