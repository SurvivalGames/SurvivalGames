package com.communitysurvivalgames.thesurvivalgames.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import com.communitysurvivalgames.thesurvivalgames.TheSurvivalGames;

public class PlayerLoginListener implements Listener {
	private TheSurvivalGames plugin;

	public PlayerLoginListener(TheSurvivalGames plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.setJoinMessage(null);
		event.getPlayer().getInventory().clear();
		event.getPlayer().getInventory().setItem(8, new ItemStack(Material.WATCH));
		for (String s : plugin.getPluginConfig().getWelcomeMessage()) {
			event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', s));
		}
	}
}
