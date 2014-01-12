/**
 * Name: MoveListener.java
 * Created: 29 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.listeners;

import com.communitysurvivalgames.thesurvivalgames.exception.ArenaNotFoundException;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.ArenaManager;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class MoveListener implements Listener {

    private static final List<String> list = new ArrayList<String>();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Location q = e.getFrom();
        Location w = e.getTo();

        if (list.contains(e.getPlayer().getName()) && (q.getBlockX() != w.getBlockX() || q.getBlockZ() != w.getBlockZ())) {
            e.setTo(e.getFrom());
        }

        try {
            if (ArenaManager.getManager().isInGame(e.getPlayer()) &&
                    ArenaManager.getManager().getArena(e.getPlayer()) != null &&
                    ArenaManager.getManager().getArena(e.getPlayer()).getState() == SGArena.ArenaState.DEATHMATCH &&
                    (Math.abs(e.getPlayer().getLocation().distanceSquared(ArenaManager.getManager().getArena(e.getPlayer()).center)) >= 0.5)) {
                e.setTo(e.getFrom());
                e.getPlayer().sendMessage(ArenaManager.getManager().prefix + I18N.getLocaleString("NOT_HAPPY"));
            }
        } catch (ArenaNotFoundException ignored) {
        }
    }

    public static List<String> getPlayers() {
        return list;
    }

}
