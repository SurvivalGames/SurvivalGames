package co.q64.survivalgames.util;

import org.bukkit.entity.Player;

import co.q64.survivalgames.managers.SGApi;
import co.q64.survivalgames.objects.PlayerData;

public class EconUtil {

	public static void addPoints(Player p, int i) {
		if (SGApi.getPlugin().useEcon()) {
			SGApi.getPlugin().getEcon().depositPlayer(p.getName(), i);
			return;
		}
		PlayerData pd = SGApi.getPlugin().getPlayerData(p);
		pd.addPoints(i);
		SGApi.getPlugin().setPlayerData(pd);
	}

	public static int getPoints(Player p) {
		if (SGApi.getPlugin().useEcon()) {
			return (int) SGApi.getPlugin().getEcon().getBalance(p.getName());
		}
		PlayerData pd = SGApi.getPlugin().getPlayerData(p);
		return pd.getPoints();
	}

	public static boolean isHooked() {
		return SGApi.getPlugin().useEcon();
	}

	public static boolean removePoints(Player p, int i) {
		if (SGApi.getPlugin().useEcon()) {
			return SGApi.getPlugin().getEcon().withdrawPlayer(p.getName(), i).transactionSuccess();
		}
		PlayerData pd = SGApi.getPlugin().getPlayerData(p);
		pd.removePoints(i);
		if (pd.getPoints() < 0)
			return false;
		SGApi.getPlugin().setPlayerData(pd);
		return true;
	}
}
