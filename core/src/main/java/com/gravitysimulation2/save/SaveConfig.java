package com.gravitysimulation2.save;

import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.gravitysimulation2.config.Config;
import com.gravitysimulation2.config.ConfigManager;
import com.gravitysimulation2.config.GameConfig;
import com.gravitysimulation2.objects.Camera;
import com.gravitysimulation2.objects.scene.GameScene;

import java.util.Map;

public class SaveConfig implements Config<SaveConfig>, Disposable {
    public String name;
    public float lastLoadTime = 0f;
    public float createTime = 0f;

    public SaveConfig(String name) {
        this.name = name;
    }

    @Override
    public String getConfigPath() {
        return GameConfig.savesDir + "/" + name + "save.json";
    }

    @Override
    public SaveConfig getDefaultConfig() {
        return new SaveConfig("untitled");
    }

    public void loadScene() {
        GameScene scene = new GameScene(name);

        Map<String, Object> sceneDataMap = SceneParser.loadSave(
            GameConfig.savesDir + "/" + name + "/scene.json"
        );

        for (Map.Entry<String, Object> entry : sceneDataMap.entrySet()) {
            scene.addObject(SceneParser.createObject(entry));
        }

        CameraConfig cameraConfig = ConfigManager.load(CameraConfig.class, "scene-camera");
        scene.setCamera(new Camera(cameraConfig.pos, cameraConfig.zoom));
    }

    public void saveWithCamera() {
        lastLoadTime = TimeUtils.millis() / 1000f;
        ConfigManager.save(name);
        ConfigManager.save("scene-camera");
    }

    @Override
    public void dispose() {
        ConfigManager.unloadConfig(name);
        if (ConfigManager.getConfig("scene-camera") != null)
            ConfigManager.unloadConfig("scene-camera");
    }
}
