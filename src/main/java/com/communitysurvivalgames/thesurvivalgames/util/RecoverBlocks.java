package com.communitysurvivalgames.thesurvivalgames.util;

import src.main.java.com.communitysurvivalgames.thesurvivalgames.managers.SGApi;

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
			delay+=20;
		}
	}
}
