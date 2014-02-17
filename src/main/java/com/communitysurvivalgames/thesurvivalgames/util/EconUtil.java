package com.communitysurvivalgames.thesurvivalgames.util;

import org.bukkit.entity.Player;

import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.objects.PlayerData;

public class EconUtil {
	public static void addPoints(Player p, int i) {
		PlayerData pd = SGApi.getPlugin().getPlayerData(p);
		pd.addPoints(i);
		SGApi.getPlugin().setPlayerData(pd);
	}

	public static void removePoints(Player p, int i) {
		PlayerData pd = SGApi.getPlugin().getPlayerData(p);
		pd.removePoints(i);
		SGApi.getPlugin().setPlayerData(pd);
	}
}
