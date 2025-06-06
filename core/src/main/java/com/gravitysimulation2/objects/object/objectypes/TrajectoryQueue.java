package com.gravitysimulation2.objects.object.objectypes;

import com.gravitysimulation2.objects.physic.Vector2D;

import java.util.ArrayDeque;

public class TrajectoryQueue {
    private final ArrayDeque<Vector2D> queue;
    private int maxSize;

    public TrajectoryQueue() {
        this.queue = new ArrayDeque<>();
    }

    public void setMaxSize(int fixedSize) {
        if (this.maxSize > fixedSize) pullingToMaxSize();
        this.maxSize = fixedSize;
    }

    public void add(Vector2D element) {
        queue.offer(element.cpy());
        pullingToMaxSize();
    }

    private void pullingToMaxSize() {
        while (queue.size() > maxSize) queue.poll();
    }

    public Iterable<Vector2D> getElements() {
        return queue;
    }

    public int size() {
        return queue.size();
    }

    public void clear() {
        queue.clear();
    }
}
