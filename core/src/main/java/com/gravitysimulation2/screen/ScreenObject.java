package com.gravitysimulation2.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.gravitysimulation2.config.ConfigManager;
import com.gravitysimulation2.config.GameConfig;
import com.gravitysimulation2.gameinterface.InterfaceObject;
import com.gravitysimulation2.objects.GameScene;
import com.gravitysimulation2.objects.IConfigNeeded;
import com.gravitysimulation2.objects.camera.CameraController;

public abstract class ScreenObject extends InterfaceObject implements Screen, IConfigNeeded {
    // screen stage
    protected ScreenViewport viewport;
    public Stage stage;
    public InputMultiplexer inputMultiplexer;

    // scene
    protected GameScene scene;
    DefaultBackground background;

    Label fpsLbl;
    Label versionLbl;

    protected ScreenObject() {
        super();
        // create viewport
        viewport = new ScreenViewport();
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // create screen stage
        stage = new Stage(viewport);
        stage.addActor(rootGroup);

        background = new DefaultBackground();  // bg
        inputMultiplexer = new InputMultiplexer();  // input
    }

    public void updateInputMultiplexer() {
        inputMultiplexer.clear();
        inputMultiplexer.addProcessor(stage);
        if (scene != null) {
            CameraController cameraController = scene.cameraController;
            if (cameraController != null)
                inputMultiplexer.addProcessor(cameraController);
        }
    }

    @Override
    public void setupUI() {
        versionLbl = createLabel("Version: " + GameConfig.version, Color.WHITE, 1f);

        fpsLbl = createLabel("FPS: 0000", Color.WHITE, 1f);

        rootGroup.addActor(fpsLbl);
        rootGroup.addActor(versionLbl);
        applyConfigs();
    }

    public void attachToScene(GameScene scene) {
        this.scene = scene;
    }

    public void applyConfigs() {
        if (scene != null) {
            scene.applyConfigs();
        }

        float curPosY = 0f;

        GameConfig config = (GameConfig) ConfigManager.getConfig("game config");

        versionLbl.setVisible(config.showVersion);
        versionLbl.setPosition(5, curPosY);

        fpsLbl.setVisible(config.showFps);
        if (config.showVersion) curPosY += versionLbl.getHeight();
        fpsLbl.setPosition(5, curPosY);
    }

    @Override
    public void resize(int width, int height) {
        if (width != 0) {
            viewport.update(width, height, true);
            background.resize(viewport);
            if (scene != null) scene.resize(viewport);
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
    public void renderUiElements() {
        if (fpsLbl.isVisible())
            fpsLbl.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
