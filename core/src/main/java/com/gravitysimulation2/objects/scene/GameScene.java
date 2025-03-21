package com.gravitysimulation2.objects.scene;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.gravitysimulation2.objects.*;

import java.util.HashMap;
import java.util.Map;

public class GameScene implements IUpdatable, IRenderer, IUiRenderer, Disposable {
    private static GameScene current;

    public final Map<String, GameObject> objects = new HashMap<>();
    public final Map<String, Float> speeds = new HashMap<>();

    public Camera camera;

    public ShapeRenderer shapeRenderer;

    public GameScene() {
        shapeRenderer = new ShapeRenderer();
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public static GameScene getCurrent() {
        return current;
    }

    public static void setCurrent(GameScene scene) {
        current = scene;
    }

    @Override
    public void preUpdate(float deltaTime) {
        objects.values().forEach(
            gameObject -> gameObject.preUpdate(deltaTime)
        );
    }

    @Override
    public void update(float deltaTime) {
        objects.values().forEach(
            gameObject -> gameObject.update(deltaTime)
        );
    }

    @Override
    public void uiElement() {
        objects.values().forEach(
            GameObject::uiElement
        );
    }

    @Override
    public void render() {
        shapeRenderer.begin();
        objects.values().forEach(
            GameObject::render
        );
        shapeRenderer.end();
        shapeRenderer.flush();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        objects.values().forEach(
            GameObject::dispose
        );
    }
}
