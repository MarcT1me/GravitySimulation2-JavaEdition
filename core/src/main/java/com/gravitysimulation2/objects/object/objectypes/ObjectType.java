package com.gravitysimulation2.objects.object.objectypes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

import com.gravitysimulation2.FixedVector2Queue;
import com.gravitysimulation2.SimpleConditionalTimer;
import com.gravitysimulation2.config.ConfigManager;
import com.gravitysimulation2.config.GraphicConfig;
import com.gravitysimulation2.gameinterface.InterfaceObject;
import com.gravitysimulation2.objects.object.GameObject;
import com.gravitysimulation2.objects.IRenderer;
import com.gravitysimulation2.objects.IUpdatable;
import com.gravitysimulation2.save.SceneParser;

import java.util.Map;

public abstract class ObjectType extends InterfaceObject implements IUpdatable, IRenderer, Disposable {
    public GameObject sourceObject;
    public Map<String, Object> objectData;
    public Vector2 screenPos;
    public Vector3 color;

    protected final ShapeRenderer shapeRenderer;
    protected final FixedVector2Queue trajectoryQueue;
    protected final SimpleConditionalTimer trajectoryTimer;

    public ObjectType(GameObject sourceObject, Map<String, Object> objectData) {
        this.sourceObject = sourceObject;
        this.objectData = objectData;
        this.color = SceneParser.parseVector3(objectData.get("color"));

        this.shapeRenderer = sourceObject.scene.shapeRenderer;
        this.trajectoryQueue = new FixedVector2Queue();
        this.trajectoryTimer = new SimpleConditionalTimer();

        applyConfigs();
    }

    public void applyConfigs() {
        GraphicConfig config = (GraphicConfig) ConfigManager.getConfig("graphic config");

        this.trajectoryQueue.setMaxSize(config.trajectoryLen);
        this.trajectoryTimer.setIntervalLength(config.trajectoryInterval);
    }

    public Vector2 fromWorldToScreenPosition(Vector2 vec) {
        return sourceObject.scene.camera.fromWorldToScreenPosition(vec);
    }

    public float fromWorldToScreenScalar(float scalar) {
        return scalar / sourceObject.scene.camera.zoom;
    }

    @Override
    public void update(float deltaTime) {
        if (trajectoryTimer.update(deltaTime)) {
            trajectoryQueue.add(sourceObject.physicBody.pos);
            trajectoryTimer.restore();
        }
    }

    @Override
    public void render() {
        // prepare rendering
        screenPos = fromWorldToScreenPosition(sourceObject.physicBody.pos);
        Vector2 screenCenter = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // render
        shapeRenderer.setColor(color.x, color.y, color.z, 0.1f);
        shapeRenderer.line(
            (screenCenter.cpy().add(screenPos)).scl(0.5f),
            screenPos
        );

        // prepare orbit
        shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 0.5f);

        // render orbits
        Vector2 prev = null;
        for (Vector2 point : trajectoryQueue.getElements()) {
            point = fromWorldToScreenPosition(point);
            if (prev != null) {
                shapeRenderer.line(prev, point);
            }
            prev = point;
        }

        // render end
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        trajectoryQueue.clear();
    }
}
