package com.gravitysimulation2.objects.object.objectypes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.gravitysimulation2.gameinterface.InterfaceObject;
import com.gravitysimulation2.objects.object.GameObject;
import com.gravitysimulation2.objects.IRenderer;
import com.gravitysimulation2.objects.IUpdatable;

import java.util.Map;

public abstract class ObjectType extends InterfaceObject implements IUpdatable, IRenderer, Disposable {
    public GameObject sourceObject;
    protected Map<String, Object> objectData;

    protected ShapeRenderer shapeRenderer;

    public ObjectType(GameObject sourceObject, Map<String, Object> objectData) {
        this.sourceObject = sourceObject;
        this.objectData = objectData;

        this.shapeRenderer = sourceObject.scene.shapeRenderer;
    }

    public Vector2 fromWorldToScreenPosition(Vector2 vec) {
        return sourceObject.scene.camera.fromWorldToScreenPosition(vec);
    }

    public float fromWorldToScreenScalar(float scalar) {
        return scalar / sourceObject.scene.camera.zoom;
    }
}
