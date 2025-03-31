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
        if (attachObject == null)
            return;
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
        return objectPos.cpy().sub(
            pos.cpy().add(
                attachPos
            )
        ).scl(
            1 / zoom
        ).toVector2().add(
            getScreenCenter()
        );
    }

    public Vector2D fromScreenToWorldPosition(Vector2 screenPos) {
        return new Vector2D(
            screenPos.cpy().sub(
                getScreenCenter()
            )
        ).scl(
            zoom
        ).add(
            pos
        );
    }

    public static Vector2 getScreenCenter() {
        return new Vector2(
            Gdx.graphics.getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f
        );
    }

    public void move(Vector2 offset) {
        pos.add(new Vector2D(offset).scl(zoom));
    }

    public void zoom(float offset) {
        zoom += offset * zoom;
    }

    public void zoomToPoint(float offset, Vector2 screenPoint) {
        // excepting errors
        if (zoom + offset <= 0f)  // except negative zoom
            return;
        if (offset < -0.5f)  // recursion calling to split zooming on smallest parts
            zoomToPoint(offset / 2f, screenPoint);

        Vector2D before = fromScreenToWorldPosition(screenPoint);

        // zooming
        float backupZoom = zoom;
        zoom(offset);
        if (zoom <= 0f)
            zoom = backupZoom;

        Vector2D after = fromScreenToWorldPosition(screenPoint);

        // moving to screen point
        pos.add(
            before.sub(
                after
            ).scl(
                0.5f
            )
        );
    }

    @Override
    public void preUpdate(float deltaTime) {

    }

    @Override
    public void update(float deltaTime) {

    }
}
