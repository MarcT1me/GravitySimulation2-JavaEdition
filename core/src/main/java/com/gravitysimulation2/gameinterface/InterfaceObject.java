package com.gravitysimulation2.gameinterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Disposable;

import com.gravitysimulation2.config.ConfigManager;
import com.gravitysimulation2.config.GameConfig;

public abstract class InterfaceObject implements Disposable {
    // global ui skin
    public static Skin skin;

    public Group rootGroup = new Group();

    public static void loadUiSkin() {
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    }

    public static void disposeSkin() {
        skin.dispose();
    }

    public static Label createLabel(String text, Color color, float fontSize) {
        Label label = new Label(text, skin);

        label.setColor(color);

        label.setFontScale(fontSize);
        label.setSize(label.getPrefWidth(), label.getPrefHeight());

        return label;
    }

    public static TextButton createTextButton(String text, Color labelColor, float fontSize, float width, float height) {
        TextButton btn = new TextButton(text, skin);

        btn.setSize(width, height);
        btn.getLabel().setFontScale(fontSize);
        btn.getLabel().setColor(labelColor);

        return btn;
    }

    public static CheckBox createCheckBox(String text, Color labelColor, float fontSize) {
        CheckBox box = new CheckBox(text, skin);

        box.getLabel().setFontScale(fontSize);
        box.getLabel().setColor(labelColor);
        box.setSize(box.getPrefWidth(), box.getPrefHeight());

        return box;
    }

    public static Slider createSlider(float min, float max, float stepSize, boolean vertical, Color color) {
        Slider slider = new Slider(min, max, stepSize, vertical, skin);

        slider.setColor(color);
        slider.setSize(slider.getPrefWidth(), slider.getPrefHeight());

        return slider;
    }

    protected static float getRelativeScreenWidthScalar(float width) {
        return (width / 480f) * Gdx.graphics.getWidth() *  // calculate relative scalar
            ((GameConfig) ConfigManager.getConfig("game config")).interfaceSize;  // apply interface size
    }

    protected static float getRelativeScreenHeightScalar(float height) {
        return (height / 720f) * Gdx.graphics.getHeight() *  // calculate relative scalar
            ((GameConfig) ConfigManager.getConfig("game config")).interfaceSize;  // apply interface size
    }

    public abstract void setupUI();

    public abstract void renderUiElements();

    public boolean isVisible() {
        return rootGroup.isVisible();
    }

    public InterfaceObject updateRootGroup() {
        rootGroup.clear();
        setupUI();
        if (GameConfig.isDebugUi) rootGroup.debugAll();
        return this;
    }

    public void show() {
        rootGroup.setVisible(true);
    }

    public void hide() {
        rootGroup.setVisible(false);
    }
}
