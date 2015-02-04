package co.q64.survivalgames.ability;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import co.q64.survivalgames.listeners.BlockListener;
import co.q64.survivalgames.util.FireworkEffectPlayer;

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

	@EventHandler(priority = EventPriority.HIGH)
	public void onBlockPlace(BlockPlaceEvent event) {
		if (!event.getBlock().getType().equals(Material.DIAMOND_ORE))
			return;
		BlockListener.addBreakable(event.getBlock());
	}
}
