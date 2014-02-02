package com.communitysurvivalgames.thesurvivalgames.configs;

import com.communitysurvivalgames.thesurvivalgames.kits.Kit;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.objects.MapHash;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArenaConfigTemplate extends ConfigTemplate<SGArena> {
    private SGArena arena;

    private SGArena cachedArena;

    public ArenaConfigTemplate(File file) {
        super(file);
    }

    public ArenaConfigTemplate(SGArena arena) {
        super(new String[] {
            "Current-state", "Id", "Lobby",
            "Current-map", "Voted", "Votes",
            "Kits", "Max-players", "Min-players",
            "Players", "Spectators"
        }, "/arenas/" + arena.getId() + ".yml");
        this.arena = arena;
    }

    @Override
    public Object toFile(int keyPair) {
        switch (keyPair) {
            case 0:
                return arena.getState().name();
            case 1:
                return arena.getId();
            case 2:
                return SGApi.getArenaManager().serializeLoc(arena.lobby);
            case 3:
                return arena.getArenaWorld().getName();
            case 4:
                return arena.voted;
            case 5:
                return serializeMaps();
            case 6:
                return serializeKit();
            case 7:
                return arena.getMaxPlayers();
            case 8:
                return arena.getMinPlayers();
            case 9:
                return arena.getPlayers();
            case 10:
                return arena.getSpectators();
        }
        return null;
    }

    @Override
    public SGArena fromFile(int index, Object o) {
        this.cachedArena = new SGArena();
        switch (index) {
            case 0:
                this.cachedArena.setState(SGArena.ArenaState.valueOf(String.valueOf(o)));
                break;
            case 1:
                this.cachedArena.createArena(Integer.parseInt(String.valueOf(o)));
                break;
            case 2:
                this.cachedArena.lobby = SGApi.getArenaManager().deserializeLoc(String.valueOf(o));
                break;
            case 3:
                this.cachedArena.currentMap = SGApi.getMultiWorldManager().worldForName(String.valueOf(o));
                break;
            case 4:
                this.cachedArena.voted = (List<String>) o;
                break;
            case 5:
                this.cachedArena.votes = deserializeMaps((List<String>) o);
                break;
            case 6:
                this.cachedArena.kits = deserializeKit((List<String>) o);
                break;
            case 7:
                this.cachedArena.maxPlayers = Integer.parseInt(String.valueOf(o));
                break;
            case 8:
                this.cachedArena.minPlayers = Integer.parseInt(String.valueOf(o));
                break;
            case 9:
                for(String s : (List<String>) o) {
                    this.cachedArena.players.add(s);
                }
                break;
            case 10:
                for(String s : (List<String>) o) {
                    this.cachedArena.spectators.add(s);
                }
                break;
        }
        return null;
    }

    public List<String> serializeMaps() {
        List<String> list = new ArrayList<>();
        for(Map.Entry<MapHash, Integer> entry : arena.votes.entrySet()) {
            list.add(entry.getKey().getId() + ":" + entry.getKey().getWorld().getWorld().getName() + ":" + entry.getValue());
        }
        return list;
    }

    public Map<MapHash, Integer> deserializeMaps(List<String> list) {
        Map<MapHash, Integer> mapHashIntegerMap = new HashMap<>();
        for(String s : list) {
            String[] strings = s.split(":");
            mapHashIntegerMap.put(new MapHash(SGApi.getMultiWorldManager().worldForName(strings[1]), Integer.parseInt(strings[0])), Integer.parseInt(strings[2]));
        }
        return mapHashIntegerMap;
    }

    public List<String> serializeKit() {
        List<String> list = new ArrayList<>();
        for(Map.Entry<String, Kit> entry : arena.kits.entrySet()) {
            list.add(entry.getKey() + ":");  //TODO
        }
        return list;
    }

    public Map<String, Kit> deserializeKit(List<String> list) {
        Map<String, Kit> map = new HashMap<>();
        for(String s : list) {
            String[] sp = s.split(":");
            //TODO
        }
        return map;
    }
}
