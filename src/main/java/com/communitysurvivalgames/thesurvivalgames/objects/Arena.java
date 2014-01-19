/**
 * Name: Arena.java Edited: 16 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.objects;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.experimental.Builder;
import com.communitysurvivalgames.thesurvivalgames.util.LocationType;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

/**
 * The type Arena.
 */
@Builder(builderMethodName = "arena")
@SerializableAs("Arena")
public class Arena implements ConfigurationSerializable {
    /**
     * The Name of the map
     */
    @Getter
    private String name;
    /**
     * The World that the map is in
     */
    @Getter
    private String world;
    /**
     * The Max players allowed in the arena
     */
    @Getter
    private int maxPlayers;
    /**
     * The Min players allowed in the arena
     */
    @Getter
    private int minPlayers;

    /**
     * The Location type.
     */
    @Getter
    private LocationType locationType;

    /**
     * The Arena status.
     */
    @Getter
    private ArenaStatus arenaStatus;

    /**
     * Serialize the Object
     * 
     * @return Map containing the current state of this class
     */
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>(4);
        map.put("name", getName());
        map.put("world", getWorld());
        map.put("max", getMaxPlayers());
        map.put("min", getMinPlayers());
        map.put("type", getLocationType().name());
        map.put("status", getArenaStatus().name());
        return map;
    }

    /**
     * Deserialize arena. You should not be using this method directly
     * 
     * @param map the map
     * @return the arena
     */
    public static Arena deserialize(Map<String, Object> map) {
        Object oName = map.get("name"), oWorld = map.get("world"), oMin = map.get("min"), oMax = map.get("max"), oType = map.get("type"), oStatus = map.get("status");
        if (oName == null || oWorld == null || oMin == null || oMax == null || oType == null || oStatus == null) {
            return null;
        }

        String n = (String) oName;
        String w = (String) oWorld;
        Integer mi = (Integer) (oMin);
        Integer mx = (Integer) oMax;
        LocationType lt = LocationType.valueOf((String) oType);
        ArenaStatus st = ArenaStatus.valueOf((String) oStatus);
        return Arena.arena().name(n).world(w).minPlayers(mi).maxPlayers(mx).locationType(lt).build();
    }
}
