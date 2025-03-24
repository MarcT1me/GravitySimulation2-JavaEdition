package com.gravitysimulation2.objects.object.objectypes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gravitysimulation2.objects.object.GameObject;
import com.gravitysimulation2.save.SceneParser;

import java.util.Map;

public class PlanetObject extends ObjectType {
    protected Vector3 color;

    public PlanetObject(GameObject sourceObject, Map<String, Object> objectData) {
        super(sourceObject, objectData);
        this.color = SceneParser.parseVector3(objectData.get("color"));
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
        Vector2 screenCenter = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        Vector2 screenPos = fromWorldToScreenPosition(sourceObject.physicBody.pos);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color.x, color.y, color.z, 1.0f);
        shapeRenderer.circle(screenPos.x, screenPos.y, fromWorldToScreenScalar(sourceObject.physicBody.radius));
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(color.x, color.y, color.z, 0.1f);
        shapeRenderer.line(
            screenCenter,
            screenPos
        );
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

