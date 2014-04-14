package co.q64.paradisesurvivalgames.managers;

import org.bukkit.Bukkit;

import co.q64.paradisesurvivalgames.objects.SGArena;
import co.q64.paradisesurvivalgames.rollback.Rollback;

public class RollbackManager {
	public void rollbackArena(SGArena a) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Rollback(a));
	}
}
