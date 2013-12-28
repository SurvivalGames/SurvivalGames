/**
 * Name: TimeManager.java
 * Created: 28 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.managers;

import com.communitysurvivalgames.thesurvivalgames.TheSurvivalGames;
import com.communitysurvivalgames.thesurvivalgames.listeners.MoveListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.SafeEntityListener;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;
import com.communitysurvivalgames.thesurvivalgames.runnables.CodeExecutor;
import com.communitysurvivalgames.thesurvivalgames.runnables.Countdown;
import org.bukkit.Bukkit;

public class TimeManager {

    private static SGArena a;
    private static final TimeManager tm = new TimeManager();

    private TimeManager() { }
    public static TimeManager getInstance(SGArena arena) {
        a = arena;
        return tm;
    }

    public void countdownLobby() {
        Countdown c = new Countdown(a, 1, 5, "Game", "minutes", new CodeExecutor() {
            @Override
            public void runCode() {
                a.broadcast(I18N.getLocaleString("GAME_STARTING"));
                a.setState(SGArena.ArenaState.STARTING_COUNTDOWN);
                for(int i = 0; i <= a.maxPlayers; i++) {
                    org.bukkit.entity.Player p = Bukkit.getPlayerExact(a.getPlayers().get(i));
                    org.bukkit.Location loc = a.locs.get(i);
                    p.teleport(loc);
                    MoveListener.getPlayers().add(a.getPlayers().get(i));

                    countdown();
                }
            }
        });
        c.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(TheSurvivalGames.getPlugin(), c, 0L, 60*20L));
    }

    public void countdown() {
        Countdown c = new Countdown(a, 1, 10, "Game", "seconds", new CodeExecutor() {
            @Override
            public void runCode() {
                a.broadcast(I18N.getLocaleString("ODDS"));
                a.setState(SGArena.ArenaState.IN_GAME);
                for(String s : a.getPlayers()) {
                    if(MoveListener.getPlayers().contains(s)) {
                        MoveListener.getPlayers().remove(s);
                    }
                }

                countdownDm();
            }
        });
        c.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(TheSurvivalGames.getPlugin(), c, 0L, 20L));
    }
    
    public void countdownDm() {
        Countdown c = new Countdown(a, 5, 30, "DeathMatch", "minutes", new CodeExecutor() {
            @Override
            public void runCode() {
                a.broadcast(I18N.getLocaleString("DM_STARTING"));
                a.setState(SGArena.ArenaState.DEATHMATCH);
                SafeEntityListener.getPlayers().addAll(a.getPlayers());
                //tp to deathmatch

                commenceDm();
            }
        });
        c.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(TheSurvivalGames.getPlugin(), c, 0L, 5*60*20L));
    }
    
    public void commenceDm() {
        Countdown c = new Countdown(a, 1, 10, "DeathMatch", "seconds", new CodeExecutor() {
            @Override
            public void runCode() {
                a.broadcast(I18N.getLocaleString("START"));
                SafeEntityListener.getPlayers().removeAll(a.getPlayers());

                countdownEnd();
            }
        });
        c.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(TheSurvivalGames.getPlugin(), c, 0L, 20L));
    }
    
    public void countdownEnd() {
        Countdown c = new Countdown(a, 1, 5, "EndGame", "minutes", new CodeExecutor() {
            @Override
            public void runCode() {
                a.broadcast(I18N.getLocaleString("END") + " TIED_GAME");
                //tp out of arena, rollback, pick up all items and arrows
            }
        });
        c.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(TheSurvivalGames.getPlugin(), c, 0L, 60*20L));
    }
    
}
