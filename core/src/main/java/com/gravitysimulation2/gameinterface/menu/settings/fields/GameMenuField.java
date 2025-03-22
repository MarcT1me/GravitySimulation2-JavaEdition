package com.gravitysimulation2.gameinterface.menu.settings.fields;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.gravitysimulation2.GravitySimulation2;
import com.gravitysimulation2.config.ConfigManager;
import com.gravitysimulation2.config.GameConfig;
import com.gravitysimulation2.gameinterface.menu.MenuObject;

public class GameMenuField extends MenuObject {
    public float categoryStartX;
    GameConfig gameConfig;

    TextField interfaceSizeField;
    CheckBox fpsBox;
    CheckBox versionBox;
    CheckBox VBox;
    CheckBox posBox;

    CheckBox debugUiBox;

    public GameMenuField(float categoryStartX) {
        this.categoryStartX = categoryStartX;
        gameConfig = (GameConfig) ConfigManager.getConfig("game config");
    }

    @Override
    protected void setupUI() {
        float relativePad = getRelativeScreenHeightScalar(10f);
        float curPosY = Gdx.graphics.getHeight();
        float startPosX = categoryStartX + relativePad;

        float relativeFontSize = getRelativeScreenHeightScalar(2f);

        // Interface size
        Label interfaceSizeLbl = new Label("Interface size: ", skin);

        interfaceSizeLbl.setFontScale(relativeFontSize);
        interfaceSizeLbl.setColor(Color.WHITE);
        interfaceSizeLbl.setSize(interfaceSizeLbl.getPrefWidth(), interfaceSizeLbl.getPrefHeight());
        curPosY -= relativePad + interfaceSizeLbl.getHeight();
        interfaceSizeLbl.setPosition(startPosX + relativePad, curPosY);

        // field
        interfaceSizeField = new TextField(String.valueOf(gameConfig.interfaceSize), skin);

        interfaceSizeField.setMaxLength(3);
        interfaceSizeField.setTextFieldFilter((textField, c) -> {
            if (Character.isDigit(c)) {
                return true;
            } else {
                String currentText = textField.getText();
                return c == '.' && !currentText.contains(".") && !currentText.isEmpty();
            }
        });
        interfaceSizeField.setSize(relativePad * 4, relativePad * 3f);
        interfaceSizeField.setPosition(
            categoryStartX + interfaceSizeLbl.getWidth() + relativePad * 2,
            curPosY
        );

        // fps
        fpsBox = createCheckBox(
            "show fps", Color.WHITE, relativeFontSize
        );
        fpsBox.setChecked(gameConfig.showFps);

        curPosY -= relativePad + fpsBox.getHeight();
        fpsBox.setPosition(startPosX, curPosY);

        // version
        versionBox = createCheckBox(
            "show version", Color.WHITE, relativeFontSize
        );
        versionBox.setChecked(gameConfig.showVersion);

        curPosY -= relativePad + versionBox.getHeight();
        versionBox.setPosition(startPosX, curPosY);

        // V
        VBox = createCheckBox(
            "show V vectors", Color.WHITE, relativeFontSize
        );
        VBox.setChecked(gameConfig.showVVectors);

        curPosY -= relativePad + VBox.getHeight();
        VBox.setPosition(startPosX, curPosY);

        // pos
        posBox = createCheckBox(
            "show positions", Color.WHITE, relativeFontSize
        );
        posBox.setChecked(gameConfig.showPositions);

        curPosY -= relativePad + posBox.getHeight();
        posBox.setPosition(startPosX, curPosY);

        // is debug UI
        debugUiBox = createCheckBox(
            "debug UI", Color.WHITE, relativeFontSize
        );
        debugUiBox.setChecked(GameConfig.isDebugUi);

        curPosY -= relativePad + debugUiBox.getHeight() + relativePad * 5;
        debugUiBox.setPosition(startPosX, curPosY);

        /* adding */
        rootGroup.addActor(interfaceSizeLbl);
        rootGroup.addActor(interfaceSizeField);
        rootGroup.addActor(fpsBox);
        rootGroup.addActor(versionBox);
        rootGroup.addActor(VBox);
        rootGroup.addActor(posBox);

        rootGroup.addActor(debugUiBox);
    }

    public void applySettings() {
        gameConfig.interfaceSize = Float.parseFloat(interfaceSizeField.getText());
        gameConfig.showFps = fpsBox.isChecked();
        gameConfig.showVersion = versionBox.isChecked();
        gameConfig.showVVectors = VBox.isChecked();
        gameConfig.showPositions = posBox.isChecked();
        applyConfig();

        GameConfig.isDebugUi = debugUiBox.isChecked();
    }

    public void resetSettings() {
        gameConfig = new GameConfig();
        ConfigManager.addConfig("game config", gameConfig);
        applyConfig();
    }

    public void applyConfig() {
        ConfigManager.save("game config");
    }
}
