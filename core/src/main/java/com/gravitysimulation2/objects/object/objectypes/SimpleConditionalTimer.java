package com.gravitysimulation2.objects.object.objectypes;

import com.gravitysimulation2.objects.GameScene;

public class SimpleConditionalTimer {
    private float timeAccumulator = 0f;
    private float intervalLength = 12;

    public void setIntervalLength(float intervalLength) {
        this.intervalLength = intervalLength;
    }

    public boolean update(float deltaTime) {
        timeAccumulator += deltaTime;
        return timeAccumulator * GameScene.speeds.get("simulation") > intervalLength;
    }

    public void restore() {
        this.timeAccumulator = 0f;
    }
}
