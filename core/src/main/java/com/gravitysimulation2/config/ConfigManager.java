package com.gravitysimulation2.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    static {
        Json json = new Json();
        json.setSerializer(Vector2.class, new Json.Serializer<>() {
            @Override
            public void write(Json json, Vector2 vector, Class knownType) {
                json.writeArrayStart();
                json.writeValue(vector.x);
                json.writeValue(vector.y);
                json.writeArrayEnd();
            }

            @Override
            public Vector2 read(Json json, JsonValue jsonData, Class type) {
                return new Vector2(
                    jsonData.get(0).asFloat(),
                    jsonData.get(1).asFloat()
                );
            }
        });
    }

    private static final Map<String, Config<?>> configs = new HashMap<>();

    // get loaded config
    public static Config<?> getConfig(String name) {
        return configs.get(name);
    }

    // add config in loaded set
    public static void addConfig(String name, Config<?> config) {
        configs.put(name, config);
    }

    // unload loaded config
    public static void unloadConfig(String name) {
        configs.remove(name);
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
        FileHandle file = Gdx.files.local(getConfig(name).getConfigPath());
        file.writeString(json.prettyPrint(getConfig(name)), false);
    }
}
