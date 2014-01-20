package com.communitysurvivalgames.thesurvivalgames.event;

import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStartEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    SGArena arena = null;
    
    public GameStartEvent(SGArena arena) {
        this.arena = arena;    
    }
    
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
