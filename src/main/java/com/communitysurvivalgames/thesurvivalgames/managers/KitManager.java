package com.communitysurvivalgames.thesurvivalgames.managers;

import java.io.File;

import com.communitysurvivalgames.thesurvivalgames.TheSurvivalGames;

public class KitManager {
	private static KitManager km = new KitManager();

	public static KitManager getKitManager() {
		return km;
	}

	public void loadKits() {
		File kitsFolder = new File(TheSurvivalGames.getPlugin().getDataFolder(), "kits");
		if (!(kitsFolder.list().length > 0))
			saveDefaultKits();
	}

	public void saveDefaultKits() {
		
	}
}
