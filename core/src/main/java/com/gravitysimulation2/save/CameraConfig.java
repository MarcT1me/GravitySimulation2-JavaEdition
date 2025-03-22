package com.gravitysimulation2.save;

import com.badlogic.gdx.math.Vector2;
import com.gravitysimulation2.config.Config;
import com.gravitysimulation2.config.GameConfig;
import com.gravitysimulation2.objects.scene.GameScene;

public class CameraConfig implements Config<CameraConfig> {
    public Vector2 pos = new Vector2();
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
