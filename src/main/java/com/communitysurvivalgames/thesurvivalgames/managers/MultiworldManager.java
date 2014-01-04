/**
 * Name: MultiworldMain.java Created: 13 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.managers;

import com.communitysurvivalgames.thesurvivalgames.multiworld.SGWorld;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.*;
import java.net.URL;
import java.util.zip.GZIPInputStream;

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

	/**
	 * Copies bytes from the URL <code>source</code> to a file
	 * <code>destination</code>. The directories up to <code>destination</code>
	 * will be created if they don't already exist. <code>destination</code>
	 * will be overwritten if it already exists.
	 * <p>
	 * Warning: this method does not set a connection or read timeout and thus
	 * might block forever. Use {@link #copyURLToFile(URL, File, int, int)}
	 * with reasonable timeouts to prevent this.
	 *
	 * @param source  the <code>URL</code> to copy bytes from, must not be {@code null}
	 * @param destination  the non-directory <code>File</code> to write bytes to
	 *  (possibly overwriting), must not be {@code null}
	 * @throws IOException if <code>source</code> URL cannot be opened
	 * @throws IOException if <code>destination</code> is a directory
	 * @throws IOException if <code>destination</code> cannot be written
	 * @throws IOException if <code>destination</code> needs creating but can't be
	 * @throws IOException if an IO error occurs during copying
	 */
	public static void copyURLToFile(URL source, File destination) throws IOException {
		InputStream input = source.openStream();
		copyInputStreamToFile(input, destination);
	}

	/**
	 * Copies bytes from an {@link InputStream} <code>source</code> to a file
	 * <code>destination</code>. The directories up to <code>destination</code>
	 * will be created if they don't already exist. <code>destination</code>
	 * will be overwritten if it already exists.
	 *
	 * @param source  the <code>InputStream</code> to copy bytes from, must not be {@code null}
	 * @param destination  the non-directory <code>File</code> to write bytes to
	 *  (possibly overwriting), must not be {@code null}
	 * @throws IOException if <code>destination</code> is a directory
	 * @throws IOException if <code>destination</code> cannot be written
	 * @throws IOException if <code>destination</code> needs creating but can't be
	 * @throws IOException if an IO error occurs during copying
	 */
	public static void copyInputStreamToFile(InputStream source, File destination) throws IOException {
		try {
			FileOutputStream output = openOutputStream(destination);
			try {
				IOUtils.copy(source, output);
				output.close(); // don't swallow close Exception if copy completes normally
			} finally {
				IOUtils.closeQuietly(output);
			}
		} finally {
			IOUtils.closeQuietly(source);
		}
	}

	public World copyFromInternet(String url) throws IOException {
		URL source = new URL(url);
		if (url == null) {
			return null;
		}

		File target;
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
