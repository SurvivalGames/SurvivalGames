package com.communitysurvivalgames.thesurvivalgames.net;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;

public class SoundEffectsManager {
	public  static Map<String, String> music = new HashMap<String, String>();
	
	public static void playToPlayer(Player p, String data) {
		if (WebsocketSessionManager.getSessionManager().getSessionByName(p.getName()) != null) {
			WebsocketServer.s.sendData(WebsocketSessionManager.getSessionManager().getSessionByName(p.getName()), data);
		}
	}

	public static void playToArena(SGArena arena, String data) {
		for (String s : arena.getPlayers()) {
			Player p = Bukkit.getPlayer(s);
			if (WebsocketSessionManager.getSessionManager().getSessionByName(p.getName()) != null) {
				WebsocketServer.s.sendData(WebsocketSessionManager.getSessionManager().getSessionByName(p.getName()), data);
			}
		}
	}

	public static void playToAll(String data) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (WebsocketSessionManager.getSessionManager().getSessionByName(p.getName()) != null) {
				WebsocketServer.s.sendData(WebsocketSessionManager.getSessionManager().getSessionByName(p.getName()), data);
			}
		}
	}
}
