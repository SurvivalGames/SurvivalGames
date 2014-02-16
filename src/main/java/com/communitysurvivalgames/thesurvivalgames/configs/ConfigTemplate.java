package com.communitysurvivalgames.thesurvivalgames.configs;

import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;


public abstract class ConfigTemplate<T> {
    private File file = null;
    private FileConfiguration config = null;

    public ConfigTemplate(String path) {
        try {
            this.file = new File(SGApi.getPlugin().getDataFolder().getAbsolutePath() + path);
            this.file.createNewFile();

            this.config = YamlConfiguration.loadConfiguration(file);
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public ConfigTemplate(File file) {
        this.file = file;
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public final void serialize() {
        for(int i = 0; i <= pattern().length - 1; i++) {
            config.set(pattern()[i], toFile(i));
        }

        try {
            config.save(file);
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public final T deserialize() {
        T t = null;
        for(int i = 0; i <= pattern().length - 1; i++) {
            t = fromFile(i, config.get(pattern()[i]));
        }

        return t;
    }

    public abstract Object toFile(int index);
    public abstract T fromFile(int index, Object o);
    public abstract String[] pattern();
}
