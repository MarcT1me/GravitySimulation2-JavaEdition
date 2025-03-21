package com.gravitysimulation2.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class MenuObject implements Screen {
    protected final Stage stage;
    protected final Skin skin;

    public Group rootGroup;

    public MenuObject() {
        ScreenViewport viewport = new ScreenViewport();
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport);
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    }

    protected abstract void setupUI();

    protected Label createLabel(
        String text, Color color, float fontSize, float x, float y
    ) {
        Label label = new Label(text, skin);
        label.setColor(color);
        label.setFontScale(fontSize);
        label.setSize(label.getPrefWidth(), label.getPrefHeight());
        label.setPosition(x, y);
        return label;
    }

    protected TextButton createTextButton(
        String text,
        float x, float y,
        float width, float height
    ) {
        TextButton btn = new TextButton(text, skin);
        btn.setSize(width, height);
        btn.setPosition(x, y);
        return btn;
    }

    public MenuObject updateRootGroup() {
        stage.clear();
        rootGroup = new Group();
        setupUI();
        stage.addActor(rootGroup);
        return this;
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        updateRootGroup();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
