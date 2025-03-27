package com.gravitysimulation2.objects.object.objectypes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.gravitysimulation2.objects.object.GameObject;

import java.util.Map;

public class PlanetObject extends ObjectType {
    public PlanetObject(GameObject sourceObject, Map<String, Object> objectData) {
        super(sourceObject, objectData);
    }

    @Override
    public void preRender() {
        updateScreenPos();

        if (isNotAllowedScreenPositions()) return;

        super.preRender();
    }

    @Override
    public void render() {
        if (isNotAllowedScreenPositions()) return;

        super.render();

        float radius = fromWorldToScreenScalar(sourceObject.physicBody.radius);
        if (isAllowRadius(radius)) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(color.x, color.y, color.z, 1.0f);
            shapeRenderer.circle(screenPos.x, screenPos.y, radius);
            shapeRenderer.end();
        }

        renderUiElements();
    }
}

