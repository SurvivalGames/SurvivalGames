package com.communitysurvivalgames.thesurvivalgames.managers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.communitysurvivalgames.thesurvivalgames.exception.ArenaNotFoundException;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;

public class SignManager {

	private Map<Sign, String> signs = new HashMap<Sign, String>();
	private FileConfiguration config;
	
	public SignManager() {

		SGApi.getPlugin().saveResource("signs.yml", false);

		config = YamlConfiguration.loadConfiguration(new File(SGApi.getPlugin().getDataFolder(), "signs.yml"));
		ConfigurationSection signConfig = config.getConfigurationSection("signs");
		for (String s : signConfig.getKeys(false)) {
			ConfigurationSection currentSign = signConfig.getConfigurationSection(s);
			Location loc = SGApi.getArenaManager().deserializeLoc(currentSign.getString("loc"));
			Block block = loc.getWorld().getBlockAt(loc);
			if (block.getState() != null && block.getState() instanceof Sign) {
				Sign sign = (Sign) block.getState();
				signs.put(sign, s);
			}
		}

		Bukkit.getScheduler().scheduleSyncRepeatingTask(SGApi.getPlugin(), new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < signs.size(); i++) {
					Sign sign = (Sign) signs.keySet().toArray()[i];
					SGArena arena = null;

					// LINE 1
					try {
						arena = SGApi.getArenaManager().getArena(Integer.parseInt(signs.get(sign)));
					} catch (NumberFormatException e) {
						return;
					} catch (ArenaNotFoundException e) {
						return;
					}
					if (arena.getState().equals(SGArena.ArenaState.WAITING_FOR_PLAYERS) || arena.getState().equals(SGArena.ArenaState.PRE_COUNTDOWN)) {
						sign.setLine(0, ChatColor.GREEN + "[Join]");
					} else if (arena.getState().equals(SGArena.ArenaState.IN_GAME) || arena.getState().equals(SGArena.ArenaState.STARTING_COUNTDOWN)) {
						sign.setLine(0, ChatColor.YELLOW + "[Spectate]");
					} else if (arena.getState().equals(SGArena.ArenaState.POST_GAME) || arena.getState().equals(SGArena.ArenaState.DEATHMATCH)) {
						sign.setLine(0, ChatColor.RED + "[NotJoinable]");
					}

					//LINE 2
					sign.setLine(1, "Arena: " + arena.getId());

					//LINE 3
					sign.setLine(2, arena.getPlayers().size() + "/" + arena.getMaxPlayers());

					//LINE 4
					if (arena.getState().equals(SGArena.ArenaState.WAITING_FOR_PLAYERS) || arena.getState().equals(SGArena.ArenaState.PRE_COUNTDOWN)) {
						sign.setLine(3, "Voteing for Map");
					} else {
						sign.setLine(3, arena.getCurrentMap().getDisplayName());
					}
				}
			}
		}, 20L, 20L);
	}

	public void addSign(Sign sign, int arenaId) {
		signs.put(sign, arenaId + "");
		config.set("signs." + arenaId + ".loc", SGApi.getArenaManager().serializeLoc(sign.getLocation()));
		try {
			config.save(new File(SGApi.getPlugin().getDataFolder(), "signs.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Map<Sign, String> getSigns(){
		return signs;
	}
}
