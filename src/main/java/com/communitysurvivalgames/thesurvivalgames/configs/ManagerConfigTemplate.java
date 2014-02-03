package com.communitysurvivalgames.thesurvivalgames.configs;

import com.communitysurvivalgames.thesurvivalgames.managers.ArenaManager;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.multiworld.SGWorld;
import com.communitysurvivalgames.thesurvivalgames.util.ItemSerialization;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManagerConfigTemplate extends ConfigTemplate<ArenaManager> {

    public ManagerConfigTemplate(File file) {
        super(file);
    }

    public ManagerConfigTemplate() {
        super(new String[] {
            "Creators",
            "Locations",
            "Inventory",
            "Armor",
            "Arena-size"
        }, "ArenaManager.yml");
    }

    @Override
    public Object toFile(int index) {
        switch (index) {
            case 0:
                List<String> list = new ArrayList<>();
                for(Map.Entry<String, SGWorld> entry : SGApi.getArenaManager().getCreators().entrySet()) {
                    list.add(entry.getKey() + ":" + entry.getValue().getWorld().getName());
                }
                return list;
            case 1:
                List<String> stringList = new ArrayList<>();
                for(Map.Entry<String, Location> entry : SGApi.getArenaManager().locs.entrySet()) {
                    stringList.add(entry.getKey() + ":" + SGApi.getArenaManager().serializeLoc(entry.getValue()));
                }
                return stringList;
            case 2:
                List<String> stringArray = new ArrayList<>();
                Inventory inventory = Bukkit.getServer().createInventory(null, 54, "Inv");
                {
                    for(Map.Entry<String, ItemStack[]> entry : SGApi.getArenaManager().inv.entrySet()) {
                        inventory.setContents(entry.getValue());
                        stringArray.add(entry.getKey() + ":" + ItemSerialization.inventoryToString(inventory));
                    }
                }
                return stringArray;
            case 3:
                List<String> armorArray = new ArrayList<>();
                Inventory inv = Bukkit.getServer().createInventory(null, 54, "Inv");
                {
                    for(Map.Entry<String, ItemStack[]> entry : SGApi.getArenaManager().armor.entrySet()) {
                        inv.setContents(entry.getValue());
                        armorArray.add(entry.getKey() + ":" + ItemSerialization.inventoryToString(inv));
                    }
                }
                return armorArray;
            case 4:
                return SGApi.getArenaManager().arenaSize;
        }
        return null;
    }

    @Override
    public ArenaManager fromFile(int index, Object o) {
        switch (index) {
            case 0:
                for(String s : (List<String>) o) {
                    String[] strings = s.split(":");
                    SGApi.getArenaManager().getCreators().put(strings[0], SGApi.getMultiWorldManager().worldForName(strings[1]));
                }
            case 1:
                for(String s : (List<String>) o) {
                    String[] strings = s.split(":");
                    SGApi.getArenaManager().locs.put(strings[0], SGApi.getArenaManager().deserializeLoc(strings[1]));
                }
            case 2:
                for(String s : (List<String>) o) {
                    String[] strings = s.split(":");
                    SGApi.getArenaManager().inv.put(strings[0], ItemSerialization.stringToInventory(strings[1]).getContents());
                }
            case 3:
                for(String s : (List<String>) o) {
                    String[] strings = s.split(":");
                    SGApi.getArenaManager().armor.put(strings[0], ItemSerialization.stringToInventory(strings[1]).getContents());
                }
            case 4:
                SGApi.getArenaManager().arenaSize = Integer.valueOf(String.valueOf(o));
        }
        return SGApi.getArenaManager();
    }
}
