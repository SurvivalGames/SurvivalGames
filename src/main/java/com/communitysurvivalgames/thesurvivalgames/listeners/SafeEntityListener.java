/**
 * Name: SafeEntityListener.java 
 * Created: 29 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.List;

public class SafeEntityListener implements Listener {

    public static List<String> safe = new ArrayList<String>();

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            if(safe.contains(((Player) e.getEntity()).getName())) {
                e.setCancelled(true);
            }
        }
    }
    
    public static List<String> getPlayers() {
        return safe;    
    }

}
