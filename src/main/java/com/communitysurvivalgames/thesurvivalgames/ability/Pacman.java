package com.communitysurvivalgames.thesurvivalgames.ability;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;

import com.communitysurvivalgames.thesurvivalgames.util.FireworkEffectPlayer;

public class Pacman extends SGAbility {
	public Pacman() {
		super(5);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (this.hasAbility(player)) {
			if (player.getItemInHand().getType() == Material.GLOWSTONE_DUST && player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("Zelda Heart")) {

				player.setHealth(player.getHealth() + 1);
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
