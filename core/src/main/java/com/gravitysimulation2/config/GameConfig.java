package com.gravitysimulation2.config;

import java.io.InputStream;
import java.util.Properties;

public class GameConfig implements Config<GameConfig> {
    // start properties
    public static String savesDir = "";
    // game
    public static boolean isRelease = true;
    public static String version = "0.0.0";
    public static String releaseDate;
    // window
    public static String windowTitle = "untitled";
    public static boolean isResizable = false;

    // show settings
    public boolean showFps = true;
    public boolean showVersion = true;
    public boolean showVVectors = true;
    public boolean showPositions = true;

    static {
        loadFromProperties();
    }

    private static void loadFromProperties() {
        try (InputStream input = GameConfig.class.getClassLoader().getResourceAsStream("game.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            savesDir = prop.getProperty("savesDir", savesDir);

            isRelease = Boolean.parseBoolean(
                prop.getProperty("game.isRelease", String.valueOf(isRelease))
            );
            version = prop.getProperty("game.version", version);
            releaseDate = prop.getProperty("game.releaseDate", "");

            windowTitle = prop.getProperty("window.Title", windowTitle);
            isResizable = Boolean.parseBoolean(
                prop.getProperty("window.isResizable", String.valueOf(isResizable))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getConfigPath() {
        return configDir + "/game.json";
    }

    @Override
    public GameConfig getDefaultConfig() {
        return new GameConfig();
    }
}
