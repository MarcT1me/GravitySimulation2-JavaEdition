package com.gravitysimulation2.config;

public class GraphicConfig implements Config<GraphicConfig> {
    public int trajectoryLen = 750;
    public float trajectoryInterval = 0.001f;
    public float vVectorsScale = 1f;
    public float dirLineAlpha = 0.5f;

    public boolean showTrajectory = true;
    public boolean showDirectionLine = true;
    public boolean showVVectors = true;
    public boolean showVMods = true;
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
