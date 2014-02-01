package com.communitysurvivalgames.thesurvivalgames.ability;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.communitysurvivalgames.thesurvivalgames.event.GameStartEvent;
import com.communitysurvivalgames.thesurvivalgames.util.FireworkEffectPlayer;

public class Zelda extends SGAbility implements Listener {
	public Zelda() {
		super(1);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onGameStart(GameStartEvent event) {
		for (String p : event.getArena().getPlayers()) {
			Player player = Bukkit.getPlayer(p);
			player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 99999, 5, false));
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (this.hasAbility(player)) {
			if (player.getItemInHand().getType() == Material.SPECKLED_MELON && player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("Zelda Heart")) {

				player.setHealth(player.getHealth() + 1);
				FireworkEffect fEffect = FireworkEffect.builder().flicker(false).withColor(Color.YELLOW).withFade(Color.GREEN).with(Type.BALL).trail(true).build();
				try {
					FireworkEffectPlayer.getFireworkEffectPlayer().playFirework(event.getPlayer().getWorld(), event.getPlayer().getLocation(), fEffect);
				} catch (Exception e) {
					//If the firework dosen't work... to bad 
				}
			}
		}
	}
}
