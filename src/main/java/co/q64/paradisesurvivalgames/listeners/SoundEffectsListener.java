package co.q64.paradisesurvivalgames.listeners;

import co.q64.paradisesurvivalgames.net.WebsocketSessionManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class SoundEffectsListener implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onProjectileHit(ProjectileHitEvent event) {
        if ((event.getEntity() instanceof Projectile)) {
            Projectile a = (Projectile) event.getEntity();
            if (a.getShooter() instanceof Player) {
                Player p = (Player) a.getShooter();
                Bukkit.getLogger().info("Player " + p.getName() + " hit something with an arrow, " +
                        "so I think I'll play a sound");
                Bukkit.getLogger().info("Got a session: " + WebsocketSessionManager.getSessionManager()
                        .getSessionByName(p.getName()));
                //SendWebsocketData.playToPlayer(p, "headshot");
            }
        }
    }
}
