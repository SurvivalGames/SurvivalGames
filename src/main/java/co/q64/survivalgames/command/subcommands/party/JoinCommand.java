/**
 * Name: JoinCommand.java Created: 8 December 2013
 *
 * @version 1.0.0
 */

package co.q64.survivalgames.command.subcommands.party;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import co.q64.survivalgames.command.subcommands.SubCommand;
import co.q64.survivalgames.locale.I18N;
import co.q64.survivalgames.managers.SGApi;
import co.q64.survivalgames.objects.Party;

public class JoinCommand implements SubCommand {

	/**
	 * Joins a party if you have been invited to one
	 *
	 * @param sender The player executing the command
	 */
	@Override
	public void execute(String cmd, Player sender, String[] args) {
		if ((args.length == 1) && (args[0].equalsIgnoreCase("join"))) {
			UUID partyID = SGApi.getPartyManager().getInvites().get(sender.getName());
			if (partyID != null) {
				Party party = SGApi.getPartyManager().getParties().get(partyID);
				if (party != null) {
					if (SGApi.getPartyManager().getParties().get(partyID).hasRoom()) {
						party.addMember(sender.getName());
						SGApi.getPartyManager().getPlayers().put(sender.getName(), partyID);
						sender.sendMessage(org.bukkit.ChatColor.YELLOW + I18N.getLocaleString("YOU_JOINED") + party.getLeader() + I18N.getLocaleString("PARTY"));
						Player player = Bukkit.getServer().getPlayer(party.getLeader());
						player.sendMessage(org.bukkit.ChatColor.YELLOW + sender.getName() + I18N.getLocaleString("HAS_JOINED_YOUR_PARTY"));
						for (String member : party.getMembers()) {
							if (member != null) {
								Player p = Bukkit.getServer().getPlayer(member);
								if (p != null) {
									p.sendMessage(org.bukkit.ChatColor.YELLOW + sender.getName() + I18N.getLocaleString("HAS_JOINED_YOUR_PARTY"));
								}
							}
						}
					} else {
						sender.sendMessage(org.bukkit.ChatColor.YELLOW + I18N.getLocaleString("PARTY_FULL_2"));
					}

				} else {
					sender.sendMessage(org.bukkit.ChatColor.YELLOW + I18N.getLocaleString("NO_PARTY"));
				}
				SGApi.getPartyManager().getInvites().remove(sender.getName());
			} else {
				sender.sendMessage(org.bukkit.ChatColor.YELLOW + I18N.getLocaleString("NO_INVITE"));
			}
		}
	}
}
