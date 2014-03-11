package com.communitysurvivalgames.thesurvivalgames.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;
import com.communitysurvivalgames.thesurvivalgames.util.IconMenu;
import com.communitysurvivalgames.thesurvivalgames.util.IconMenu.OptionClickEvent;

public class JoinMeunManager {
	static JoinMeunManager menuManager;
	private IconMenu menu;

	public static JoinMeunManager getMenuManager() {
		if (menuManager == null)
			menuManager = new JoinMeunManager();
		return menuManager;
	}

	public JoinMeunManager() {
		menu = new IconMenu("Join an arena", 54, new IconMenu.OptionClickEventHandler() {

			@Override
			public void onOptionClick(OptionClickEvent event) {
				if (event.getItem().getType() == Material.EMERALD_BLOCK) {
					try {
						SGApi.getArenaManager().addPlayer(event.getPlayer(), Integer.parseInt(event.getName().charAt(11) + ""));
						event.setWillClose(true);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
			}
		}, SGApi.getPlugin());
		Bukkit.getScheduler().scheduleSyncRepeatingTask(SGApi.getPlugin(), new Runnable() {

			// 11-15 joinable
			// 26-53 non-joinable
			@Override
			public void run() {
				menu.clear();
				List<SGArena> arenas = cloneThoseArenas();
				int index = 0;
				for (SGArena a : arenas) {
					if (index == 5)
						break;
					if (a.getState().equals(SGArena.ArenaState.WAITING_FOR_PLAYERS)) {
						menu.setOption(11 + index, new ItemStack(Material.EMERALD_BLOCK, a.getPlayers().size()), "SG - Arena " + a.getId(), new String[] { ChatColor.BLACK + "", ChatColor.YELLOW + "Players: " + ChatColor.WHITE + a.getPlayers().size(), ChatColor.YELLOW + "Status: " + ChatColor.GREEN + a.getState().toString(), ChatColor.AQUA + "", ChatColor.WHITE + "" + ChatColor.UNDERLINE + "Click to Join!" });
						index++;
					}

				}

				for (SGArena a : arenas) {
					if (index == 27)
						break;
					if (a.getState().equals(SGArena.ArenaState.IN_GAME) || a.getState().equals(SGArena.ArenaState.DEATHMATCH)) {
						menu.setOption(26 + index, new ItemStack(Material.GOLD_BLOCK, a.getPlayers().size()), "SG - Arena " + a.getId(), new String[] { ChatColor.BLACK + "", ChatColor.YELLOW + "Players: " + ChatColor.WHITE + a.getPlayers().size(), ChatColor.YELLOW + "Status: " + ChatColor.GREEN + a.getState().toString(), ChatColor.AQUA + "", ChatColor.WHITE + "" + ChatColor.UNDERLINE + "Click to Spectate!" });
						index++;
					}

				}
			}
		}, 20L, 20L);
	}

	public void displayMenu(Player p) {
		menu.open(p);
	}

	private List<SGArena> cloneThoseArenas() {
		List<SGArena> a = new ArrayList<SGArena>();
		for (SGArena arena : SGApi.getArenaManager().getArenas()) {
			a.add(arena);
		}
		Collections.sort(a, new Comparator<SGArena>() {
			public int compare(SGArena o1, SGArena o2) {
				return Integer.compare(o1.getPlayers().size(), o2.getPlayers().size());
			}
		});
		Collections.reverse(a);
		return a;
	}
}
