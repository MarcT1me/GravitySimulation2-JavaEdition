package com.gravitysimulation2.objects.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Camera {
    public Vector2 pos;
    public float zoom;

    public Camera(Vector2 pos, float zoom) {
        this.pos = pos;
        this.zoom = zoom;
    }

    public Vector2 fromWorldToScreenPosition(Vector2 objectPos) {
        return new Vector2(
            (objectPos.x - pos.x) / zoom + Gdx.graphics.getWidth() / 2f,
            (objectPos.y - pos.y) / zoom + Gdx.graphics.getHeight() / 2f
        );
    }

    public void move(Vector2 offset) {
        pos.add(offset.cpy().scl(zoom));
    }

    public void zoom(float offset) {
        zoom += offset * zoom;
    }

    public void zoomToPoint(float deltaZoom, Vector2 screenPoint) {
        Vector2 worldBefore = fromWorldToScreenPosition(screenPoint);
        zoom += deltaZoom;
        Vector2 worldAfter = fromWorldToScreenPosition(screenPoint);
        pos.add(worldAfter.sub(worldBefore));
    }
}
