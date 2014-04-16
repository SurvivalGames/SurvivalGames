package co.q64.paradisesurvivalgames.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import co.q64.paradisesurvivalgames.util.player.items.SGItem;
import co.q64.paradisesurvivalgames.util.player.items.ce.SingleExecutor;

public class ItemManager implements Listener {

	private static ItemManager instance;

	private SGItem clock;
	private SGItem compass;
	private SGItem gem;
	private SGItem star;

	public ItemManager() {

		ItemStack emerald = new ItemStack(Material.EMERALD);
		ItemMeta meta = emerald.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN.toString() + ChatColor.BOLD + "Click to vote for a map");
		emerald.setItemMeta(meta);

		ItemStack compassItem = new ItemStack(Material.COMPASS);
		ItemMeta compassmeta = compassItem.getItemMeta();
		compassmeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Click to join a SG game");
		compassItem.setItemMeta(compassmeta);

		ItemStack clockItem = new ItemStack(Material.WATCH);
		ItemMeta clockmeta = clockItem.getItemMeta();
		clockmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Click to connect to the soundserver");
		clockItem.setItemMeta(clockmeta);

		ItemStack starItem = new ItemStack(Material.NETHER_STAR);
		ItemMeta starmeta = starItem.getItemMeta();
		starmeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Click to spectate a player");
		starItem.setItemMeta(starmeta);

		setClock(new SGItem(clockItem, 8, true, false, new SingleExecutor() {

			@Override
			public void use(Player p) {
				p.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮");
				p.sendMessage(ChatColor.AQUA + "");
				p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Want to here LIVE music, announcers, and sound effects?");
				p.sendMessage(ChatColor.AQUA + "");
				p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Click this link:");
				p.sendMessage(ChatColor.WHITE + "" + ChatColor.UNDERLINE + "http://sg.q64.co/sg/index.html?name=" + p.getName() + "&session=" + SGApi.getPlugin().getPluginConfig().getServerIP());
				p.sendMessage(ChatColor.AQUA + "");
				p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Simply leave your browser window open in the background, turn up your speakers, and we'll do the rest!");
				p.sendMessage(ChatColor.AQUA + "");
				p.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮");

			}
		}));

		setCompass(new SGItem(compassItem, 0, true, false, new SingleExecutor() {

			@Override
			public void use(Player player) {
				MenuManager.getMenuManager().displayJoinMenu(player);
			}
		}));

		setGem(new SGItem(emerald, 0, true, true, new SingleExecutor() {

			@Override
			public void use(Player player) {
				MenuManager.getMenuManager().displayVoteMenu(player);
			}
		}));

		setStar(new SGItem(starItem, 0, false, true, new SingleExecutor() {

			@Override
			public void use(Player player) {
				MenuManager.getMenuManager().displaySpecMenu(player);
			}
		}));
	}

	public static void register() {
		setInstance(new ItemManager());
		SGApi.getPlugin().getServer().getPluginManager().registerEvents(getInstance(), SGApi.getPlugin());
	}

	public static ItemManager getInstance() {
		return instance;
	}

	public static void setInstance(final ItemManager instance) {
		ItemManager.instance = instance;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onWorldChange(final PlayerChangedWorldEvent event) {
		if (event.getPlayer().getWorld().equals(Bukkit.getWorld(SGApi.getPlugin().getPluginConfig().getHubWorld()))) {
			event.getPlayer().getInventory().clear();
			if (SGApi.getPlugin().getPluginConfig().getUseServers())
				getClock().givePlayerItem(event.getPlayer());
			getCompass().givePlayerItem(event.getPlayer());
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(final PlayerJoinEvent event) {
		Bukkit.getScheduler().runTaskLater(SGApi.getPlugin(), new Runnable() {

			@Override
			public void run() {
				if (event.getPlayer().getWorld().equals(Bukkit.getWorld(SGApi.getPlugin().getPluginConfig().getHubWorld()))) {
					event.getPlayer().getInventory().clear();
					if (SGApi.getPlugin().getPluginConfig().getUseServers())
						getClock().givePlayerItem(event.getPlayer());
					getCompass().givePlayerItem(event.getPlayer());
				}
			}

		}, 10L);

	}

	public SGItem getClock() {
		return clock;
	}

	public void setClock(final SGItem clock) {
		this.clock = clock;
	}

	public SGItem getCompass() {
		return compass;
	}

	public void setCompass(final SGItem compass) {
		this.compass = compass;
	}

	public SGItem getGem() {
		return gem;
	}

	public void setGem(final SGItem gem) {
		this.gem = gem;
	}

	public SGItem getStar() {
		return star;
	}

	public void setStar(final SGItem star) {
		this.star = star;
	}
}
