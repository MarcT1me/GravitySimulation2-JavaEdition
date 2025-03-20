package com.gravitysimulation2.config;

public class GameConfig implements Config<GameConfig> {
    @Override
    public String getConfigPath() {
        return "config/game.json";
    }

    @Override
    public GameConfig getDefaultConfig() {
        return new GameConfig();
    }
}
