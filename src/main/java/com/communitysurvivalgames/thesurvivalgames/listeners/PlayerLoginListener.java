package com.communitysurvivalgames.thesurvivalgames.listeners;

import com.communitysurvivalgames.thesurvivalgames.TheSurvivalGames;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.util.EconUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

class PlayerLoginListener implements Listener {
	private final TheSurvivalGames plugin;

	public PlayerLoginListener(TheSurvivalGames plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event) {
		Bukkit.getScheduler().runTaskLater(SGApi.getPlugin(), new Runnable() {

			@Override
			public void run() {
				event.setJoinMessage(null);
				event.getPlayer().getInventory().clear();
				event.getPlayer().getInventory().setItem(8, new ItemStack(Material.WATCH));
				for (String s : plugin.getPluginConfig().getWelcomeMessage()) {
					event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', s));
				}
				if (!event.getPlayer().hasPlayedBefore()) {
					EconUtil.addPoints(event.getPlayer(), SGApi.getPlugin().getPluginConfig().getDefaultPoints());
				}
			}
		}, 10L);

	}
}
