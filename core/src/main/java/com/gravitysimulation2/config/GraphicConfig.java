package com.gravitysimulation2.config;

public class GraphicConfig implements Config<GraphicConfig> {
    public int trajectoryLen = 200;
    public float trajectoryInterval = 0.01f;

    public boolean showVVectors = true;
    public boolean showPositions = true;

    @Override
    public String getConfigPath() {
        return "config/graphic.json";
    }

    @Override
    public GraphicConfig getDefaultConfig() {
        return new GraphicConfig();
    }
}
