package co.q64.paradisesurvivalgames.util;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import co.q64.paradisesurvivalgames.managers.SGApi;

public class RecoverBlocks {
	public static void recover(List<Block> blocks, final List<Material> replace) {
		int index = 0;
		int delay = 200;
		for (final Block b : blocks) {
			final int i = index;
			new BukkitRunnable() {
				public void run() {
					b.setType(replace.get(i));
				}
			}.runTaskLater(SGApi.getPlugin(), delay);
			index++;
			delay += 20;
		}
	}
}
