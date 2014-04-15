package co.q64.paradisesurvivalgames.managers;

import co.q64.paradisesurvivalgames.objects.SGArena;
import co.q64.paradisesurvivalgames.rollback.Rollback;
import org.bukkit.Bukkit;

public class RollbackManager {
    public void rollbackArena(SGArena a) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Rollback(a));
    }
}
