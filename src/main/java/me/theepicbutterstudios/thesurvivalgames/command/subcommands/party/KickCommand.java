/**
 * Name: KickCommand.java
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

public class KickCommand implements SubCommand {

    /**
     * Kicks a player out of the party if you are the party leader
     *
     * @param sender The player executing the command
     * @param player The player to be kicked from the party
     */

    public void execute(String cmd, Player sender, String[] args) {
        if ((args.length == 1) && (args[0].equalsIgnoreCase("kick"))) {
            UUID id = (UUID) PartyManager.getPartyManager().getPlayers().get(sender.getName());
            if (id != null) {
                Party party = (Party) PartyManager.getPartyManager().getParties().get(id);
                if (party.getLeader().equalsIgnoreCase(sender.getName())) {
                    for (String members : party.getMembers()) {
                        if ((members != null) && (members.equalsIgnoreCase(args[0]))) {
                            party.removeMember(args[0]);
                            PartyManager.getPartyManager().getPlayers().remove(args[0]);
                            sender.sendMessage(org.bukkit.ChatColor.YELLOW + args[0] + " was kicked from the party");
                            Player p = Bukkit.getServer().getPlayer(args[0]);
                            if (p != null) {
                                p.sendMessage(org.bukkit.ChatColor.YELLOW + "You were kicked from the party");
                            }
                            if (party.hasNoMembers()) {
                                PartyManager.endParty(sender.getName(), id);
                            }
                            for (String member : party.getMembers()) {
                                if (member != null) {
                                    Player play = Bukkit.getServer().getPlayer(member);
                                    if (play != null) {
                                        play.sendMessage(org.bukkit.ChatColor.YELLOW + args[0] + " was kicked from the party");
                                    }
                                }
                            }
                            return;
                        }
                    }

                    sender.sendMessage(org.bukkit.ChatColor.YELLOW + "Player " + args[0] + " is not in your party");
                } else {
                    sender.sendMessage(org.bukkit.ChatColor.YELLOW + "You must be the party leader to kick another player");
                }
            } else {
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + "You are not in a party");
            }
        }
    }
}