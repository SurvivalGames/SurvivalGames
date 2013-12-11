/**
 * Name: PromoteCommand.java
 * Created: 8 December 2013
 *
 * @version 1.0.0
 */
package me.theepicbutterstudios.thesurvivalgames.command.subcommands.party;

import me.theepicbutterstudios.thesurvivalgames.command.SubCommand;
import me.theepicbutterstudios.thesurvivalgames.managers.PartyManager;
import me.theepicbutterstudios.thesurvivalgames.objects.Party;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PromoteCommand implements SubCommand {

    /**
     * Promotes another player to party leader if the executer is the current leader
     *
     * @param player The player executing the command
     */

    public void execute(String cmd, Player sender, String[] args) {
        if ((args.length == 2) && (args[0].equalsIgnoreCase("promote"))) {
            UUID id = (UUID) PartyManager.getPartyManager().getPlayers().get(sender.getName());
            if (id != null) {
                Party party = (Party) PartyManager.getPartyManager().getParties().get(id);
                if (party.getLeader().equalsIgnoreCase(sender.getName())) {
                    for (String member : party.getMembers()) {
                        if ((member != null) && (member.equalsIgnoreCase(args[0]))) {
                            Player p = Bukkit.getServer().getPlayer(args[0]);
                            if (p != null) {
                                String oldLeader = party.getLeader();
                                party.setLeader(p.getName());
                                party.removeMember(p.getName());
                                party.addMember(oldLeader);
                                sender.sendMessage(org.bukkit.ChatColor.YELLOW + "You have promoted " + p.getName() + " to leader");
                                p.sendMessage(org.bukkit.ChatColor.YELLOW + sender.getName() + " has promoted you to leader");
                            } else {
                                sender.sendMessage(org.bukkit.ChatColor.YELLOW + args[0] + " is not online");
                            }
                            return;
                        }
                    }

                    sender.sendMessage(org.bukkit.ChatColor.YELLOW + args[0] + " is not in your party");
                } else {
                    sender.sendMessage(org.bukkit.ChatColor.YELLOW + "You must be the party leader to promote another player");
                }
            } else {
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + "You are not in a party");
            }
        }
    }
}