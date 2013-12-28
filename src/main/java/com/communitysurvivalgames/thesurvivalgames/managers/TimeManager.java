/**
 * Name: TimeManager.java
 * Created: 28 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.managers;

import com.communitysurvivalgames.thesurvivalgames.TheSurvivalGames;
import com.communitysurvivalgames.thesurvivalgames.object.SGArena;
import com.communitysurvivalgames.thesurvivalgames.runnables.*;

import org.bukkit.Bukkit;

public class TimeManager {

    private static final SGArena a;
    private final TimeManager tm = new TimeManager();

    private TimeManager() { }
    public static void getInstance(SGArena arena) {
        a = arena;
        return tm;
    }

    public void countdownLobby() {
        Countdown c = new Countdown(a, 1, 5, "Game", "minutes", new CodeExecutor {
            a.broadcast(I18N.getLocaleString("GAME_STARTING"));
            a.setState(SGArena.ArenaState.STARTING_COUNTDOWN);
            for(org.bukkit.Location loc : a.locs) {
                for(String s : a.getPlayers()) {
                    org.bukkit.entity.Player p = Bukkit.getPlayerExact(s);
                    if(p != null) {
                        p.teleport(loc);
                        continue;
                    }
                }
                coontinue; //TODO gotta figure this out...
            }
        }
        c.setId(Bukkit.getScheduler().runTaskTimer(TheSurvivalGames.getPlugin(), c, 0L, 60*20L);
    }

    public void countdownGame() {
        Countdown c = new Countdown(a, 5, 5, "DeathMatch", "minutes", new CodeExecutor {
            //TODO deathmatch, tp players.
        }
        c.setId(Bukkit.getScheduler().runTaskTimer(TheSurvivalGames.getPlugin(), c, 0L, 5*60*20L);
    }
}
