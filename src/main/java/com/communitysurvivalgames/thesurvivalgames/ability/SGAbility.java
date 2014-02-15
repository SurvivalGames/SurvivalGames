package com.communitysurvivalgames.thesurvivalgames.ability;

import org.bukkit.entity.Player;

import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;

public class SGAbility {
	int id;

	public SGAbility(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public boolean hasAbility(Player p) {
		if (SGApi.getKitManager().getKit(p) != null) {
			if (SGApi.getKitManager().getKit(p).getAbilityIds().contains(id)) {
				return true;
			}
		}
		return false;
	}
}
