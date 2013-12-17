/**
 * Name: MultiworldMain.java Created: 13 December 2013
 *
 * @version 1.0.0
 */
package me.theepicbutterstudios.thesurvivalgames.managers;

import me.theepicbutterstudios.thesurvivalgames.multiworld.SGWorld;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.*;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class MultiworldManager {

    static final MultiworldManager wm = new MultiworldManager();

    public MultiworldManager() {
    }

    public static MultiworldManager getInstance() {
        return wm;
    }

    public World createWorld(String name) {
        return new SGWorld(name).create();
    }

    public void deleteWorld(String name) {
        new SGWorld(name).remove();
    }

    public World copyFromInternet(String url) throws IOException {
        URL source = new URL(url);
        if (url == null) {
            return null;
        }

        File target = null;
        int i = 0;
        while (true) {
            String name = "SurvivalGamesWorld" + i;
            if (Bukkit.getServer().getWorld(name) == null) {
                target = createWorld(name).getWorldFolder();
                break;
            }
        }

        InputStream in = source.openConnection().getInputStream();

        unTar(in, target);
        in.close();

        return Bukkit.getServer().getWorld("SurvivalGamesWorld" + i);
    }

    private void unTar(InputStream stream, File target) throws IOException {
        GZIPInputStream in = new GZIPInputStream(stream);
        OutputStream out = new FileOutputStream(target);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }

        in.close();
        out.close();
    }
}
