package com.gravitysimulation2.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

public class ConfigFactory {
    public static <T extends Config<T>> T loadConfig(Class<T> configClass) {
        try {
            T configInstance = configClass.getDeclaredConstructor().newInstance();
            FileHandle configFile = Gdx.files.local(configInstance.getConfigPath());

            if (configFile.exists()) {
                return new Json().fromJson(configClass, configFile);
            } else {
                T defaultConfig = configInstance.getDefaultConfig();
                saveConfig(defaultConfig);
                return defaultConfig;
            }
        } catch (Exception e) {
            Gdx.app.error("ConfigFactory", "Failed to load config. Using defaults.", e);
            try {
                return configClass.getDeclaredConstructor().newInstance().getDefaultConfig();
            } catch (Exception ex) {
                throw new RuntimeException("Critical config error", ex);
            }
        }
    }

    public static <T extends Config<T>> void saveConfig(T config) {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        FileHandle file = Gdx.files.local(config.getConfigPath());
        file.writeString(json.prettyPrint(config), false);
    }
}
