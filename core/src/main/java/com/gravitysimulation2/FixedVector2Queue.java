package com.gravitysimulation2;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayDeque;
import java.util.Queue;

public class FixedVector2Queue {
    private final Queue<Vector2> queue;
    private int maxSize;

    public FixedVector2Queue() {
        this.queue = new ArrayDeque<>();
    }

    public void setMaxSize(int fixedSize) {
        this.maxSize = fixedSize;
    }

    public void add(Vector2 element) {
        Vector2 copy = new Vector2(element); // Создаем копию, чтобы избежать изменений
        while (queue.size() >= maxSize) {
            queue.poll(); // Удаляем головной элемент
        }
        queue.offer(copy); // Добавляем в конец
    }

    public Iterable<Vector2> getElements() {
        return queue;
    }

    public void clear() {
        queue.clear();
    }
}
