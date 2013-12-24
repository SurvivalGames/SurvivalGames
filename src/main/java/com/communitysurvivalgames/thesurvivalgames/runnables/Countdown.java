/**
 * Name: Countdown.java Created: 8 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.runnables;

import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;
import org.bukkit.Bukkit;

class Countdown implements Runnable {

    private SGArena a = null;
    private int count = 0;
    private int id = 0;

    /**
     * Constructs a new countdown for this arena
     *
     * @param a     The arena to countdown on
     * @param count The amount of numbers to countdown from
     */
    public Countdown(SGArena a, int count) {
        this.a = a;
        this.count = count;
    }

    /**
     * Sets the id for the runnable to cancel
     *
     * @param id The return value from starting a new task
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * The overrided method to run
     */
    @Override
    public void run() {

        if (count == 0) {
            a.broadcast(I18N.getLocaleString("ODDS"));
            Bukkit.getServer().getScheduler().cancelTask(this.id);
            return;
        }

        a.broadcast(I18N.getLocaleString("STARTING_IN") + " " + count);
        count--;

    }

}
