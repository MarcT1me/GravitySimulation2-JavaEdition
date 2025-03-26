package com.gravitysimulation2.objects.object.objectypes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.gravitysimulation2.gameinterface.InterfaceObject;
import com.gravitysimulation2.objects.object.GameObject;
import com.gravitysimulation2.objects.IRenderer;
import com.gravitysimulation2.objects.IUpdatable;
import com.gravitysimulation2.save.SceneParser;

import java.util.Map;
import java.util.ArrayDeque;
import java.util.Queue;

class FixedVector2Queue {
    private final Queue<Vector2> queue;
    private int maxSize;

    public FixedVector2Queue(int fixedSize) {
        this.maxSize = fixedSize;
        this.queue = new ArrayDeque<>(fixedSize);
    }

    public void setMaxSize(int fixedSize) {
        this.maxSize = fixedSize;
    }

    public void add(Vector2 element) {
        Vector2 copy = new Vector2(element); // Создаем копию, чтобы избежать изменений
        if (queue.size() >= maxSize) {
            queue.poll(); // Удаляем головной элемент
        }
        queue.offer(copy); // Добавляем в конец
    }

    public Iterable<Vector2> getElements() {
        return queue;
    }

    public void clear() {
        queue.clear();
    }
}

public abstract class ObjectType extends InterfaceObject implements IUpdatable, IRenderer, Disposable {
    public GameObject sourceObject;
    public Map<String, Object> objectData;
    public Vector2 screenPos;
    public Vector3 color;

    protected ShapeRenderer shapeRenderer;
    protected FixedVector2Queue trajectory = new FixedVector2Queue(200);

    public ObjectType(GameObject sourceObject, Map<String, Object> objectData) {
        this.sourceObject = sourceObject;
        this.objectData = objectData;

        this.shapeRenderer = sourceObject.scene.shapeRenderer;
        this.color = SceneParser.parseVector3(objectData.get("color"));
    }

    public Vector2 fromWorldToScreenPosition(Vector2 vec) {
        return sourceObject.scene.camera.fromWorldToScreenPosition(vec);
    }

    public float fromWorldToScreenScalar(float scalar) {
        return scalar / sourceObject.scene.camera.zoom;
    }

    @Override
    public void update(float deltaTime) {
        trajectory.add(sourceObject.physicBody.pos);
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
        for (Vector2 point : trajectory.getElements()) {
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
        trajectory.clear();
    }
}
