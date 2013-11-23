package me.theepicbutterstudios.thesurvivalgames.command;

public interface SubCommand {

    /**
     * The method to execute the command. DO NOT CALL. Use only in CommandHandler/override in SubCommands
     *
     * @param cmd The name of the command to execute
     * @param p The player executing the command
     * @param args The arguments after the command
     */
    
    public void execute(String cmd, Player p, String[] args);

}
