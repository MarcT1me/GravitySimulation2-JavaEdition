package com.gravitysimulation2.objects.RendererObject;

import com.badlogic.gdx.math.Vector3;

public record RendererObjectData(Vector3 color) {
    @Override
    public Vector3 color() {
        return color != null ? color : new Vector3();
    }
}
