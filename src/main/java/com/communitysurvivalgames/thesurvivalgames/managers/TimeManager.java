/**
 * Name: TimeManager.java
 * Created: 28 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.managers;

import com.communitysurvivalgames.thesurvivalgames.event.GameStartEvent;
import com.communitysurvivalgames.thesurvivalgames.listeners.MoveListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.SafeEntityListener;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;
import com.communitysurvivalgames.thesurvivalgames.runnables.CodeExecutor;
import com.communitysurvivalgames.thesurvivalgames.runnables.Countdown;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TimeManager {

    private static SGArena a;

    public TimeManager() {
        // Wait a sec...
    }

    public void countdownLobby(int n) {
        Countdown c = new Countdown(a, 1, n, "Game", "minutes", new CodeExecutor() {
            @Override
            public void runCode() {
                Bukkit.getPluginManager().callEvent(new GameStartEvent(a));
                a.broadcast(I18N.getLocaleString("GAME_STARTING"));
                a.setState(SGArena.ArenaState.STARTING_COUNTDOWN);
                for (int i = 0; i <= a.maxPlayers; i++) {
                    Player p = Bukkit.getPlayerExact(a.getPlayers().get(i));
                    Location loc = a.locs.get(i);
                    p.teleport(loc);
                    MoveListener.getPlayers().add(a.getPlayers().get(i));

                    countdown();
                }
            }
        });
        c.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(SGApi.getPlugin(), c, 0L, 60 * 20L));
    }

    public void countdown() {
        Countdown c = new Countdown(a, 1, 10, "Game", "seconds", new CodeExecutor() {
            @Override
            public void runCode() {
                
                a.broadcast(I18N.getLocaleString("ODDS"));
                a.setState(SGArena.ArenaState.IN_GAME);
                for (String s : a.getPlayers()) {
                    if (MoveListener.getPlayers().contains(s)) {
                        MoveListener.getPlayers().remove(s);
                    }
                }

                countdownDm();
            }
        });
        c.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(SGApi.getPlugin(), c, 0L, 20L));
    }

    public void countdownDm() {
        Countdown c = new Countdown(a, 5, 30, "DeathMatch", "minutes", new CodeExecutor() {
            @Override
            public void runCode() {
                a.broadcast(I18N.getLocaleString("DM_STARTING"));
                a.setState(SGArena.ArenaState.DEATHMATCH);
                SafeEntityListener.getPlayers().addAll(a.getPlayers());
                // tp to deathmatch

                commenceDm();
            }
        });
        c.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(SGApi.getPlugin(), c, 0L, 5 * 60 * 20L));
    }

    void commenceDm() {
        Countdown c = new Countdown(a, 1, 10, "DeathMatch", "seconds", new CodeExecutor() {
            @Override
            public void runCode() {
                a.broadcast(I18N.getLocaleString("START"));
                SafeEntityListener.getPlayers().removeAll(a.getPlayers());

                countdownEnd();
            }
        });
        c.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(SGApi.getPlugin(), c, 0L, 20L));
    }

    void countdownEnd() {
        Countdown c = new Countdown(a, 1, 5, "EndGame", "minutes", new CodeExecutor() {
            @Override
            public void runCode() {
                a.broadcast(I18N.getLocaleString("END") + " TIED_GAME");
                // tp out of arena, rollback, pick up all items and arrows
            }
        });
        c.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(SGApi.getPlugin(), c, 0L, 60 * 20L));
    }

}
