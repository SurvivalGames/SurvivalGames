package com.communitysurvivalgames.thesurvivalgames.util;

import src.main.java.com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.objects.PlayerData;

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

	public static boolean removePoints(Player p, int i) {
		if(econ){
			return economy.withdrawPlayer(p.getName(), i).transactionSuccess();
		}
		PlayerData pd = SGApi.getPlugin().getPlayerData(p);
		pd.removePoints(i);
		SGApi.getPlugin().setPlayerData(pd);
		return true;
	}

	public static boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		econ = (economy != null);
		return econ;
	}
	
	public static boolean isHooked(){
		return econ;
	}
}
