package com.gravitysimulation2.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    private static final Map<String, Config<?>> configs = new HashMap<>();

    // get loaded config
    public static Config<?> getLoaded(String name) {
        return configs.get(name);
    }

    // unload loaded config
    public static Config<?> unload(String name) {
        return configs.remove(name);
    }

    // loading config with name into class
    public static <T extends Config<T>> T load(Class<T> configClass, String name) {
        T resultConfig;

        try {
            T instance = configClass.getDeclaredConstructor().newInstance();
            FileHandle configFile = Gdx.files.local(instance.getConfigPath());

            if (configFile.exists()) {
                resultConfig = new Json().fromJson(configClass, configFile);
                configs.put(name, resultConfig);
            } else {
                resultConfig = instance.getDefaultConfig();
                configs.put(name, resultConfig);
                save(name);
            }
        } catch (Exception e) {
            Gdx.app.error("ConfigManager", "Failed to load config. Using defaults.", e);
            try {
                resultConfig = configClass.getDeclaredConstructor().newInstance().getDefaultConfig();
                configs.put(name, resultConfig);
            } catch (Exception ex) {
                throw new RuntimeException("Failed to create default config", ex);
            }
        }

        return resultConfig;
    }

    // save loaded config
    public static <T extends Config<T>> void save(String name) {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        FileHandle file = Gdx.files.local(getLoaded(name).getConfigPath());
        file.writeString(json.prettyPrint(getLoaded(name)), false);
    }
}
