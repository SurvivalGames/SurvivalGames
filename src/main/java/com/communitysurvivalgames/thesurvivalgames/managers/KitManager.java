package com.communitysurvivalgames.thesurvivalgames.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.communitysurvivalgames.thesurvivalgames.TheSurvivalGames;
import com.communitysurvivalgames.thesurvivalgames.exception.ArenaNotFoundException;
import com.communitysurvivalgames.thesurvivalgames.kits.Kit;
import com.communitysurvivalgames.thesurvivalgames.util.IconMenu;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class KitManager {
	private static KitManager km = new KitManager();
	private List<Kit> kits = new ArrayList<Kit>();
	private IconMenu menu;

	public static KitManager getKitManager() {
		return km;
	}

	public void loadKits() {

        String[] files = TheSurvivalGames.getPlugin(TheSurvivalGames.class).getDataFolder().list();

		do {
            for (String file : files) {
                if (file.startsWith("kit_")) {
                    FileConfiguration kit = YamlConfiguration.loadConfiguration(new File(TheSurvivalGames.getPlugin(TheSurvivalGames.class).getDataFolder(), file));
                    // TODO Load kits here im to lazy now gonna do it later
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
        }, TheSurvivalGames.getPlugin(TheSurvivalGames.class));
		int index = 0;
		for (Kit k : kits) {
			index++;
            menu.setOption(index, k.getIcon(), k.getName(), k.getIconLore());
        }
    }

    void saveDefaultKits() {
        TheSurvivalGames.getPlugin(TheSurvivalGames.class).saveResource("kit_knight", false);
	}

    Kit getKit(String name) {
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
