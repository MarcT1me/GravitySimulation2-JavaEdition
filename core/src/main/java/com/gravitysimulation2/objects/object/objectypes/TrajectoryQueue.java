package com.gravitysimulation2.objects.object.objectypes;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayDeque;

public class TrajectoryQueue {
    private final ArrayDeque<Vector2> queue;
    private int maxSize;

    public TrajectoryQueue() {
        this.queue = new ArrayDeque<>();
    }

    public void setMaxSize(int fixedSize) {
        if (this.maxSize > fixedSize) pullingToMaxSize();
        this.maxSize = fixedSize;
    }

    public void add(Vector2 element) {
        Vector2 copy = new Vector2(element);
        queue.offer(copy);
        pullingToMaxSize();
    }

    private void pullingToMaxSize() {
        while (queue.size() >= maxSize) queue.poll();
    }

    public Iterable<Vector2> getElements() {
        return queue;
    }

    public int size() {
        return queue.size();
    }

    public void clear() {
        queue.clear();
    }
}
