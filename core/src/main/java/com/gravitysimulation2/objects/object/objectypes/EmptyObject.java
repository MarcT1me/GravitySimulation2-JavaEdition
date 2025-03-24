package com.gravitysimulation2.objects.object.objectypes;

import com.gravitysimulation2.objects.object.GameObject;

import java.util.Map;

public class EmptyObject extends ObjectType {
    public EmptyObject(GameObject sourceObject, Map<String, Object> rendererData) {
        super(sourceObject, rendererData);
    }

    @Override
    public void setupUI() {

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void preUpdate(float deltaTime) {

    }

    @Override
    public void renderUiElements() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void render() {

    }
}
