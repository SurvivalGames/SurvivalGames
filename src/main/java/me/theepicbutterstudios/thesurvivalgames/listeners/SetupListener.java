/**
 * Name: SetupListener.java
 * Created: 7 December 2013
 *
 * @version 1.0.0
 */
package me.theepicbutterstudios.thesurvivalgames.listeners;

import me.theepicbutterstudios.thesurvivalgames.managers.ArenaManager;
import me.theepicbutterstudios.thesurvivalgames.objects.SGArena;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class SetupListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (ArenaManager.getManager().getCreators().containsKey(e.getPlayer().getName())) {
            SGArena a = ArenaManager.getManager().getArena(e.getPlayer());

            a.nextSpawn(e.getClickedBlock().getLocation());
            e.getPlayer().sendMessage(ArenaManager.getManager().prefix + "Spawn " + a.locs.size() + " set!");
        }
    }

}
