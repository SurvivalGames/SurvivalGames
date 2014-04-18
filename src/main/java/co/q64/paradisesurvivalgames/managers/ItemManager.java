package co.q64.paradisesurvivalgames.managers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import co.q64.paradisesurvivalgames.util.gui.AdminMenu;
import co.q64.paradisesurvivalgames.util.player.items.SGItem;
import co.q64.paradisesurvivalgames.util.player.items.ce.MultiExecutor;
import co.q64.paradisesurvivalgames.util.player.items.ce.SingleExecutor;

public class ItemManager implements Listener {

	private static ItemManager instance;
	private Map<String, SGItem> items = new HashMap<String, SGItem>();
	private FileConfiguration itemsConfig;

	public ItemManager() {

		SGApi.getPlugin().saveResource("items.yml", false);

		itemsConfig = YamlConfiguration.loadConfiguration(new File(SGApi.getPlugin().getDataFolder(), "items.yml"));

		registerItem("admin-item", Material.COMMAND, ChatColor.GREEN.toString() + ChatColor.BOLD + "Admin Setup", 4, true, false, true, new SingleExecutor() {
			
			@Override
			public void use(Player player) {
				new AdminMenu().display(player);
				
			}
		});
		
		registerItem("vote-item", Material.EMERALD, ChatColor.GREEN.toString() + ChatColor.BOLD + "Click to vote for a map", 4, true, true, false, new SingleExecutor() {

			@Override
			public void use(Player player) {
				MenuManager.getMenuManager().displayVoteMenu(player);
			}
		});

		registerItem("join-item", Material.COMPASS, ChatColor.GREEN.toString() + ChatColor.BOLD + "Click to join a SG game", 0, true, false, false, new SingleExecutor() {

			@Override
			public void use(Player player) {
				MenuManager.getMenuManager().displayJoinMenu(player);
			}
		});

		registerItem("connect-item", Material.WATCH, ChatColor.YELLOW.toString() + ChatColor.BOLD + "Click to connect to the soundserver", 8, true, false, false, new SingleExecutor() {

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
		});

		registerItem("spec-item", Material.NETHER_STAR, ChatColor.AQUA.toString() + ChatColor.BOLD + "Click to spectate a player", 0, false, true, false, new SingleExecutor() {

			@Override
			public void use(Player player) {
				MenuManager.getMenuManager().displaySpecMenu(player);
			}
		});
	}

	public static ItemManager getInstance() {
		return instance;
	}

	public static void register() {
		setInstance(new ItemManager());
		SGApi.getPlugin().getServer().getPluginManager().registerEvents(getInstance(), SGApi.getPlugin());
	}

	public static void setInstance(final ItemManager instance) {
		ItemManager.instance = instance;
	}

	public SGItem getItem(String key) {
		return items.get(key);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(final PlayerJoinEvent event) {
		Bukkit.getScheduler().runTaskLater(SGApi.getPlugin(), new Runnable() {

			@Override
			public void run() {
				if (event.getPlayer().getWorld().equals(Bukkit.getWorld(SGApi.getPlugin().getPluginConfig().getHubWorld()))) {
					event.getPlayer().getInventory().clear();
					if (SGApi.getPlugin().getPluginConfig().getUseServers())
						getItem("connect-item").givePlayerItem(event.getPlayer());
					getItem("join-item").givePlayerItem(event.getPlayer());
					if (event.getPlayer().hasPermission("sg.admin")|| event.getPlayer().isOp()){
						getItem("admin-item").givePlayerItem(event.getPlayer());
					}
				}
			}

		}, 10L);

	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onWorldChange(final PlayerChangedWorldEvent event) {
		if (event.getPlayer().getWorld().equals(Bukkit.getWorld(SGApi.getPlugin().getPluginConfig().getHubWorld()))) {
			event.getPlayer().getInventory().clear();
			if (SGApi.getPlugin().getPluginConfig().getUseServers())
				getItem("connect-item").givePlayerItem(event.getPlayer());
			getItem("join-item").givePlayerItem(event.getPlayer());
			if (event.getPlayer().hasPermission("sg.admin")|| event.getPlayer().isOp()){
				getItem("admin-item").givePlayerItem(event.getPlayer());
			}
		}
	}
	
	private void registerItem(String key, Material defMat, String name, int slot, boolean onlyInHub, boolean onlyInGame, boolean ifAdmin, SingleExecutor exe) {
		Material itemMat = Material.valueOf((itemsConfig.getString(key) == null) ? saveDefaults(key, defMat) : itemsConfig.getString(key));
		ItemStack itemStack = new ItemStack(itemMat);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(name);
		itemStack.setItemMeta(itemMeta);

		SGItem item = new SGItem(itemStack, slot, onlyInHub, onlyInGame, ifAdmin, exe);
		items.put(key, item);
	}

	private void registerItem(String key, Material defMat, String name, int slot, boolean onlyInHub, boolean onlyInGame, boolean ifAdmin, MultiExecutor exe) {
		Material itemMat = Material.valueOf((itemsConfig.getString(key) == null) ? saveDefaults(key, defMat) : itemsConfig.getString(key));
		ItemStack itemStack = new ItemStack(itemMat);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(name);
		itemStack.setItemMeta(itemMeta);

		SGItem item = new SGItem(itemStack, slot, onlyInHub, onlyInGame, ifAdmin, exe);
		items.put(key, item);
	}
	
	private String saveDefaults(String key, Material m) {
		itemsConfig.set(key, m.toString());
		try {
			itemsConfig.save(new File(SGApi.getPlugin().getDataFolder(), "items.yml"));
		} catch (IOException e) {}
		return m.toString();
	}
}
