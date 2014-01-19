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
     * Creates a Map representation of this class.
     * <p/>
     * This class must provide a method to restore this class, as defined in the
     * {@link org.bukkit.configuration.serialization.ConfigurationSerializable}
     * interface javadocs.
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
        return map;
    }

    public static Arena deserialize(Map<String, Object> map) {
        Object oName = map.get("name"), oWorld = map.get("world"), oMin = map.get("min"), oMax = map.get("max");
        if (oName == null || oWorld == null || oMin == null || oMax == null) {
            return null;
        }

        String n = (String) oName;
        String w = (String) oWorld;
        Integer mi = (Integer) (oMin);
        Integer mx = (Integer) oMax;

        return Arena.arena().name(n).world(w).minPlayers(mi).maxPlayers(mx).build();
    }
}
