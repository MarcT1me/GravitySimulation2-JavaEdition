package com.gravitysimulation2.objects.object;

import com.badlogic.gdx.utils.Disposable;

import com.gravitysimulation2.objects.GameScene;
import com.gravitysimulation2.objects.IRenderer;
import com.gravitysimulation2.objects.IUpdatable;
import com.gravitysimulation2.objects.object.objectypes.*;
import com.gravitysimulation2.objects.physic.PhysicBody;

import java.util.Map;

public class GameObject implements IUpdatable, IRenderer, Disposable {
    public String name;
    public GameScene scene;

    public final PhysicBody physicBody;
    public ObjectType objectType;

    public GameObject(
        GameScene scene,
        String name,
        PhysicBody body,
        ObjectTypes objectType,
        Map<String, Object> objectData
    ) {
        this.scene = scene;
        this.name = name;
        this.physicBody = body;

        switch (objectType) {
            case STAR -> this.objectType = new StarObject(this, objectData);
            case PLANET -> this.objectType = new PlanetObject(this, objectData);
            case SATELLITE -> this.objectType = new SatelliteObject(this, objectData);
            default -> this.objectType = new EmptyObject(this, objectData);
        }
    }

    @Override
    public void preUpdate(float deltaTime) {
        objectType.preUpdate(deltaTime);
    }

    @Override
    public void update(float deltaTime) {
        objectType.update(deltaTime);
    }

    @Override
    public void preRender() {
        objectType.preRender();
    }

    @Override
    public void render() {
        objectType.render();
    }

    @Override
    public void dispose() {
        objectType.dispose();
    }
}
