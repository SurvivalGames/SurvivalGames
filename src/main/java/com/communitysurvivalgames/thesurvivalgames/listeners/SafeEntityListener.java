/**
 * Name: SafeEntityListener.java 
 * Created: 29 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.listeners;


public class SafeEntityListener implements Listener {

    private static final List<String> safe = new ArrayList<>();

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (safe.contains(((Player) e.getEntity()).getName())) {
                e.setCancelled(true);
            }
        }
    }

    public static List<String> getPlayers() {
        return safe;
    }

}
