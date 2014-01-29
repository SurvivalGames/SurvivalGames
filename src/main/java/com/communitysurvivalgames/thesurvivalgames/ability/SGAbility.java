package com.communitysurvivalgames.thesurvivalgames.ability;

import org.bukkit.entity.Player;

import com.communitysurvivalgames.thesurvivalgames.exception.ArenaNotFoundException;
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
		try {
			if (SGApi.getArenaManager().getArena(p).getKit(p).getAbailityIds().contains(id))
				return true;
		} catch (ArenaNotFoundException e) {
			//?
		}
		return false;
	}
}
