/**
 * Name: MultiworldMain.java Created: 13 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.managers;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.communitysurvivalgames.thesurvivalgames.TheSurvivalGames;
import com.communitysurvivalgames.thesurvivalgames.multiworld.SGWorld;
import com.communitysurvivalgames.thesurvivalgames.util.FileUtil;

public class MultiworldManager {

	private static final MultiworldManager wm = new MultiworldManager();

	private MultiworldManager() {
	}

	public static MultiworldManager getInstance() {
		return wm;
	}

	World createWorld(String name) {
		return new SGWorld(name).create();
	}

	public void deleteWorld(String name) {
		new SGWorld(name).remove();
	}

	public World copyFromInternet(Player sender, String worldName) throws IOException {
		String url = "http://communitysurvivalgames.com/worlds/" + worldName + ".zip";
		if(!FileUtil.exists(url.toString())){
			sender.sendMessage("That arena dosen't seem to be in our database!  :(");
			sender.sendMessage("Look for worlds we do have at: http://communitysurvivalgames.com/worlds/");
			return null;
		}
		FileUtil.copyURLToFile(new URL(url), new File(TheSurvivalGames.getPlugin().getDataFolder().getAbsolutePath(), "SG_ARENA_TMP.zip"));
		FileUtil.unZipIt(new File(TheSurvivalGames.getPlugin().getDataFolder().getAbsolutePath(), "SG_ARENA_TMP.zip").getAbsolutePath(), new File(Bukkit.getServer().getWorldContainer().getAbsolutePath(), worldName).getAbsolutePath());
		return Bukkit.getServer().getWorld(worldName);
	}

	public static boolean checkIfIsWorld(File worldFolder) {
		if (worldFolder.isDirectory()) {
			File[] files = worldFolder.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File file, String name) {
					return name.equalsIgnoreCase("level.dat");
				}
			});
			if (files != null && files.length > 0) {
				return true;
			}
		}
		return false;
	}
}
