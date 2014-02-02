package com.communitysurvivalgames.thesurvivalgames.configs;

import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


public abstract class ConfigTemplate<T> {
    private String[] pattern;

    private File file = null;
    private FileConfiguration config = null;

    public ConfigTemplate(String pattern, String path) {
        this.pattern = pattern;
        try {
            file = new File(SGApi.getPlugin().getDataFolder().getAbsolutePath() + path);
            file.createNewFile();

            config = YamlConfiguration.loadConfiguration(file);
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public void serialize() {
        for(int i = 0; i <= pattern.length - 1; i++) {
            config.set(pattern[i], toFile(i));
        }

        try {
            config.save(file);
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public T deserialize() {
        T t;
        for(int i = 0; i <= pattern.length - 1; i++) {
            t = fromFile(i, config.get(pattern[i]);
        }

        return t;
    }

    public abstract Object toFile(int index);
    public abstract T fromFile(int index, Object o);
}
