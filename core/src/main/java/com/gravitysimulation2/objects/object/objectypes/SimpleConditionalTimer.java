package com.gravitysimulation2.objects.object.objectypes;

public class SimpleConditionalTimer {
    private float timeAccumulator = 0f;
    private float intervalLength = 12;

    public void setIntervalLength(float intervalLength) {
        this.intervalLength = intervalLength;
    }

    public boolean update(float deltaTime) {
        timeAccumulator += deltaTime;
        return timeAccumulator > intervalLength;
    }

    public void restore() {
        this.timeAccumulator = 0f;
    }
}
