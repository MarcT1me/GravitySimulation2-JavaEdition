package com.gravitysimulation2.gameinterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.gravitysimulation2.config.ConfigManager;
import com.gravitysimulation2.config.GameConfig;

public abstract class InterfaceObject {
    // global ui skin
    public static Skin skin;

    public Group rootGroup = new Group();

    public static void loadUiSkin() {
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
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

    protected static float getRelativeScreenWidthScalar(float width) {
        return (width / 480f) * Gdx.graphics.getWidth() *  // calculate relative scalar
            ((GameConfig) ConfigManager.getConfig("game config")).interfaceSize;  // apply interface size
    }

    protected static float getRelativeScreenHeightScalar(float height) {
        return (height / 720f) * Gdx.graphics.getHeight() *  // calculate relative scalar
            ((GameConfig) ConfigManager.getConfig("game config")).interfaceSize;  // apply interface size
    }

    protected abstract void setupUI();

    protected abstract void renderUiElements();
}
