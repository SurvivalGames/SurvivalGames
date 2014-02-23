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
import com.communitysurvivalgames.thesurvivalgames.multiworld.SGWorld;
import com.communitysurvivalgames.thesurvivalgames.objects.MapHash;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;
import com.communitysurvivalgames.thesurvivalgames.runnables.CodeExecutor;
import com.communitysurvivalgames.thesurvivalgames.runnables.Countdown;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TimeManager {

    private SGArena a;

    public TimeManager(SGArena a) {
        this.a = a;
    }

    public void countdownLobby(int n) {
        // setup the voting
        int i = 0;
        List<MapHash> hashes = new ArrayList<>();
        for(SGWorld world : SGApi.getMultiWorldManager().getWorlds()) {
            if(world.getWorld().getPlayers().isEmpty() && i <= 5) {
                MapHash hash = new MapHash(world, i);
                hashes.add(hash);
            }
        }
        for(MapHash hash : hashes) {
            a.votes.put(hash, 0);
        }

        Countdown c = new Countdown(a, 1, n, "Game", "minutes", new CodeExecutor() {
            @Override
            public void runCode() {
                //handle votes
                Map.Entry<MapHash, Integer> maxEntry = null;
                for (Map.Entry<MapHash, Integer> entry : a.votes.entrySet()) {
                    if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                        maxEntry = entry;
                    }
                }
                a.currentMap = maxEntry.getKey().getWorld();
                a.broadcast(SGApi.getArenaManager().prefix + I18N.getLocaleString("MAP_WINNER") + " " + a.currentMap.getWorld().getName());

                Bukkit.getPluginManager().callEvent(new GameStartEvent(a));
                a.broadcast(I18N.getLocaleString("GAME_STARTING"));
                a.setState(SGArena.ArenaState.STARTING_COUNTDOWN);

                int index = 0;
                for (String s : a.getPlayers()) {           
                    Player p = Bukkit.getPlayerExact(s);
                    Location loc = a.currentMap.locs.get(index);
                    p.teleport(loc);               

                    index++;
                }               
                countdown();
                MoveListener.getPlayers().addAll(a.getPlayers());
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
