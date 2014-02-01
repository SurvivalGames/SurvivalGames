package com.communitysurvivalgames.thesurvivalgames.command.subcommands;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Player;

import com.communitysurvivalgames.thesurvivalgames.command.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.util.CircleUtil;

public class TestCommand implements SubCommand {

	@Override
	public void execute(String cmd, Player p, String[] args) {
			CircleUtil.playFireworkCircle(p, FireworkEffect.builder().withColor(Color.FUCHSIA).withFade(Color.BLUE).trail(true).flicker(false).with(Type.BALL).build(), Integer.parseInt(args[0]), Integer.parseInt(args[1]));
	}

}
