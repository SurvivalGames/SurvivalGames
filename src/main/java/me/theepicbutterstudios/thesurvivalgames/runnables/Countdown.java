/**
 * Name: Countdown.java Created: 8 December 2013
 *
 * @version 1.0.0
 */
package me.theepicbutterstudios.thesurvivalgames.runnables;

import me.theepicbutterstudios.thesurvivalgames.objects.SGArena;
import org.bukkit.Bukkit;

public class Countdown implements Runnable {

    SGArena a = null;
    int count = 0;
    int id = 0;

    /**
     * Constructs a new countdown for this arena
     *
     * @param a The arena to countdown on
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
            a.broadcast("May the odds be ever in your favor");
            Bukkit.getServer().getScheduler().cancelTask(this.id);
            return;
        }

        a.broadcast("This arena is starting in " + count);
        count--;

    }

}
