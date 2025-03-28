package com.gravitysimulation2.objects.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.gravitysimulation2.objects.IUpdatable;
import com.gravitysimulation2.objects.physic.Vector2D;

public class Camera implements IUpdatable {
    public Vector2 pos;
    public float zoom;

    public Camera(Vector2 pos, float zoom) {
        this.pos = pos;
        this.zoom = zoom;
    }

    public Vector2 fromWorldToScreenPosition(Vector2D objectPos) {
        return new Vector2(
            ((float) objectPos.x - pos.x) / zoom + Gdx.graphics.getWidth() / 2f,
            ((float) objectPos.y - pos.y) / zoom + Gdx.graphics.getHeight() / 2f
        );
    }

    public void move(Vector2 offset) {
        pos.add(offset.cpy().scl(zoom));
    }

    public void zoom(float offset) {
        zoom += offset * zoom;
    }

    public void zoomToPoint(float deltaZoom, Vector2 screenPoint) {
        Vector2 worldBefore = fromWorldToScreenPosition(new Vector2D(screenPoint.x, screenPoint.y));
        zoom += deltaZoom;
        Vector2 worldAfter = fromWorldToScreenPosition(new Vector2D(screenPoint.x, screenPoint.y));
        pos.add(worldAfter.sub(worldBefore));
    }

    @Override
    public void preUpdate(float deltaTime) {

    }

    @Override
    public void update(float deltaTime) {

    }
}
