package com.gravitysimulation2.objects.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.gravitysimulation2.objects.GameScene;
import com.gravitysimulation2.objects.IUpdatable;
import com.gravitysimulation2.objects.object.GameObject;
import com.gravitysimulation2.objects.physic.Vector2D;

public class Camera implements IUpdatable {
    public GameScene scene;

    public Vector2D pos;
    public float zoom;

    private GameObject attachObject;
    private final Vector2D attachPos = new Vector2D();

    public Camera(GameScene scene, Vector2D pos, float zoom) {
        this.scene = scene;
        this.pos = pos;
        this.zoom = zoom;
    }

    public void attachToObject(GameObject object) {
        pos.setZero();
        this.attachObject = object;
    }

    public void cancelAttach() {
        if (attachObject == null) return;
        pos.set(attachObject.physicBody.pos.cpy());
        this.attachObject = null;
    }

    public void updateAttach() {
        if (attachObject != null)
            attachPos.set(attachObject.physicBody.pos);
        else
            attachPos.setZero();
    }

    public Vector2 fromWorldToScreenPosition(Vector2D objectPos) {
        return new Vector2(
            (float) (objectPos.x - (pos.x + attachPos.x)) / zoom + Gdx.graphics.getWidth() / 2f,
            (float) (objectPos.y - (pos.y + attachPos.y)) / zoom + Gdx.graphics.getHeight() / 2f
        );
    }

    public Vector2D fromScreenToWorldPosition(Vector2 screenPos) {
        Vector2 middleCalculateVec = screenPos.cpy().sub(
            new Vector2(
                Gdx.graphics.getWidth() / 2f,
                Gdx.graphics.getHeight() / 2f
            )
        );
        return new Vector2D(
            middleCalculateVec.x,
            middleCalculateVec.y
        ).scl(zoom).add(pos);
    }

    public void move(Vector2 offset) {
        pos.add(new Vector2D(offset.x, offset.y).scl(zoom));
    }

    public void zoom(float offset) {
        zoom += offset * zoom;
    }

    public void zoomToPoint(float offset, Vector2 screenPoint) {
        Vector2D before = fromScreenToWorldPosition(screenPoint);
        zoom(offset);
        Vector2D after = fromScreenToWorldPosition(screenPoint);

        Vector2D zoomPoint;
        zoomPoint = before.sub(after);
        zoomPoint.scl(0.5f);
        pos.add(new Vector2D(zoomPoint.x, zoomPoint.y));
    }

    @Override
    public void preUpdate(float deltaTime) {

    }

    @Override
    public void update(float deltaTime) {

    }
}
