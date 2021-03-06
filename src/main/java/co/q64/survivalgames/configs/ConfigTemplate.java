package co.q64.survivalgames.configs;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import co.q64.survivalgames.managers.SGApi;

public abstract class ConfigTemplate<T> {
	private FileConfiguration config = null;
	private File file = null;

	public ConfigTemplate(File file) {
		this.file = file;
		this.config = YamlConfiguration.loadConfiguration(file);
		try {
			config.save(file);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	public ConfigTemplate(String path) {

		this.file = new File(SGApi.getPlugin().getDataFolder().getAbsolutePath() + File.separator + path);

		if (!this.file.exists()) {
			try {
				this.file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		this.config = YamlConfiguration.loadConfiguration(file);
		try {
			config.save(file);
		} catch (IOException x) {
			x.printStackTrace();
		}
	}

	public final T deserialize() {
		T t = null;
		for (int i = 0; i <= pattern().length - 1; i++) {
			t = fromFile(i, config.get(pattern()[i]));
		}

		return t;
	}

	public void fixErrorsTheQuantum64Way() {
		serialize();
	}

	public abstract T fromFile(int index, Object o);

	public abstract String[] pattern();

	public final void serialize() {
		for (int i = 0; i <= pattern().length - 1; i++) {
			config.set(pattern()[i], toFile(i));
		}

		try {
			config.save(file);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	public abstract Object toFile(int index);
}
