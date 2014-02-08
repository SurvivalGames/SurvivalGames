package com.communitysurvivalgames.thesurvivalgames.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.communitysurvivalgames.thesurvivalgames.exception.ArenaNotFoundException;
import com.communitysurvivalgames.thesurvivalgames.kits.Kit;
import com.communitysurvivalgames.thesurvivalgames.kits.KitItem;
import com.communitysurvivalgames.thesurvivalgames.util.IconMenu;
import com.communitysurvivalgames.thesurvivalgames.util.ItemSerialization;

public class KitManager {
	private List<Kit> kits = new ArrayList<Kit>();
	private IconMenu menu;

	public void loadKits() {
		String[] files = SGApi.getPlugin().getDataFolder().list();
		do {
			for (String file : files) {
				if (file.startsWith("kit_")) {
					FileConfiguration kitData = YamlConfiguration.loadConfiguration(new File(SGApi.getPlugin().getDataFolder(), file));

					String kitName = kitData.getString("name");
					String type = kitData.getString("type");
					Material icon = Material.getMaterial(kitData.getString("icon"));
					String iconLore = kitData.getString("iconLore");
					String serializedInventory = kitData.getString("lvl1.inventory");

                    Inventory inventory = ItemSerialization.stringToInventory(serializedInventory);  // TODO Not a temp solution, this is awesome!
                    List<KitItem> list = new ArrayList<>();
                    for(ItemStack itemStack : inventory) {
                    	KitItem ki = new KitItem();
                    	ki.setItem(itemStack);
                        list.add(ki);
                    }

                    List<Integer> abilityIds = kitData.getIntegerList("ability-ids");
                    
                    kits.add(new Kit(kitName, list, icon, iconLore, abilityIds));
				}
			}

			if (kits.size() == 0)
				saveDefaultKits();
		} while (kits.size() == 0);

		menu = new IconMenu("Select Your Kit", 9, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {
				event.getPlayer().sendMessage("You have chosen the " + event.getName() + " kit!");
				try {
					SGApi.getArenaManager().getArena(event.getPlayer()).setPlayerKit(event.getPlayer(), getKit(event.getName()));
				} catch (ArenaNotFoundException e) {
					e.printStackTrace();
				}
				event.setWillClose(true);
			}
		}, SGApi.getPlugin());
		int index = 0;
		for (Kit k : kits) {
			index++;
			menu.setOption(index, k.getIcon(), k.getName(), k.getIconLore());
		}
	}

	void saveDefaultKits() {
		SGApi.getPlugin().saveResource("kit_knight", false);
	}

	public Kit getKit(String name) {
		for (Kit k : kits) {
			if (k.getName().equalsIgnoreCase(name))
				return k;
		}
		return kits.get(0);
	}

	public List<Kit> getKits() {
		return kits;
	}

	public void displayKitSelectionMenu(Player p) {
		menu.open(p);
	}
}
