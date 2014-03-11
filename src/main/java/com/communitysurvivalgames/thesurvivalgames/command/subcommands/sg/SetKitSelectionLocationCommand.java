package com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg;

import src.main.java.com.communitysurvivalgames.thesurvivalgames.command.subcommands.SubCommand;

public class SetKitSelectionLocationCommand implements SubCommand {

	@Override
	public void execute(String cmd, Player p, String[] args) {
		p.getWorld().spawnEntity(p.getLocation(), EntityType.ENDER_CRYSTAL);
	}

}
