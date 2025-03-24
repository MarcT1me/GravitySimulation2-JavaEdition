package com.gravitysimulation2.save;

import com.gravitysimulation2.config.Config;
import com.gravitysimulation2.config.GameConfig;

public class CameraConfig implements Config<CameraConfig> {
    private String saveName = "default";

    public float posX = 0f;
    public float posY = 0f;
    public float zoom = 1.0f;

    public CameraConfig() {
    }

    public CameraConfig(String saveName) {
        this.saveName = saveName;
    }

    @Override
    public String getConfigPath() {
        return GameConfig.savesDir + "/" + saveName + "/camera.json";
    }

    @Override
    public CameraConfig getDefaultConfig() {
        return new CameraConfig();
    }
}
