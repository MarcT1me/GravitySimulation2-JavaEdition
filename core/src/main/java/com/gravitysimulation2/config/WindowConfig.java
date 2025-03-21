package com.gravitysimulation2.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;

public class WindowConfig implements Config<WindowConfig> {
    // window mode
    public boolean fullscreen = true;
    public int windowWidth = 1600;
    public int windowHeight = 900;

    // fps
    public boolean vsync = true;
    public int targetFPS = 60;

    // monitor
    public int selectedMonitor = -1;
    private final Graphics.Monitor[] monitors = Gdx.graphics.getMonitors();

    @Override
    public String getConfigPath() {
        return configDir + "/window.json";
    }

    @Override
    public WindowConfig getDefaultConfig() {
        return new WindowConfig();
    }

    public void apply() {
        // window mode
        if (fullscreen) {
            Gdx.graphics.setFullscreenMode(
                Gdx.graphics.getDisplayMode(  // get display
                    selectedMonitor == -1 ?
                        Gdx.graphics.getMonitor() :  // if not chosen
                        monitors[Math.min(selectedMonitor, monitors.length - 1)]
                )
            );
        } else {
            Gdx.graphics.setWindowedMode(windowWidth, windowHeight);
        }

        // fps
        Gdx.graphics.setVSync(vsync);
        Gdx.graphics.setForegroundFPS(targetFPS);
    }
}
