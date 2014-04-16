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

import co.q64.paradisesurvivalgames.util.player.items.SGItem;
import co.q64.paradisesurvivalgames.util.player.items.ce.MultiExecutor;
import co.q64.paradisesurvivalgames.util.player.items.ce.SingleExecutor;

public class ItemManager implements Listener {

	private static ItemManager instance;
	private FileConfiguration itemsConfig;
	private File cfgFile;
	private Map<String, SGItem> items = new HashMap<String, SGItem>();

	public ItemManager() {

		cfgFile = new File(SGApi.getPlugin().getDataFolder(), "items.yml");
		cfgFile.mkdirs();
		try {
			cfgFile.createNewFile();
		} catch (IOException e) {
			Bukkit.getLogger().severe("Faild to save items.yml file");
			return;
		}

		itemsConfig = YamlConfiguration.loadConfiguration(cfgFile);
		FileConfigurationOptions itemConfigOptions = itemsConfig.options();
		itemConfigOptions.header("You can change the default material binds here\r\nUSE VALID BUKKIT ITEM NAMES ONLY");
		itemConfigOptions.copyHeader(true);

		registerItem("vote-item", Material.EMERALD, ChatColor.GREEN.toString() + ChatColor.BOLD + "Click to vote for a map", 4, true, true, new SingleExecutor() {

			@Override
			public void use(Player player) {
				MenuManager.getMenuManager().displayVoteMenu(player);
			}
		});

		registerItem("join-item", Material.COMPASS, ChatColor.GREEN.toString() + ChatColor.BOLD + "Click to join a SG game", 0, true, false, new SingleExecutor() {

			@Override
			public void use(Player player) {
				MenuManager.getMenuManager().displayJoinMenu(player);
			}
		});

		registerItem("connect-item", Material.WATCH, ChatColor.YELLOW.toString() + ChatColor.BOLD + "Click to connect to the soundserver", 8, true, false, new SingleExecutor() {

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

		registerItem("spec-item", Material.NETHER_STAR, ChatColor.AQUA.toString() + ChatColor.BOLD + "Click to spectate a player", 0, false, true, new SingleExecutor() {

			@Override
			public void use(Player player) {
				MenuManager.getMenuManager().displaySpecMenu(player);
			}
		});
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
				getItem("connect-item").givePlayerItem(event.getPlayer());
			getItem("join-item").givePlayerItem(event.getPlayer());
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
						getItem("connect-item").givePlayerItem(event.getPlayer());
					getItem("join-item").givePlayerItem(event.getPlayer());
				}
			}

		}, 10L);

	}

	public SGItem getItem(String key) {
		return items.get(key);
	}

	private void registerItem(String key, Material defMat, String name, int slot, boolean onlyInHub, boolean onlyInGame, SingleExecutor exe) {
		Material itemMat = Material.valueOf((itemsConfig.getString("key") == null) ? saveDefaults(key, defMat) : itemsConfig.getString("key"));
		ItemStack itemStack = new ItemStack(itemMat);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(name);
		itemStack.setItemMeta(itemMeta);

		SGItem item = new SGItem(itemStack, slot, onlyInHub, onlyInGame, exe);
		items.put(key, item);
	}

	private void registerItem(String key, Material defMat, String name, int slot, boolean onlyInHub, boolean onlyInGame, MultiExecutor exe) {
		Material itemMat = Material.valueOf((itemsConfig.getString("key") == null) ? saveDefaults(key, defMat) : itemsConfig.getString("key"));
		ItemStack itemStack = new ItemStack(itemMat);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(name);
		itemStack.setItemMeta(itemMeta);

		SGItem item = new SGItem(itemStack, slot, onlyInHub, onlyInGame, exe);
		items.put(key, item);
	}

	private String saveDefaults(String key, Material m) {
		itemsConfig.set(key, m.toString());
		try {
			itemsConfig.save(cfgFile);
		} catch (IOException e) {}
		return m.toString();
	}
}
