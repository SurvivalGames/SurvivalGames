package co.q64.paradisesurvivalgames.ability;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import co.q64.paradisesurvivalgames.event.GameStartEvent;

public class Knight extends SGAbility implements Listener {

	public Knight() {
		super(0);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onGameStart(GameStartEvent event) {
		for (UUID p : event.getArena().getPlayers()) {
			if (hasAbility(p)) {
				Player player = Bukkit.getPlayer(p);
				player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999, 1, false));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2, false));
			}
		}
	}
}
