package com.communitysurvivalgames.thesurvivalgames.listeners;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.communitysurvivalgames.thesurvivalgames.managers.JoinMeunManager;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;

public class SignListener implements Listener {
	@EventHandler(priority = EventPriority.HIGH)
	public void onSignChange(SignChangeEvent event) {
		if (event.getLines()[0].equals("[SGJoin]")) {
			event.setLine(0, ChatColor.BLUE + "[SGJoin]");
			return;
		}

		if (event.getLines()[0].equals("[SGKit]")) {
			event.setLine(0, ChatColor.BLUE + "[SGKit]");
			return;
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	private void onClick(PlayerInteractEvent event) {
		if (event.getClickedBlock().getState() instanceof Sign) {
			Sign sign = (Sign) event.getClickedBlock().getState();
			if (sign.getLines()[0].equals(ChatColor.BLUE + "[SGJoin]")) {
				JoinMeunManager.getMenuManager().displayMenu(event.getPlayer());
				return;
			}
			if (sign.getLines()[0].equals(ChatColor.BLUE + "[SGKit]")) {
				SGApi.getKitManager().displayDefaultKitSelectionMenu(event.getPlayer());
				return;
			}
		}
	}
}
