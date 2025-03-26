package com.gravitysimulation2.objects.object.objectypes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.gravitysimulation2.objects.object.GameObject;

import java.util.Map;

public class SatelliteObject extends ObjectType {
    public SatelliteObject(GameObject sourceObject, Map<String, Object> objectData) {
        super(sourceObject, objectData);
    }

    @Override
    public void setupUI() {

    }

    @Override
    public void preUpdate(float deltaTime) {

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void preRender() {

    }

    @Override
    public void render() {
        super.render();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color.x, color.y, color.z, 1.0f);
        shapeRenderer.circle(screenPos.x, screenPos.y, fromWorldToScreenScalar(sourceObject.physicBody.radius));
        shapeRenderer.end();

        renderUiElements();
    }

    @Override
    public void renderUiElements() {

    }

    @Override
    public void dispose() {

    }
}
