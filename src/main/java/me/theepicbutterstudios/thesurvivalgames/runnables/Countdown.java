package me.theepicbutterstudios.thesurvivalgames.runnables;

import me.theepicbutterstudios.thesurvivalgames.SGArena;

import org.bukkit.Bukkit;

public class Countdown implements Runnable {

    SGArena a = null;
    int count = 0;
    int id = 0;
    
    public Countdown(SGArena a, int count) {
        this.a = a;
        this.count = count;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        
        if(count == 0) {
            a.broadcast("May the odds be ever in your favor");
            Bukkit.getServer().getScheduler().cancelTask(this.id);
            return;
        }
        
        a.broadcast("This arena is starting in " + count);
        count--;
        
    }

}
