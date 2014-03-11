package com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg;

import src.main.java.com.communitysurvivalgames.thesurvivalgames.command.subcommands.SubCommand;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.util.FireworkUtil;

public class TestCommand implements SubCommand {

	@Override
	public void execute(String cmd, Player p, String[] args) {
		if (args[0].equalsIgnoreCase("firework"))
			FireworkUtil.getCircleUtil().playFireworkRing(p, FireworkEffect.builder().withColor(Color.FUCHSIA).withFade(Color.BLUE).trail(true).flicker(false).with(Type.BALL).build(), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
		if (args[0].equalsIgnoreCase("kit"))
			SGApi.getKitManager().displayDefaultKitSelectionMenu(p);
	}

}
