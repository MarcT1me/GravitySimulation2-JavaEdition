package com.gravitysimulation2.objects.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.gravitysimulation2.config.ConfigManager;
import com.gravitysimulation2.config.GameConfig;
import com.gravitysimulation2.objects.IConfigNeeded;

import java.util.concurrent.atomic.AtomicBoolean;

public class CameraController implements InputProcessor, IConfigNeeded {
    private final Camera camera;
    private float moveSpeed;
    private float mouseSensitivity;
    private float zoomSpeed;
    private final Vector2 lastTouch = new Vector2();
    private boolean isDragging = false;

    public CameraController(Camera camera) {
        this.camera = camera;

        applyConfigs();
    }

    @Override
    public void applyConfigs() {
        GameConfig gameConfig = (GameConfig) ConfigManager.getConfig("game config");

        moveSpeed = gameConfig.cameraMoveSpeed;
        mouseSensitivity = gameConfig.mouseSensitivity;
        zoomSpeed = gameConfig.cameraZoomSpeed;
    }

    public void update(float deltaTime) {
        // Управление клавиатурой
        float moveSpeed = this.moveSpeed * deltaTime;
        float zoomSpeed = this.zoomSpeed * deltaTime;

        // Перемещение
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.move(new Vector2(-moveSpeed, 0));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.move(new Vector2(moveSpeed, 0));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.move(new Vector2(0, moveSpeed));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.move(new Vector2(0, -moveSpeed));
        }

        // Зум
        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_UP)) {
            camera.zoom(-zoomSpeed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN)) {
            camera.zoom(zoomSpeed);
        }
    }
    // Реализация InputProcessor для управления мышью

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            lastTouch.set(screenX, screenY);
            isDragging = true;
            return true;
        } else if (button == Input.Buttons.MIDDLE) {
            AtomicBoolean isFindCollision = new AtomicBoolean(false);
            camera.scene.objects.values().forEach(
                object -> {
                    Vector2 objDistVec = camera.fromWorldToScreenPosition(
                        object.physicBody.pos
                    ).sub(
                        new Vector2(
                            screenX,
                            Gdx.graphics.getHeight() - screenY
                        )
                    );
                    if (objDistVec.len() < 20) {
                        camera.attachToObject(object);
                        isFindCollision.set(true);
                    }
                }
            );
            if (!isFindCollision.get())
                camera.cancelAttach();
            return isFindCollision.get();
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            isDragging = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isDragging) {
            Vector2 delta = new Vector2(
                lastTouch.x - screenX,
                screenY - lastTouch.y
            ).scl(mouseSensitivity);
            camera.move(delta);
            lastTouch.set(screenX, screenY);
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        camera.zoomToPoint(
            amountY * zoomSpeed,
            new Vector2(
                Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()
            )
        );
        return true;
    }
}
