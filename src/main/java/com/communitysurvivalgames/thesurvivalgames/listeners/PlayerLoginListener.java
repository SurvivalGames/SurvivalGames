package com.communitysurvivalgames.thesurvivalgames.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.communitysurvivalgames.thesurvivalgames.managers.MeunManager;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.objects.PlayerData;
import com.communitysurvivalgames.thesurvivalgames.util.EconUtil;

public class PlayerLoginListener implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(final PlayerJoinEvent event) {
		Bukkit.getScheduler().runTaskLater(SGApi.getPlugin(), new Runnable() {

			@Override
			public void run() {
				event.setJoinMessage(null);
				if (event.getPlayer().getWorld().equals(Bukkit.getWorld(SGApi.getPlugin().getPluginConfig().getHubWorld()))) {
					event.getPlayer().getInventory().clear();
					ItemStack compass = new ItemStack(Material.COMPASS);
					ItemMeta compassmeta = compass.getItemMeta();
					compassmeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Click to join a SG game");
					compass.setItemMeta(compassmeta);
					ItemStack clock = new ItemStack(Material.WATCH);
					ItemMeta clockmeta = compass.getItemMeta();
					clockmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Click to connect to the soundserver");
					clock.setItemMeta(clockmeta);
					event.getPlayer().getInventory().setItem(8, clock);
					event.getPlayer().getInventory().setItem(0, compass);
				}
				for (String s : SGApi.getPlugin().getPluginConfig().getWelcomeMessage()) {
					event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', s));
				}
				if (!event.getPlayer().hasPlayedBefore()) {
					EconUtil.addPoints(event.getPlayer(), SGApi.getPlugin().getPluginConfig().getDefaultPoints());
				}
				if (SGApi.getPlugin().useChat()) {
					PlayerData data = SGApi.getPlugin().getPlayerData(event.getPlayer());
					if (SGApi.getPlugin().getPrefix(event.getPlayer()) != null && !SGApi.getPlugin().getPrefix(event.getPlayer()).isEmpty()) {
						data.setRank(SGApi.getPlugin().getPrefix(event.getPlayer()));
						SGApi.getPlugin().setPlayerData(data);
					}
				}
		        SGApi.getPlugin().getTracker().trackEvent("Player Login", event.getPlayer().getName());
			}
			
		}, 10L);

	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onInteract(final PlayerInteractEvent event) {
		if (event.getPlayer().getWorld().equals(Bukkit.getWorld(SGApi.getPlugin().getPluginConfig().getHubWorld()))) {
			if (!SGApi.getArenaManager().isInGame(event.getPlayer())) {
				if (event.getItem() != null) {
					if (event.getItem().getType().equals(Material.WATCH)) {
						Player p = event.getPlayer();
						p.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮");
						p.sendMessage(ChatColor.AQUA + "");
						p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Want to here LIVE music, announcers, and sound effects?");
						p.sendMessage(ChatColor.AQUA + "");
						p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Click this link:");
						p.sendMessage(ChatColor.WHITE + "" + ChatColor.UNDERLINE + "http://communitysurvivalgames.com/sg/index.html?name=" + p.getName() + "&session=" + SGApi.getPlugin().getPluginConfig().getServerIP());
						p.sendMessage(ChatColor.AQUA + "");
						p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Simply leave your browser window open in the background, turn up your speakers, and we'll do the rest!");
						p.sendMessage(ChatColor.AQUA + "");
						p.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮");
						return;
					}
					if (event.getItem().getType().equals(Material.COMPASS)) {
						MeunManager.getMenuManager().displayJoinMenu(event.getPlayer());
						return;
					}
				}
			}
		}
		if (event.getItem() != null) {
			if (event.getItem().getType().equals(Material.EMERALD)) {
				if (SGApi.getArenaManager().isInGame(event.getPlayer())) {
					MeunManager.getMenuManager().displayVoteMenu(event.getPlayer());
				}
				return;
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onWorldChange(final PlayerChangedWorldEvent event) {
		if (event.getPlayer().getWorld().equals(Bukkit.getWorld(SGApi.getPlugin().getPluginConfig().getHubWorld()))) {
			event.getPlayer().getInventory().clear();
			ItemStack compass = new ItemStack(Material.COMPASS);
			ItemMeta compassmeta = compass.getItemMeta();
			compassmeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Click to join a SG game");
			compass.setItemMeta(compassmeta);
			ItemStack clock = new ItemStack(Material.WATCH);
			ItemMeta clockmeta = compass.getItemMeta();
			clockmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Click to connect to the soundserver");
			clock.setItemMeta(clockmeta);
			event.getPlayer().getInventory().setItem(8, clock);
			event.getPlayer().getInventory().setItem(0, compass);
		}
	}

}
