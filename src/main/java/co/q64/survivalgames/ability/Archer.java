package co.q64.survivalgames.ability;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import co.q64.survivalgames.event.GameStartEvent;
import co.q64.survivalgames.managers.SGApi;

public class Archer extends SGAbility implements Listener {

	public Archer() {
		super(2);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onGameStart(GameStartEvent event) {
		for (UUID p : event.getArena().getPlayers()) {
			final Player player = Bukkit.getPlayer(p);
			if (hasAbility(player)) {
				for (int i = 0; i < 32; i++) {
					Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {

						@Override
						public void run() {
							player.getInventory().addItem(new ItemStack(Material.ARROW));
						}
					}, i * 20L);
				}
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
