package com.gravitysimulation2.save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.gravitysimulation2.GravitySimulation2;
import com.gravitysimulation2.config.Config;
import com.gravitysimulation2.config.ConfigManager;
import com.gravitysimulation2.config.GameConfig;
import com.gravitysimulation2.objects.Camera;
import com.gravitysimulation2.objects.scene.GameScene;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SaveConfig implements Config<SaveConfig>, Disposable {
    public String name = "untitled";
    public long lastLoadTime = 0;
    public long createTime = 0;

    public static List<SaveConfig> scanSaves() {
        List<SaveConfig> saves = new LinkedList<>();
        FileHandle savesDir = Gdx.files.local(GameConfig.savesDir);

        if (!savesDir.exists() || !savesDir.isDirectory()) {
            Gdx.app.error("SaveConfig", "Cant scan dir");
            return saves;
        }

        for (FileHandle saveDir : savesDir.list()) {
            if (saveDir.isDirectory()) {
                String saveName = saveDir.name();
                FileHandle configFile = saveDir.child("save.json");

                if (configFile.exists()) {
                    SaveConfig config = ConfigManager.load(
                        SaveConfig.class, saveName,
                        new Class[]{String.class}, new Object[]{saveName}
                    );
                    saves.add(config);
                }
            }
        }

        return saves;
    }

    public SaveConfig() {
    }

    public SaveConfig(String name) {
        this.name = name;
    }

    @Override
    public String getConfigPath() {
        return GameConfig.savesDir + "/" + name + "/save.json";
    }

    @Override
    public SaveConfig getDefaultConfig() {
        return new SaveConfig("untitled");
    }

    public void loadScene(GameScene scene) {
        Map<String, Object> sceneDataMap = SceneParser.loadSave(
            GameConfig.savesDir + "/" + name + "/scene.json"
        );

        for (Map.Entry<String, Object> entry : sceneDataMap.entrySet()) {
            scene.addObject(SceneParser.createObject(entry));
        }

        CameraConfig cameraConfig = ConfigManager.load(
            CameraConfig.class, "scene-camera",
            ConfigManager.emptyParametersTypes,
            ConfigManager.emptyParameters
        );
        scene.setCamera(new Camera(new Vector2(cameraConfig.posX, cameraConfig.posY), cameraConfig.zoom));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        GameScene.loaded = true;
    }

    public String getFormattedSaveTime(long time) {
        return LocalDateTime
            .ofInstant(Instant.ofEpochMilli(time * 1000), ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void saveWithCamera() {
        lastLoadTime = TimeUtils.millis() / 1000;
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
