package me.theepicbutterstudios.thesurvivalgames.command.subcommands;

import me.theepicbutterstudios.thesurvivalgames.command.subcommands.party.ChatCommand;
import me.theepicbutterstudios.thesurvivalgames.command.subcommands.party.DeclineCommand;
import me.theepicbutterstudios.thesurvivalgames.command.subcommands.party.HelpCommand;
import me.theepicbutterstudios.thesurvivalgames.command.subcommands.party.InviteCommand;
import me.theepicbutterstudios.thesurvivalgames.command.subcommands.party.JoinCommand;
import me.theepicbutterstudios.thesurvivalgames.command.subcommands.party.KickCommand;
import me.theepicbutterstudios.thesurvivalgames.command.subcommands.party.LeaveCommand;
import me.theepicbutterstudios.thesurvivalgames.command.subcommands.party.ListCommand;
import me.theepicbutterstudios.thesurvivalgames.command.subcommands.party.PromoteCommand;

import org.bukkit.entity.Player;

public class PartyCommandHandler {

	public boolean execute(String cmd, Player p, String[] args) {
		if ((args.length == 0) || (args[0].equalsIgnoreCase("help")) || (args[0].equalsIgnoreCase("?"))) {
			HelpCommand.execute(p, args);
			return true;
		}

		Player player = p;
		if ((args.length == 1) && (args[0].equalsIgnoreCase("list"))) {
			ListCommand.execute(player);
		} else if ((args.length == 2) && (args[0].equalsIgnoreCase("list")) && (player.hasPermission("partymanager.admin.list"))) {
			ListCommand.execute(player, args[1]);
		} else if ((args.length == 1) && (args[0].equalsIgnoreCase("leave"))) {
			LeaveCommand.execute(player);
		} else if ((args.length == 2) && (args[0].equalsIgnoreCase("invite"))) {
			InviteCommand.execute(player, args[1]);
		} else if ((args.length == 2) && (args[0].equalsIgnoreCase("kick"))) {
			KickCommand.execute(player, args[1]);
		} else if ((args.length == 1) && (args[0].equalsIgnoreCase("join"))) {
			JoinCommand.execute(player);
		} else if ((args.length == 1) && (args[0].equalsIgnoreCase("decline"))) {
			DeclineCommand.execute(player);
		} else if ((args.length == 2) && (args[0].equalsIgnoreCase("promote"))) {
			PromoteCommand.execute(player, args[1]);
		} else if ((args.length == 1) && (args[0].equalsIgnoreCase("chat"))) {
			ChatCommand.execute(player);
		} else {
			player.sendMessage("Please see /sg party for usage information");
		}

		return true;
	}
}
