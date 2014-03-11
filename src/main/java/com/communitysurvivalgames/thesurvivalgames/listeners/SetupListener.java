/**
 * Name: SetupListener.java Created: 7 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.listeners;

import src.main.java.com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.multiworld.SGWorld;

public class SetupListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (SGApi.getArenaManager().getCreators().containsKey(e.getPlayer().getName()) && e.getAction() == Action.LEFT_CLICK_BLOCK) {
            SGWorld a = SGApi.getArenaManager().getCreators().get(e.getPlayer().getName());

            a.nextSpawn(e.getClickedBlock().getLocation());
            e.getPlayer().sendMessage(SGApi.getArenaManager().prefix + "Spawn " + a.locs.size() + " set!");
        }
    }

}
