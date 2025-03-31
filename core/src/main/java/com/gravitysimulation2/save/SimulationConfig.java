package com.gravitysimulation2.save;

import com.gravitysimulation2.config.Config;
import com.gravitysimulation2.config.GameConfig;

public class SimulationConfig implements Config<SimulationConfig> {
    private String saveName;

    public float speed = 1f;
    public int maxSpeedMod = 20;
    public int tps = 300;

    public SimulationConfig() {
    }

    public SimulationConfig(String saveName) {
        this.saveName = saveName;
    }

    @Override
    public String getConfigPath() {
        return GameConfig.savesDir + "/" + saveName + "/simulation.json";
    }

    @Override
    public SimulationConfig getDefaultConfig() {
        return new SimulationConfig();
    }
}
