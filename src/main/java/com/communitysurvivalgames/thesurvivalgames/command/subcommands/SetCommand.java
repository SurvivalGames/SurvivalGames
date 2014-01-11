/**
 * Name: CreateCommand.java 
 * Created: 21 December 2013 
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.command.subcommands;

import com.communitysurvivalgames.thesurvivalgames.command.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.exception.ArenaNotFoundException;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.ArenaManager;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

public class SetCommand implements SubCommand {
    //TODO permissions

    /**
     * The create command. DO NOT CALL DIRECTLY. Only use in CommandHandler
     *
     * @param cmd  The command that was executed
     * @param p    The player that executed the command
     * @param args The arguments after the command
     */
    @Override
    public void execute(String cmd, Player p, String[] args) {
        try {
            if (cmd.equalsIgnoreCase("setlobby") && p.hasPermission("sg.create")) {
                int i;
                try {
                    i = Integer.parseInt(args[0]);
                } catch (NumberFormatException x) {
                    p.sendMessage(ArenaManager.getManager().error + I18N.getLocaleString("NOT_NUMBER"));
                    return;
                }

                SGArena a;
				try {
					a = ArenaManager.getManager().getArena(i);
				} catch (ArenaNotFoundException e) {
					Bukkit.getLogger().severe(e.getMessage());
					return;
				}
                a.lobby = p.getLocation();

                p.sendMessage(ArenaManager.getManager().prefix + I18N.getLocaleString("CREATING_LOBBY") + " " + a.getId());
            } else if (cmd.equalsIgnoreCase("setdeathmatch")) {
                int i;
                try {
                    i = Integer.parseInt(args[0]);
                } catch (NumberFormatException x) {
                    p.sendMessage(ArenaManager.getManager().error + I18N.getLocaleString("NOT_NUMBER"));
                    return;
                }

                SGArena a;
				try {
					a = ArenaManager.getManager().getArena(i);
				} catch (ArenaNotFoundException e) {
					Bukkit.getLogger().severe(e.getMessage());
					return;
				}
                a.lobby = p.getLocation();

                p.sendMessage(ArenaManager.getManager().prefix + I18N.getLocaleString("CREATING_DM") + " " + a.getId());
            } else if (cmd.equalsIgnoreCase("setmaxplayers")) {
                int i;
                int amount;
                try {
                    i = Integer.parseInt(args[0]);
                    amount = Integer.parseInt(args[1]);
                } catch (NumberFormatException x) {
                    p.sendMessage(ArenaManager.getManager().error + I18N.getLocaleString("NOT_NUMBER"));
                    return;
                }

                SGArena a;
				try {
					a = ArenaManager.getManager().getArena(i);
				} catch (ArenaNotFoundException e) {
					Bukkit.getLogger().severe(e.getMessage());
					return;
				}
                a.maxPlayers = amount;

                p.sendMessage(ArenaManager.getManager().prefix + I18N.getLocaleString("SET_MAXPLAYERS") + " " + a.getId());
            } else if (cmd.equalsIgnoreCase("setchest")) {
                int i;
                try {
                    i = Integer.parseInt(args[1]);
                } catch (NumberFormatException x) {
                    p.sendMessage(ArenaManager.getManager().error + I18N.getLocaleString("NOT_NUMBER"));
                    return;
                }
                SGArena a;
				try {
					a = ArenaManager.getManager().getArena(i);
				} catch (ArenaNotFoundException e) {
					Bukkit.getLogger().severe(e.getMessage());
					return;
				}

                BlockIterator bit = new BlockIterator(p, 6);
                Block next;
                while (bit.hasNext()) {
                    next = bit.next();
                    if (next.getType() == Material.CHEST) {
                        if (args[0].equalsIgnoreCase("t2") && !a.t2.contains(next.getState())) {
                            a.t2.add(next.getState());
                        } else if (args[0].equalsIgnoreCase("t1") && a.t2.contains(next.getState())) {
                            a.t2.remove(next.getState());
                        } else {
                            p.chat("/sg help");
                        }
                    }
                }

                p.sendMessage(ArenaManager.getManager().prefix + I18N.getLocaleString("SET_CHEST") + " " + a.getId());
            } else if (cmd.equalsIgnoreCase("setgamespawn")) {
                int i;
                int spawn;
                try {
                    spawn = Integer.parseInt(args[0]);
                    i = Integer.parseInt(args[1]);
                } catch (NumberFormatException x) {
                    p.sendMessage(ArenaManager.getManager().error + I18N.getLocaleString("NOT_NUMBER"));
                    return;
                }
                SGArena a;
				try {
					a = ArenaManager.getManager().getArena(i);
				} catch (ArenaNotFoundException e) {
					Bukkit.getLogger().severe(e.getMessage());
					return;
				}
                a.locs.set(spawn - 1, p.getLocation());

                p.sendMessage(ArenaManager.getManager().prefix + I18N.getLocaleString("SET_SPAWN") + " " + a.getId());
            }
        } catch (ArrayIndexOutOfBoundsException x) {
            p.sendMessage(ArenaManager.getManager().error + I18N.getLocaleString("INVALID_ARGUMENTS"));
        }
    }
}
