/**
 * Name: MultiworldMain.java Created: 13 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.communitysurvivalgames.thesurvivalgames.TheSurvivalGames;
import com.communitysurvivalgames.thesurvivalgames.multiworld.SGWorld;
import com.communitysurvivalgames.thesurvivalgames.util.UnTAR;

public class MultiWorld {

    private static final MultiWorld wm = new MultiWorld();

    private MultiWorld() {
    }

    public static MultiWorld getInstance() {
        return wm;
    }

    World createWorld(String name) {
        return new SGWorld(name).create();
    }

    public void deleteWorld(String name) {
        new SGWorld(name).remove();
    }

    public World copyFromInternet(final Player sender, final String worldName) {// TODO:
                                                                                // Translate

        String url = "http://communitysurvivalgames.com/worlds/" + worldName + ".zip";
        /*if (!FileUtil.exists(url)) {
            sender.sendMessage("That arena dosen't seem to be in our database!  :(");
            sender.sendMessage("Look for worlds we do have at: http://communitysurvivalgames.com/worlds/");
            return null;
        }*/
        try {
            FileUtils.copyURLToFile(new URL(url), new File(TheSurvivalGames.getPlugin(TheSurvivalGames.class).getDataFolder().getAbsolutePath(), "SG_ARENA_TMP.tar"));
        } catch (MalformedURLException e) {
            sender.sendMessage("Bad world name! Are you using special characters?");
            return null;
        } catch (IOException e) {
            sender.sendMessage("World downloading failed, try again later or something");
            return null;
        }
        try {
			UnTAR.unTar(new File(TheSurvivalGames.getPlugin(TheSurvivalGames.class).getDataFolder(), "SG_ARENA_TMP.tar"),
			        new File(Bukkit.getServer().getWorldContainer().getAbsolutePath(), worldName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ArchiveException e) {
			e.printStackTrace();
		}
        if (!checkIfIsWorld(new File(Bukkit.getServer().getWorldContainer().getAbsolutePath(), worldName))) {
            sender.sendMessage("The downloaded world was not a world at all!");
            return null;
        }
        createWorld(worldName);

        return Bukkit.getWorld(worldName);
    }

    public World importWorldFromFolder(final Player sender, final String worldName) {

        if (!checkIfIsWorld(new File(Bukkit.getServer().getWorldContainer().getAbsolutePath(), worldName))) {
            sender.sendMessage("That's not a world :/");
            return null;
        }
        createWorld(worldName);

        return Bukkit.getWorld(worldName);
    }

    public World createRandomWorld(final String worldName) {
        // TODO
        return Bukkit.getWorld(worldName);
    }

    private static boolean checkIfIsWorld(File worldFolder) {
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
