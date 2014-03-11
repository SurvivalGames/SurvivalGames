package com.communitysurvivalgames.thesurvivalgames.ability;

import src.main.java.com.communitysurvivalgames.thesurvivalgames.exception.ArenaNotFoundException;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.objects.SGArena;

public class SGAbility {
	int id;

	public SGAbility(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public boolean hasAbility(Player p) {
		try {
			if (SGApi.getArenaManager().getArena(p).getState().equals(SGArena.ArenaState.IN_GAME) || SGApi.getArenaManager().getArena(p).getState().equals(SGArena.ArenaState.DEATHMATCH)) {
				if (SGApi.getKitManager().getKit(p) != null) {
					if (SGApi.getKitManager().getKit(p).getAbilityIds().contains(id)) {
						return true;
					}
				}
				return false;
			}
		} catch (ArenaNotFoundException e) {
			return false;
		}
		return false;
	}

	public boolean hasAbility(String p) {
		try {
			if (SGApi.getArenaManager().getArena(Bukkit.getPlayer(p)).getState().equals(SGArena.ArenaState.IN_GAME) || SGApi.getArenaManager().getArena(Bukkit.getPlayer(p)).getState().equals(SGArena.ArenaState.DEATHMATCH)) {
				if (SGApi.getKitManager().getKit(Bukkit.getPlayer(p)) != null) {
					if (SGApi.getKitManager().getKit(Bukkit.getPlayer(p)).getAbilityIds().contains(id)) {
						return true;
					}
				}
				return false;
			}
		} catch (ArenaNotFoundException e) {
			return false;
		}
		return false;
	}
}
