package com.gravitysimulation2.objects.physic;

import com.badlogic.gdx.utils.Disposable;
import com.gravitysimulation2.objects.IConfigNeeded;
import com.gravitysimulation2.objects.IUpdatable;
import com.gravitysimulation2.save.SimulationConfig;

import java.util.concurrent.TimeUnit;

public class Simulation extends Thread implements IConfigNeeded, IUpdatable, Disposable {
    public boolean running = true;
    public boolean paused = false;

    public final SimulationConfig config;
    public final SimulationSpace space;

    private static final long NANOS_IN_SECOND = 1_000_000_000L;
    private long targetTickTimeNanos;
    private long nextTickTime;
    private long previousTickTime; // Время предыдущего тика
    public float deltaTime;
    public int tickCount;
    public long lastTpsUpdateTime;
    public int currentTps;

    public Simulation(SimulationConfig config, SimulationSpace space) {
        this.config = config;
        this.space = space;
        updateTickTime(); // Инициализация при создании
    }

    @Override
    public void applyConfigs() {
        updateTickTime();
    }

    private void updateTickTime() {
        if (config.tps == 0)
            targetTickTimeNanos = 0;
        else
            targetTickTimeNanos = NANOS_IN_SECOND / config.tps;
    }

    private void tick() {
        nextTickTime += targetTickTimeNanos;
        long curTime = System.nanoTime();
        long sleepTimeNanos = nextTickTime - curTime;

        if (sleepTimeNanos > 0) {
            try {
                TimeUnit.NANOSECONDS.sleep(sleepTimeNanos);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        updateDeltaTime(curTime);
        updateTpsCounter(curTime);
    }

    private void updateDeltaTime(long curTime) {
        deltaTime = (curTime - previousTickTime) / (float) NANOS_IN_SECOND; // Корректный расчет
        previousTickTime = curTime; // Обновляем время предыдущего тика
    }

    private void updateTpsCounter(long curTime) {
        if (curTime - lastTpsUpdateTime >= NANOS_IN_SECOND) {
            currentTps = tickCount;
            tickCount = 0;
            lastTpsUpdateTime = curTime;
        }
        tickCount++;
    }

    public int getTps() {
        return currentTps;
    }

    @Override
    public void preUpdate(float deltaTime) {
        space.getPhysicBodyes().forEach(
            physicBody -> physicBody.preUpdate(deltaTime)
        );
    }

    @Override
    public void update(float deltaTime) {
        space.getPhysicBodyes().forEach(
            physicBody -> physicBody.update(deltaTime)
        );
    }

    @Override
    public void run() {
        previousTickTime = System.nanoTime(); // Инициализация времени предыдущего тика
        nextTickTime = previousTickTime;
        lastTpsUpdateTime = previousTickTime;

        while (running) {
            if (!paused) {
                float curDelta = deltaTime * config.speed;
                preUpdate(curDelta);
                update(curDelta);
            }
            tick();
        }
    }

    @Override
    public void dispose() {
        running = false;
    }
}
