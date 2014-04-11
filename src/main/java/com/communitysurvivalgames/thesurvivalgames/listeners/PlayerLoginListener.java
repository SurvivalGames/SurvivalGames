package com.communitysurvivalgames.thesurvivalgames.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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

}
