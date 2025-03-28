package com.gravitysimulation2.objects.object.objectypes;

import com.gravitysimulation2.objects.GameScene;
import com.gravitysimulation2.objects.object.GameObject;

public class SimpleConditionalTimer {
    private GameObject sourceObject;

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
        return timeAccumulator * sourceObject.scene.saveConfig.simulationSpeed > intervalLength;
    }

    public void restore() {
        this.timeAccumulator = 0f;
    }
}
