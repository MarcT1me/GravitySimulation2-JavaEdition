package com.gravitysimulation2.objects.object.objectypes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gravitysimulation2.objects.object.GameObject;
import com.gravitysimulation2.save.SceneParser;

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
    public void render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color.x, color.y, color.z, 1.0f);
        Vector2 screenPos = fromWorldToScreenPosition(sourceObject.physicBody.pos);
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
