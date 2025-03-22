package com.gravitysimulation2.gameinterface.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.gravitysimulation2.gameinterface.InterfaceObject;

import com.gravitysimulation2.config.ConfigManager;
import com.gravitysimulation2.config.GameConfig;

public abstract class MenuObject extends InterfaceObject implements Screen {
    // menu screen stage
    protected ScreenViewport viewport;
    public Stage stage;

    protected MenuObject() {
        viewport = new ScreenViewport();
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport);
        stage.addActor(rootGroup);
    }

    @Override
    public void resize(int width, int height) {
        if (width != 0) {
            viewport.update(width, height, true);
            updateRootGroup();
        }
    }

    public MenuObject updateRootGroup() {
        rootGroup.clear();
        setupUI();
        rootGroup.setPosition(0, 0);
        if (GameConfig.isDebugUi) rootGroup.debugAll();
        return this;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    protected void renderUiElements() {

    }

    @Override
    public void show() {
        rootGroup.setVisible(true);
    }

    @Override
    public void hide() {
        rootGroup.setVisible(false);
    }

    @Override
    public void render(float delta) {
        renderUiElements();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
