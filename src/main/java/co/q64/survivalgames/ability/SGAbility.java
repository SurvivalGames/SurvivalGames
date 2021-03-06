package co.q64.survivalgames.ability;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import co.q64.survivalgames.exception.ArenaNotFoundException;
import co.q64.survivalgames.managers.SGApi;
import co.q64.survivalgames.objects.SGArena;

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
			if (SGApi.getArenaManager().getArena(p).getState().equals(SGArena.ArenaState.IN_GAME) || SGApi.getArenaManager().getArena(p).getState().equals(SGArena.ArenaState.DEATHMATCH)) {
				if (SGApi.getKitManager().getKit(p) != null) {
					if (SGApi.getKitManager().getKit(p).getAbilityIds().contains(id)) {
						return true;
					}
				}
				return false;
			}
		} catch (ArenaNotFoundException e) {
			return false;
		}
		return false;
	}

	public boolean hasAbility(UUID p) {
		try {
			if (SGApi.getArenaManager().getArena(Bukkit.getPlayer(p)).getState().equals(SGArena.ArenaState.IN_GAME) || SGApi.getArenaManager().getArena(Bukkit.getPlayer(p)).getState().equals(SGArena.ArenaState.DEATHMATCH)) {
				if (SGApi.getKitManager().getKit(Bukkit.getPlayer(p)) != null) {
					if (SGApi.getKitManager().getKit(Bukkit.getPlayer(p)).getAbilityIds().contains(id)) {
						return true;
					}
				}
				return false;
			}
		} catch (ArenaNotFoundException e) {
			return false;
		}
		return false;
	}

	public void removeOneFromHand(Player p) {
		ItemStack item = p.getInventory().getItemInHand();

		int slot = p.getInventory().getHeldItemSlot();
		int amount = item.getAmount() - 1;

		item.setAmount(amount);

		p.getInventory().setItemInHand(item);
	}
}
