package com.communitysurvivalgames.thesurvivalgames.command.standalone;

import src.main.java.com.communitysurvivalgames.thesurvivalgames.exception.ArenaNotFoundException;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.objects.SGArena;

public class SponsorCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player p = (Player) sender;
		try {
			SGArena a = SGApi.getArenaManager().getArena(p);
			SGApi.getSponsorManager(a).sponsor(p);
		} catch (ArenaNotFoundException e) {
			p.sendMessage(ChatColor.RED + "You must be in a game to use that");
		}
		return false;
	}

}
