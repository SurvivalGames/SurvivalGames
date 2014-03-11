package com.communitysurvivalgames.thesurvivalgames.ability;

import src.main.java.com.communitysurvivalgames.thesurvivalgames.event.GameStartEvent;

public class Knight extends SGAbility implements Listener {

	public Knight() {
		super(0);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onGameStart(GameStartEvent event) {
		for (String p : event.getArena().getPlayers()) {
			if (hasAbility(p)) {
				Player player = Bukkit.getPlayer(p);
				player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999, 1, false));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2, false));
			}
		}
	}
}
