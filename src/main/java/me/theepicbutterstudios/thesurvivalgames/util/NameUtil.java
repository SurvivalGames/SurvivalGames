package me.theepicbutterstudios.thesurvivalgames.util;

import org.bukkit.entity.Player;

public class NameUtil {
	public static int getNameUUID(Player p) {
		return hash(p.getName());
	}

	public static int getNameUUID(String p) {
		return hash(p);
	}

	public static int hash(String s) {
		int h = 0;
		for (int i = 0; i < s.length(); i++) {
			h = 31 * h + s.charAt(i);
		}
		return h;
	}
}
