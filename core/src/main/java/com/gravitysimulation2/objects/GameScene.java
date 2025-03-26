package com.gravitysimulation2.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gravitysimulation2.gameinterface.InterfaceObject;
import com.gravitysimulation2.objects.camera.Camera;
import com.gravitysimulation2.objects.object.GameObject;
import com.gravitysimulation2.objects.physic.PhysicBody;
import com.gravitysimulation2.save.SaveConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class GameScene extends InterfaceObject implements IUpdatable, IRenderer {
    public boolean loaded = false;
    public boolean paused = false;

    public String name;
    public SaveConfig saveConfig;

    public final Map<String, GameObject> objects = new HashMap<>();
    public static final Map<String, Float> speeds = new HashMap<>();

    public Camera camera;

    public ShapeRenderer shapeRenderer;

    public GameScene(SaveConfig saveConfig) {
        this.name = saveConfig.name;
        this.saveConfig = saveConfig;
        shapeRenderer = new ShapeRenderer();
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void addObject(GameObject object) {
        objects.put(object.name, object);
    }

    public void applyConfigs() {
        objects.values().forEach(
            object -> {
                object.objectType.applyConfigs();
                object.objectType.updateRootGroup();
            }
        );
    }

    public Stream<PhysicBody> getPhysicBodyStream() {
        return objects.values().stream().map(gameObject -> gameObject.physicBody);
    }

    public void resize(ScreenViewport viewport) {
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
    }

    @Override
    public void preUpdate(float deltaTime) {
        if (!paused) {
            objects.values().forEach(
                gameObject -> gameObject.preUpdate(deltaTime)
            );
        }
    }

    @Override
    public void update(float deltaTime) {
        // Управление камерой
        float moveSpeed = 500.0f * deltaTime;
        float zoomSpeed = 0.5f * deltaTime;

        // Движение стрелками
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.move(new Vector2(-moveSpeed, 0));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.move(new Vector2(moveSpeed, 0));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.move(new Vector2(0, moveSpeed));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.move(new Vector2(0, -moveSpeed));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_UP)) {
            camera.zoom(-zoomSpeed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN)) {
            camera.zoom(zoomSpeed);
        }

        if (!paused) {
            objects.values().forEach(
                gameObject -> gameObject.update(deltaTime)
            );
        }
    }

    @Override
    public void preRender() {
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

    @Override
    public void dispose() {
        // objects
        objects.values().forEach(
            GameObject::dispose
        );

        // this scene
        shapeRenderer.dispose();
    }
}
