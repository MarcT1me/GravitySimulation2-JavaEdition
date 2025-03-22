package com.gravitysimulation2.save;

import com.gravitysimulation2.config.Config;
import com.gravitysimulation2.config.GameConfig;
import com.gravitysimulation2.objects.scene.GameScene;

public class CameraConfig implements Config<CameraConfig> {
    public float posX = 0f;
    public float posY = 0f;
    public float zoom = 1.0f;

    @Override
    public String getConfigPath() {
        return GameConfig.savesDir + "/" + GameScene.getCurrent().name + "/camera.json";
    }

    @Override
    public CameraConfig getDefaultConfig() {
        return new CameraConfig();
    }
}
