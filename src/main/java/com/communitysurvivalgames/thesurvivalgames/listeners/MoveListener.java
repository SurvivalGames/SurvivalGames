/**
 * Name: MoveListener.java
 * Created: 29 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.listeners;

import org.bukkit.Location;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class MoveListener implements Listener {
    
    public static List<String> list = new ArrayList<String>();
    
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Location q = e.getFrom();
        Location w = e.getTo();
    
        if(list.contains(e.getPlayer().getName()) && (q.getBlockX() != w.getBlockX() || q.getBlockZ() != w.getBlockZ())) {
            e.setTo(e.getFrom());
        }
    }
    
    public static List<String> getPlayers() {
        return list;
    }
    
}
