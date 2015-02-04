package co.q64.survivalgames.command.subcommands.sg;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Player;

import co.q64.survivalgames.command.subcommands.SubCommand;
import co.q64.survivalgames.managers.SGApi;
import co.q64.survivalgames.util.FakeBlockUtil;
import co.q64.survivalgames.util.FireworkUtil;

public class TestCommand implements SubCommand {

	@Override
	public void execute(String cmd, Player p, String[] args) {
		if (args[0].equalsIgnoreCase("firework"))
			FireworkUtil.getCircleUtil().playFireworkRing(p, FireworkEffect.builder().withColor(Color.FUCHSIA).withFade(Color.BLUE).trail(true).flicker(false).with(Type.BALL).build(), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
		if (args[0].equalsIgnoreCase("kit"))
			SGApi.getKitManager().displayDefaultKitSelectionMenu(p);
		if (args[0].equalsIgnoreCase("sphere"))
			FakeBlockUtil.createFakeSphere(p);
	}

}
