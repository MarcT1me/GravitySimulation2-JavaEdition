package com.gravitysimulation2.objects.object.objectypes;

import com.gravitysimulation2.objects.object.GameObject;

public class SimpleConditionalTimer {
    private final GameObject sourceObject;

    private float timeAccumulator = 0f;
    private float intervalLength = 12;

    public SimpleConditionalTimer(GameObject sourceObject){
        this.sourceObject = sourceObject;
    }

    public void setIntervalLength(float intervalLength) {
        this.intervalLength = intervalLength;
    }

    public boolean update(float deltaTime) {
        timeAccumulator += deltaTime;
        return timeAccumulator * sourceObject.scene.simulation.config.speed > intervalLength;
    }

    public void restore() {
        this.timeAccumulator = 0f;
    }
}
