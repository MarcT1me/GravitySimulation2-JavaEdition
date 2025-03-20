package com.gravitysimulation2.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.gravitysimulation2.objects.RendererObject.*;
import com.gravitysimulation2.objects.scene.GameScene;
import com.gravitysimulation2.physic.PhysicBody;

public class GameObject extends PhysicBody implements IUpdatable, IRenderer, IUiRenderer, Disposable {
    private RendererObject renderer;

    public GameObject(
        RendererObjectTypes objectType,
        Vector2 pos, float angle,
        float mass, float density, float radius,
        Vector2 velocity, float angularVelocity,
        RendererObjectData rendererData
    ) {
        super(
            pos, angle,
            mass, density, radius,
            velocity, angularVelocity
        );

        switch (objectType) {
            case PLANET:
                renderer = new PlanetRenderer(this, rendererData);
                break;
            case SATELLITE:
                renderer = new SatelliteRenderer(this, rendererData);
                break;
            case STAR:
                renderer = new StarRenderer(this, rendererData);
                break;
        }
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
    public void uiElement() {
        renderer.uiElement();
        return;
    }

    @Override
    public void render() {
        renderer.render();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
