package com.communitysurvivalgames.thesurvivalgames.util;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.objects.PlayerData;

public class EconUtil {
	public static Economy economy = null;
	private static boolean econ = false;

	public static void addPoints(Player p, int i) {
		if(econ){
			economy.depositPlayer(p.getName(), i);
			return;
		}
		PlayerData pd = SGApi.getPlugin().getPlayerData(p);
		pd.addPoints(i);
		SGApi.getPlugin().setPlayerData(pd);
	}

	public static void removePoints(Player p, int i) {
		if(econ){
			economy.withdrawPlayer(p.getName(), i);
			return;
		}
		PlayerData pd = SGApi.getPlugin().getPlayerData(p);
		pd.removePoints(i);
		SGApi.getPlugin().setPlayerData(pd);
	}

	public static void setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		econ = (economy != null);
	}
}
