package com.gravitysimulation2.config;

import com.badlogic.gdx.Gdx;

public class WindowConfig implements Config<WindowConfig> {
    public boolean fullscreen = true;
    public int windowWidth = 1600;
    public int windowHeight = 900;
    public boolean vsync = true;
    public int targetFPS = 60;

    @Override
    public String getConfigPath() {
        return "config/window.json";
    }

    @Override
    public WindowConfig getDefaultConfig() {
        return new WindowConfig();
    }

    public void apply() {
        Gdx.graphics.setVSync(vsync);
        Gdx.graphics.setForegroundFPS(targetFPS);
        if (fullscreen) {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        } else {
            Gdx.graphics.setWindowedMode(windowWidth, windowHeight);
        }
    }
}
