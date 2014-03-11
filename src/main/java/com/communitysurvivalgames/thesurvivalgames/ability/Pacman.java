package com.communitysurvivalgames.thesurvivalgames.ability;

import src.main.java.com.communitysurvivalgames.thesurvivalgames.util.FireworkEffectPlayer;

public class Pacman extends SGAbility implements Listener {
	public Pacman() {
		super(5);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (this.hasAbility(player)) {
			if (player.getItemInHand().getType() == Material.GLOWSTONE_DUST && player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("Power Orb")) {
				ItemStack item = player.getItemInHand();
				item.setAmount(1);
				player.getInventory().remove(item);
				player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 2));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 2));
				FireworkEffect fEffect = FireworkEffect.builder().flicker(false).withColor(Color.YELLOW).withFade(Color.YELLOW).with(Type.STAR).trail(false).build();
				try {
					FireworkEffectPlayer.getFireworkEffectPlayer().playFirework(event.getPlayer().getWorld(), event.getPlayer().getLocation(), fEffect);
				} catch (Exception e) {
					//If the firework dosen't work... to bad 
				}
			}
		}
	}
}
