package com.communitysurvivalgames.thesurvivalgames.listeners;

import src.main.java.com.communitysurvivalgames.thesurvivalgames.TheSurvivalGames;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.util.EconUtil;

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
