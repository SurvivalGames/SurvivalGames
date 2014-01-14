package com.communitysurvivalgames.thesurvivalgames.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.communitysurvivalgames.thesurvivalgames.TheSurvivalGames;
import com.communitysurvivalgames.thesurvivalgames.exception.ArenaNotFoundException;
import com.communitysurvivalgames.thesurvivalgames.kits.Kit;
import com.communitysurvivalgames.thesurvivalgames.util.IconMenu;

public class KitManager {
	private static KitManager km = new KitManager();
	private List<Kit> kits = new ArrayList<Kit>();
	private IconMenu menu;

	public static KitManager getKitManager() {
		return km;
	}

	public void loadKits() {

		String[] files = TheSurvivalGames.getPlugin().getDataFolder().list();

		do {
			for (int i = 0; i < files.length; i++) {
				if (files[i].startsWith("kit_")) {
					FileConfiguration kit = YamlConfiguration.loadConfiguration(new File(TheSurvivalGames.getPlugin().getDataFolder(), files[i]));
					//TODO Load kits here im to lazy now gonna do it later
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
					ArenaManager.getManager().getArena(event.getPlayer()).setPlayerKit(event.getPlayer(), getKit(event.getName()));
				} catch (ArenaNotFoundException e) {
					e.printStackTrace();
				}
				event.setWillClose(true);
			}
		}, TheSurvivalGames.getPlugin());
		int index = 0;
		for (Kit k : kits) {
			index++;
			menu.setOption(index, k.getIcon(), k.getName(), k.getIconLore());
		}
	}

	public void saveDefaultKits() {
		TheSurvivalGames.getPlugin().saveResource("kit_knight", false);
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
