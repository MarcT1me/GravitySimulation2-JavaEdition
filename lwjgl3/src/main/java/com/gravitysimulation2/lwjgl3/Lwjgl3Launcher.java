package com.gravitysimulation2.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import com.gravitysimulation2.GravitySimulation2;
import com.gravitysimulation2.config.ConfigManager;
import com.gravitysimulation2.config.GameConfig;
import com.gravitysimulation2.config.WindowConfig;

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        new Lwjgl3Application(
            new GravitySimulation2() {
                @Override
                public void create() {
                    super.create();
                    ((WindowConfig) ConfigManager.getLoaded("window config")).apply();
                    ((Lwjgl3Graphics) Gdx.graphics).getWindow().setVisible(true);
                }
            },
            getDefaultConfiguration()
        );
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration lwjglConfig = new Lwjgl3ApplicationConfiguration();

        lwjglConfig.setInitialVisible(false);

        lwjglConfig.setTitle(GameConfig.windowTitle + "  " + GameConfig.version);
        // config.setWindowIcon("gs128.png", "gs64.png", "gs32.png", "gs16.png");
        lwjglConfig.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");

        lwjglConfig.setResizable(GameConfig.isResizable);
        lwjglConfig.setAutoIconify(true);

        return lwjglConfig;
    }
}
