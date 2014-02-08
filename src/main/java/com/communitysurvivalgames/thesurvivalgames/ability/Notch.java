package com.communitysurvivalgames.thesurvivalgames.ability;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.communitysurvivalgames.thesurvivalgames.util.FireworkEffectPlayer;

public class Notch extends SGAbility implements Listener {

	public Notch() {
		super(7);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onBlockBreak(BlockBreakEvent event) {
		if (hasAbility(event.getPlayer()) && event.getBlock().getType() == Material.DIAMOND_ORE) {
			FireworkEffect fEffect = FireworkEffect.builder().flicker(false).withColor(Color.BLUE).withFade(Color.AQUA).with(Type.BALL_LARGE).trail(false).build();
			try {
				FireworkEffectPlayer.getFireworkEffectPlayer().playFirework(event.getPlayer().getWorld(), event.getPlayer().getLocation(), fEffect);
			} catch (Exception e) {
				//If the firework dosen't work... to bad 
			}
		}
	}
}
