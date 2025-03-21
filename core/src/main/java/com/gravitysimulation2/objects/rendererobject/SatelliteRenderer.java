package com.gravitysimulation2.objects.rendererobject;

import com.badlogic.gdx.math.Vector3;
import com.gravitysimulation2.objects.GameObject;

public class SatelliteRenderer extends RendererObject {
    protected Vector3 color;

    public SatelliteRenderer(GameObject sourceObject, RendererObjectData rendererData) {
        super(sourceObject);
        this.color = rendererData.color();
    }

    @Override
    public void uiElement() {

    }

    @Override
    public void render() {
        shapeRenderer.setColor(color.x, color.y, color.z, 1.0f);
        super.render();
    }

    @Override
    public void dispose() {

    }
}
