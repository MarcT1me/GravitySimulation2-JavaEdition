package com.gravitysimulation2.objects.scene;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.gravitysimulation2.GravitySimulation2;
import com.gravitysimulation2.gameinterface.menu.MenuObject;
import com.gravitysimulation2.objects.*;

import java.util.HashMap;
import java.util.Map;

public class GameScene extends MenuObject implements IUpdatable, IRenderer, Disposable {
    private static GameScene current;
    public static boolean loaded = false;
    private SceneScreen sceneScreen;

    public String name;

    public final Map<String, GameObject> objects = new HashMap<>();
    public final Map<String, Float> speeds = new HashMap<>();

    public Camera camera;

    public ShapeRenderer shapeRenderer;

    public GameScene(String name) {
        this.name = name;
        shapeRenderer = new ShapeRenderer();
        sceneScreen = (SceneScreen) GravitySimulation2.instance.menuMap.get("scene");
    }

    public static GameScene createNew(String name) {
        GameScene scene = new GameScene(name);
        GameScene.setCurrent(scene, false);
        return scene;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public static GameScene getCurrent() {
        return current;
    }

    public static void setCurrent(GameScene scene, boolean setLoaded) {
        current = scene;
        loaded = setLoaded;
    }

    public void addObject(GameObject object) {
        objects.put(object.name, object);
    }

    public void delObject(GameObject object) {
        objects.remove(object.name);
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
    public void render() {
        shapeRenderer.begin();
        objects.values().forEach(
            GameObject::render
        );
        shapeRenderer.end();
        shapeRenderer.flush();
    }

    @Override
    public void setupUI() {
        objects.values().forEach(
            (object) -> {
                rootGroup.addActor(rootGroup);
            }
        );
    }

    @Override
    public void dispose() {
        current = null;
        loaded = false;

        // objects
        objects.values().forEach(
            GameObject::dispose
        );

        // this scene
        shapeRenderer.dispose();
    }
}
