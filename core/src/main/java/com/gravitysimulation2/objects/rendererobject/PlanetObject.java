package com.gravitysimulation2.objects.rendererobject;

import com.badlogic.gdx.math.Vector3;
import com.gravitysimulation2.objects.GameObject;

import java.util.Map;

public class PlanetObject extends RendererObject {
    protected Vector3 color;

    public PlanetObject(GameObject sourceObject, Map<String, Object> rendererData) {
        super(sourceObject, rendererData);
        this.rendererData = rendererData;
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
