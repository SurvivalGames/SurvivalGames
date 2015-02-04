package co.q64.survivalgames.managers;

import org.bukkit.Bukkit;

import co.q64.survivalgames.objects.SGArena;
import co.q64.survivalgames.rollback.Rollback;

public class RollbackManager {
	public void rollbackArena(SGArena a) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Rollback(a));
	}
}
