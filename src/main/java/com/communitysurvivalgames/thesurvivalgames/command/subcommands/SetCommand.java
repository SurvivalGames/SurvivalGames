/**
 * Name: CreateCommand.java 
 * Created: 21 December 2013 
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.command.subcommands;

import org.bukkit.entity.Player;

import com.communitysurvivalgames.thesurvivalgames.command.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.local.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.ArenaManager;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;

public class SetCommand implements SubCommand {
        //TODO setspawn and setchest

        /**
         * The create command. DO NOT CALL DIRECTLY. Only use in CommandHandler
         *
         * @param cmd The command that was executed
         * @param p The player that executed the command
         * @param args The arguments after the command
         */
        @Override
        public void execute(String cmd, Player p, String[] args) {
                if (cmd.equalsIgnoreCase("setlobby") && args.length == 1 && p.hasPermission("sg.create")) {
                        int i = 0;
                        try {
                            i = Integer.parseInt(args[0]);
                        } catch(NumberFormatException x) {
                            p.sendMessage(ArenaManager.getManager().error + "Not a real number");
                            return;
                        }    
                        
                        SGArena a = ArenaManager.getManager().getArena(i);
                        a.lobby = p.getLocation();
                        
                        p.sendMessage(ArenaManager.getManager().prefix + /* I18N.getLocaleString("CREATING_ARENA") */ "Lobby spawn set for " + a.getId());
                } else if(cmd.equalsIgnoreCase("setdeathmatch")) {
                        int i = 0;
                        try {
                            i = Integer.parseInt(args[0]);
                        } catch(NumberFormatException x) {
                            p.sendMessage(ArenaManager.getManager().error + "Not a real number");
                            return;
                        }    
                        
                        SGArena a = ArenaManager.getManager().getArena(i);
                        a.lobby = p.getLocation();
                        
                        p.sendMessage(ArenaManager.getManager().prefix + /* I18N.getLocaleString("CREATING_ARENA") */ "Deathmatch spawn set for " + a.getId());
                }  else if(cmd.equalsIgnoreCase("setmaxplayers") && args.length == 2) {
                        int i = 0;
                        int amount = 0;
                        try {
                            i = Integer.parseInt(args[0]);
                            amount = Integer.parseInt(args[1])
                        } catch(NumberFormatException x) {
                            p.sendMessage(ArenaManager.getManager().error + "Not a real number");
                            return;
                        }    
                        
                        SGArena a = ArenaManager.getManager().getArena(i);
                        a.maxPlayers = args[1];
                        
                        p.sendMessage(ArenaManager.getManager().prefix + /* I18N.getLocaleString("CREATING_ARENA") */ "Deathmatch spawn set for " + a.getId());
                } else if(args.length != 1 || args.length != 2) {
                        p.sendMessage(ArenaManager.getManager().error + "Those aren't the arguments");
                }
        }
}
