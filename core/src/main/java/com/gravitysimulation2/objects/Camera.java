package com.gravitysimulation2.objects;

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
            (objectPos.x - pos.x) / zoom,
            (objectPos.y - pos.y) / zoom
        );
    }

    public void move(Vector2 offset) {
        pos.add(offset);
    }

    public void move(float offset) {
        zoom += offset;
    }
}
