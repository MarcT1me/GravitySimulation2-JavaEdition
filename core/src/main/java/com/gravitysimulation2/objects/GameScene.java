package com.gravitysimulation2.objects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.gravitysimulation2.gameinterface.InterfaceObject;
import com.gravitysimulation2.objects.camera.Camera;
import com.gravitysimulation2.objects.camera.CameraController;
import com.gravitysimulation2.objects.object.GameObject;
import com.gravitysimulation2.objects.physic.Simulation;
import com.gravitysimulation2.save.SaveConfig;

import java.util.HashMap;
import java.util.Map;

public class GameScene extends InterfaceObject implements IConfigNeeded, IUpdatable, IRenderer {
    public boolean loaded = false;

    public String name;
    public SaveConfig saveConfig;

    public Simulation simulation;

    public final Map<String, GameObject> objects = new HashMap<>();
    public Camera camera;
    public CameraController cameraController;

    public ShapeRenderer shapeRenderer;

    public void load(boolean paused) {
        loaded = false;
        saveConfig.loadScene(this);
        simulation.paused = paused;
        loaded = true;
    }

    public GameScene(SaveConfig saveConfig) {
        this.name = saveConfig.name;
        this.saveConfig = saveConfig;
        shapeRenderer = new ShapeRenderer();
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public void addObject(GameObject object) {
        objects.put(object.name, object);
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setCameraController(CameraController controller) {
        this.cameraController = controller;
    }

    public void applyConfigs() {
        updateRootGroup();

        objects.values().forEach(
            object -> {
                object.objectType.applyConfigs();
                object.objectType.updateRootGroup();
            }
        );

        simulation.applyConfigs();
    }

    public void resize(ScreenViewport viewport) {
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
    }

    @Override
    public void preUpdate(float deltaTime) {
        objects.values().forEach(
            gameObject -> gameObject.preUpdate(deltaTime)
        );
    }

    @Override
    public void update(float deltaTime) {
        if (cameraController != null) {
            cameraController.update(deltaTime);
        }
        objects.values().forEach(
            gameObject -> gameObject.update(deltaTime)
        );
    }

    @Override
    public void preRender() {
        camera.updateAttach();
        objects.values().forEach(GameObject::preRender);
    }

    @Override
    public void render() {
        objects.values().forEach(
            GameObject::render
        );
    }

    @Override
    public void setupUI() {
        objects.values().forEach(
            (object) -> rootGroup.addActor(object.objectType.updateRootGroup().rootGroup)
        );
    }

    @Override
    public void renderUiElements() {

    }

    public void clear() {
        objects.values().forEach(
            GameObject::dispose
        );
        objects.clear();
        simulation.space.clear();
    }

    public void stopSimulation() {
        simulation.dispose();
        try {
            simulation.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dispose() {
        stopSimulation();
        clear();
        shapeRenderer.dispose();
    }
}
