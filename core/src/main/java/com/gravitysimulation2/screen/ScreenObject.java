package com.gravitysimulation2.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.gravitysimulation2.gameinterface.InterfaceObject;
import com.gravitysimulation2.objects.GameScene;

public abstract class ScreenObject extends InterfaceObject implements Screen {
    // screen stage
    protected ScreenViewport viewport;
    public Stage stage;

    // scene
    protected GameScene scene;
    DefaultBackground background;

    protected ScreenObject() {
        viewport = new ScreenViewport();
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport);
        stage.addActor(rootGroup);
        background = new DefaultBackground();
    }

    public void attachToScene(GameScene scene) {
        this.scene = scene;
    }

    public void applyConfigs() {
        if (scene != null) {
            scene.applyConfigs();
        }
    }

    @Override
    public void resize(int width, int height) {
        if (width != 0) {
            viewport.update(width, height, true);
            background.resize(viewport);
            updateRootGroup();
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    public void preRender() {
        background.preRender();
    }

    @Override
    public void render(float delta) {
        preRender();
        if (scene != null) {
            scene.preUpdate(delta);
            scene.update(delta);
            scene.preRender();
            scene.render();
            scene.renderUiElements();
        }

        renderUiElements();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
