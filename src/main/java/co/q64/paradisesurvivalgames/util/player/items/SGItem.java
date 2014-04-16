package co.q64.paradisesurvivalgames.util.player.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import co.q64.paradisesurvivalgames.managers.SGApi;
import co.q64.paradisesurvivalgames.util.player.items.ce.MultiExecutor;
import co.q64.paradisesurvivalgames.util.player.items.ce.SingleExecutor;

public class SGItem implements Listener {

	private ItemStack item;

	private MultiExecutor me;
	private boolean multiExecutor = false;
	private boolean onlyInGame = false;

	private boolean onlyInHubWorld = false;

	private SingleExecutor se;

	private int slot = 0;
	private List<String> use = new ArrayList<String>();

	public SGItem(ItemStack item, int slot, boolean onlyInHub, boolean onlyInGame, MultiExecutor se) {
		this.multiExecutor = true;
		this.me = me;
		this.slot = slot;
		this.onlyInGame = onlyInGame;
		this.onlyInHubWorld = onlyInHub;
		this.item = item;
		SGApi.getPlugin().getServer().getPluginManager().registerEvents(this, SGApi.getPlugin());
	}

	public SGItem(ItemStack item, int slot, boolean onlyInHub, boolean onlyInGame, SingleExecutor se) {
		this.multiExecutor = false;
		this.se = se;
		this.slot = slot;
		this.onlyInGame = onlyInGame;
		this.onlyInHubWorld = onlyInHub;
		this.item = item;
		SGApi.getPlugin().getServer().getPluginManager().registerEvents(this, SGApi.getPlugin());
	}

	public void givePlayerItem(Player p) {
		p.getInventory().setItem(slot, item);
		p.updateInventory();
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEvent event) {
		if (event.getItem() == null)
			return;
		if (!event.getItem().getType().equals(item.getType()))
			return;
		Player p = event.getPlayer();
		if (onlyInHubWorld) {
			if (p.getWorld().getName().equalsIgnoreCase(SGApi.getPlugin().getPluginConfig().getHubWorld())) {
				if (onlyInGame) {
					if (SGApi.getArenaManager().isInGame(p)) {
						if (multiExecutor) {
							if (use.contains(p.getName())) {
								use.remove(p.getName());
								me.unUse(p);
								return;
							} else {
								use.add(p.getName());
								me.use(p);
								return;
							}
						} else {
							se.use(p);
							return;
						}
					} else {
						return;
					}
				} else {
					if (multiExecutor) {
						if (use.contains(p.getName())) {
							use.remove(p.getName());
							me.unUse(p);
							return;
						} else {
							use.add(p.getName());
							me.use(p);
							return;
						}
					} else {
						se.use(p);
						return;
					}
				}
			} else {
				return;
			}
		} else {
			if (onlyInGame) {
				if (SGApi.getArenaManager().isInGame(p)) {
					if (multiExecutor) {
						if (use.contains(p.getName())) {
							use.remove(p.getName());
							me.unUse(p);
							return;
						} else {
							use.add(p.getName());
							me.use(p);
							return;
						}
					} else {
						se.use(p);
						return;
					}
				} else {
					return;
				}
			} else {
				if (multiExecutor) {
					if (use.contains(p.getName())) {
						use.remove(p.getName());
						me.unUse(p);
						return;
					} else {
						use.add(p.getName());
						me.use(p);
						return;
					}
				} else {
					se.use(p);
					return;
				}
			}
		}
	}
}
