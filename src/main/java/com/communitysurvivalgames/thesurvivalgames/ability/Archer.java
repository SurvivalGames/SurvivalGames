package com.communitysurvivalgames.thesurvivalgames.ability;

import src.main.java.com.communitysurvivalgames.thesurvivalgames.event.GameStartEvent;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.managers.SGApi;

public class Archer extends SGAbility implements Listener {

	public Archer() {
		super(2);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onGameStart(GameStartEvent event) {
		for (String p : event.getArena().getPlayers()) {
			final Player player = Bukkit.getPlayer(p);
			if (hasAbility(player)) {
				Bukkit.getScheduler().scheduleSyncRepeatingTask(SGApi.getPlugin(), new Runnable() {

					@Override
					public void run() {
						if (!player.getInventory().contains(Material.ARROW, 3))
							player.getInventory().addItem(new ItemStack(Material.ARROW));
					}
				}, 20L, 20L);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEvent event) {
		if (hasAbility(event.getPlayer())) {
			if (event.getPlayer().getItemInHand().getType() == Material.BOW) {
				event.getPlayer().getItemInHand().addEnchantment(Enchantment.ARROW_DAMAGE, 1);
			}
		}
	}
}
