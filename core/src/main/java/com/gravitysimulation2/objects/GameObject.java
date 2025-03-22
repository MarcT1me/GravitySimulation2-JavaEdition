package com.gravitysimulation2.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
//import com.gravitysimulation2.objects.rendererobject.*;
import com.gravitysimulation2.objects.scene.GameScene;
import com.gravitysimulation2.physic.PhysicBody;

public class GameObject extends PhysicBody implements IUpdatable, IRenderer, Disposable {
    public String name;

//    private RendererObject renderer;

    public GameObject(
        String name,
//        ObjectTypes objectType,
//        RendererObjectData rendererData,
        Vector2 pos, float angle,
        float mass, float density, float radius,
        Vector2 velocity, float angularVelocity
    ) {
        super(
            pos, angle,
            mass, density, radius,
            velocity, angularVelocity
        );

        this.name = name;

//        switch (objectType) {
//            case PLANET:
//                renderer = new PlanetObject(this, rendererData);
//                break;
//            case SATELLITE:
//                renderer = new SatelliteObject(this, rendererData);
//                break;
//            case STAR:
//                renderer = new StarObject(this, rendererData);
//                break;
//        }
    }

    @Override
    public void preUpdate(float deltaTime) {
        updateForces();
        updateVelocity(deltaTime, GameScene.getCurrent().speeds.get("simulation"));
    }

    @Override
    public void update(float deltaTime) {
        updatePos(deltaTime, GameScene.getCurrent().speeds.get("simulation"));
    }

    @Override
    public void render() {
//        renderer.render();
    }

    @Override
    public void dispose() {
//        renderer.dispose();
    }
}
