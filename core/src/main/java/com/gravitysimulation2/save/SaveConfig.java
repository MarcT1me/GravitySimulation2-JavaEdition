package com.gravitysimulation2.save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;

import com.gravitysimulation2.config.Config;
import com.gravitysimulation2.config.ConfigManager;
import com.gravitysimulation2.config.GameConfig;
import com.gravitysimulation2.objects.GameScene;
import com.gravitysimulation2.objects.camera.Camera;
import com.gravitysimulation2.objects.camera.CameraController;
import com.gravitysimulation2.objects.object.GameObject;
import com.gravitysimulation2.objects.physic.Simulation;
import com.gravitysimulation2.objects.physic.SimulationSpace;
import com.gravitysimulation2.objects.physic.Vector2D;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SaveConfig implements Config<SaveConfig>, Disposable {
    public String name;
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
                        new Class[]{String.class}, new Object[]{saveName},
                        false
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
        return new SaveConfig();
    }

    public void loadScene(GameScene scene) {
        // simulation
        SimulationConfig simulationConfig = ConfigManager.load(
            SimulationConfig.class, "simulation",
            new Class[]{String.class}, new Object[]{name},
            false
        );
        SimulationSpace space = new SimulationSpace();
        Simulation simulation = new Simulation(
            simulationConfig,
            space
        );
        scene.setSimulation(simulation);

        // objects
        Map<String, Object> sceneDataMap = SceneParser.loadSave(
            GameConfig.savesDir + "/" + name + "/scene.json"
        );
        for (Map.Entry<String, Object> entry : sceneDataMap.entrySet()) {
            GameObject object = SceneParser.createObject(scene, entry);
            scene.addObject(object);  // scene
            space.addBody(object.physicBody);  // simulation
        }

        // camera
        CameraConfig cameraConfig = ConfigManager.load(
            CameraConfig.class, "camera",
            new Class[]{String.class}, new Object[]{name},
            false
        );
        Camera camera = new Camera(
            scene,
            new Vector2D(cameraConfig.posX, cameraConfig.posY),
            cameraConfig.zoom
        );
        scene.setCamera(camera);
        scene.setCameraController(new CameraController(camera));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        scene.loaded = true;
    }

    public String getFormattedSaveTime(long time) {
        return LocalDateTime
            .ofInstant(Instant.ofEpochMilli(time * 1000), ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public void dispose() {
        ConfigManager.unloadConfig(name);
    }
}
