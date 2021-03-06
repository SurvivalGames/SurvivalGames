package co.q64.survivalgames.rollback;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

import co.q64.survivalgames.objects.SGArena;

public class Rollback implements Runnable {

	final SGArena arena;
	List<ChangedBlock> data;

	public Rollback(SGArena a) {
		this.data = a.getChangedBlocks();
		arena = a;
	}

	@Override
	public void run() {

		List<ChangedBlock> data = this.data;

		for (int i = 0; i < data.size(); i++) {
			Bukkit.getLogger().info("Resetting block: " + data.get(i).getPrevid().toString());
			Location l = new Location(Bukkit.getWorld(data.get(i).getWorld()), data.get(i).getX(), data.get(i).getY(), data.get(i).getZ());
			Block b = l.getBlock();
			b.setType(data.get(i).getPrevid());
			b.setData(data.get(i).getPrevdata());
			b.getState().update();
		}
		arena.restart();
		data.clear();
	}

}