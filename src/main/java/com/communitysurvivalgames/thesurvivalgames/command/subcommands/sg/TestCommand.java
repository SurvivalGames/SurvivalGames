package com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Player;

import com.communitysurvivalgames.thesurvivalgames.command.subcommands.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.util.CircleUtil;

public class TestCommand implements SubCommand {

	@Override
	public void execute(String cmd, Player p, String[] args) {
		if(args[0] == "firework")
			CircleUtil.getCircleUtil().playFireworkRing(p, FireworkEffect.builder().withColor(Color.FUCHSIA).withFade(Color.BLUE).trail(true).flicker(false).with(Type.BALL).build(), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
		if(args[0] == "kit")
			SGApi.getKitManager().displayKitSelectionMenu(p);
	}

}
