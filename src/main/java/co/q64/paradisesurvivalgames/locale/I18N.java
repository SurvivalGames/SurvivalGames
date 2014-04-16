package co.q64.paradisesurvivalgames.locale;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;

import org.bukkit.Bukkit;

import co.q64.paradisesurvivalgames.managers.SGApi;

public class I18N {
	public enum _Locale {
		enUS, idID, nwNO
	}
	private static _Locale currentLocale = _Locale.enUS; //
	private static final File dir = new File(SGApi.getPlugin().getDataFolder(), "locale");
	private static final Properties fallback = new Properties();
	private static final HashMap<String, String> localeFiles = new HashMap<>();
	private static final HashMap<Integer, String> localeIndices = new HashMap<>();

	private static final Properties locales = new Properties();

	/**
	 * Add files from the locale directory
	 */
	private static void addFiles() {
		int i = 1;
		Properties tmp = new Properties();
		String[] list = dir.list();
		for (String file : list) {
			if (file.endsWith(".lang")) {
				try {
					if (!file.equalsIgnoreCase("enUS.lang")) {
						tmp.clear();
						tmp.load(new InputStreamReader(new FileInputStream(dir.getAbsolutePath() + File.separator + file), "UTF8"));
						localeFiles.put(file, tmp.getProperty("LOCALE_NAME", file));
						localeIndices.put(i, file);
						i++;
					}
				} catch (IOException e) {
					Bukkit.getLogger().log(Level.SEVERE, "[i18n] Could not load language file", e);
				}
			}
		}
		try {
			fallback.clear();
			tmp.load(new InputStreamReader(new FileInputStream(dir.getAbsolutePath() + File.separator + "enUS.lang"), "UTF8"));
			Bukkit.getLogger().log(Level.SEVERE, "[i18n] Fallback enUS loaded");
		} catch (IOException e) {
			Bukkit.getLogger().log(Level.SEVERE, "[i18n] Could not load fallback file", e);
		}
	}

	/**
	 * Gets the default translation for the key (enUS)
	 *
	 * @param key The key for the string
	 * @return the default string
	 */
	private static String getFallbackString(String key) {
		return fallback.getProperty(key, key);
	}

	/**
	 * Gets the locale properties and stores loads it to locales
	 *
	 * @param file The locale file
	 */
	private static void getLocaleProperties(String file) {
		locales.clear();
		if (file.equalsIgnoreCase("enUS")) {
			try {
				locales.load(new InputStreamReader(new FileInputStream(dir.getAbsolutePath() + File.separator + "enUS" + ".lang"), "UTF8"));
			} catch (IOException e) {
				Bukkit.getLogger().log(Level.SEVERE, "[i18n] Could not load language file", e);
			}
		} else {
			try {
				locales.load(new InputStreamReader(new FileInputStream(dir.getAbsolutePath() + File.separator + file + ".lang"), "UTF8"));
			} catch (IOException e) {
				Bukkit.getLogger().log(Level.SEVERE, "[i18n] Could not load language file", e);
			}
		}
	}

	/**
	 * Gets the localized string for the field, if not defined, returns the key
	 *
	 * @param key The key for the string
	 * @return The localized string or fallback value
	 */
	public static String getLocaleString(String key) {
		return locales.getProperty(key, getFallbackString(key));
	}

	/**
	 * Sets the locale for the files
	 *
	 * @param locale the language file to be loaded
	 */
	public static void setLocale(String locale) {
		if (locale == null) {
			locale = "enUS";
			currentLocale = _Locale.enUS;

		} else {
			try {
				currentLocale = _Locale.valueOf(locale);
			} catch (IllegalArgumentException e) {
				Bukkit.getLogger().severe("[i18n] Unknown locale " + locale + ". Loaded enUs");
				currentLocale = _Locale.enUS;
			}
		}
		getLocaleProperties(locale);
		Bukkit.getLogger().info("[i18n] " + locale + " " + locales.getProperty("LOCALE_LOADED", "loaded"));
	}

	/**
	 * Set available locales and load fallback locale
	 */
	public static void setupLocale() {
		localeFiles.put("enUS", "English");
		localeIndices.put(0, "enUS");
		addFiles();
	}
}
