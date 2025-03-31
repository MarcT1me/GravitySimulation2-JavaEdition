package com.gravitysimulation2.gameinterface.menu.settings.fields;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

import com.gravitysimulation2.config.ConfigManager;
import com.gravitysimulation2.config.GameConfig;
import com.gravitysimulation2.gameinterface.menu.settings.SettingsMenuField;

public class GameSettingsField extends SettingsMenuField {
    GameConfig gameConfig;

    Slider interfaceSizeSlider;
    Label interfaceSizeSliderValueLbl;
    CheckBox fpsBox;
    CheckBox versionBox;

    Slider cameraMoveSpeedSlider;
    Label cameraMoveSpeedValueLbl;
    Slider cameraZoomSpeedSlider;
    Label cameraZoomSpeedValueLbl;
    Slider mouseSensitivitySlider;
    Label mouseSensitivityValueLbl;

    CheckBox debugUiBox;

    public GameSettingsField(float categoryStartX) {
        super(categoryStartX);
        gameConfig = (GameConfig) ConfigManager.getConfig("game config");
    }

    @Override
    public void setupUI() {
        float relativePad = getRelativeScreenHeightScalar(10f);
        float curPosY = Gdx.graphics.getHeight();
        float startPosX = categoryStartX + relativePad;

        float relativeFontSize = getRelativeScreenHeightScalar(2f);

        // Interface size
        Label interfaceSizeLbl = createLabel("Interface size ", Color.WHITE, relativeFontSize);
        curPosY -= relativePad + interfaceSizeLbl.getHeight();
        interfaceSizeLbl.setPosition(startPosX + relativePad, curPosY);

        interfaceSizeSlider = createSlider(0.35f, 1.25f, 0.01f, false, Color.GRAY);
        interfaceSizeSlider.setSize(interfaceSizeSlider.getPrefWidth(), relativePad * 2.5f);
        interfaceSizeSlider.setValue(gameConfig.interfaceSize);
        interfaceSizeSlider.setPosition(
            categoryStartX + interfaceSizeLbl.getWidth() + relativePad * 3,
            curPosY
        );

        interfaceSizeSliderValueLbl = createLabel("" + interfaceSizeSlider.getValue(), Color.WHITE, relativeFontSize);
        interfaceSizeSliderValueLbl.setPosition(
            startPosX + interfaceSizeLbl.getWidth() + interfaceSizeSlider.getWidth() + relativePad * 6,
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

        // camera move
        Label cameraMoveLbl = createLabel("camera move speed ", Color.WHITE, relativeFontSize);
        curPosY -= relativePad * 3 + cameraMoveLbl.getHeight();
        cameraMoveLbl.setPosition(startPosX + relativePad, curPosY);

        cameraMoveSpeedSlider = createSlider(1f, 1000f, 1f, false, Color.GRAY);
        cameraMoveSpeedSlider.setSize(cameraMoveSpeedSlider.getPrefWidth(), relativePad * 2.5f);
        cameraMoveSpeedSlider.setValue(gameConfig.cameraMoveSpeed);
        cameraMoveSpeedSlider.setPosition(
            categoryStartX + cameraMoveLbl.getWidth() + relativePad * 3,
            curPosY
        );

        cameraMoveSpeedValueLbl = createLabel("" + cameraMoveSpeedSlider.getValue(), Color.WHITE, relativeFontSize);
        cameraMoveSpeedValueLbl.setPosition(
            startPosX + cameraMoveLbl.getWidth() + cameraMoveSpeedSlider.getWidth() + relativePad * 6,
            curPosY
        );

        // camera zoom
        Label cameraZoomLbl = createLabel("camera zoom speed ", Color.WHITE, relativeFontSize);
        curPosY -= relativePad + cameraZoomLbl.getHeight();
        cameraZoomLbl.setPosition(startPosX + relativePad, curPosY);

        cameraZoomSpeedSlider = createSlider(0.01f, 3f, 0.01f, false, Color.GRAY);
        cameraZoomSpeedSlider.setSize(cameraZoomSpeedSlider.getPrefWidth(), relativePad * 2.5f);
        cameraZoomSpeedSlider.setValue(gameConfig.cameraZoomSpeed);
        cameraZoomSpeedSlider.setPosition(
            categoryStartX + cameraZoomLbl.getWidth() + relativePad * 3,
            curPosY
        );

        cameraZoomSpeedValueLbl = createLabel("" + cameraZoomSpeedSlider.getValue(), Color.WHITE, relativeFontSize);
        cameraZoomSpeedValueLbl.setPosition(
            startPosX + cameraZoomLbl.getWidth() + cameraZoomSpeedSlider.getWidth() + relativePad * 6,
            curPosY
        );

        // mouse sensitivity
        Label mouseSensitivityLbl = createLabel("Mouse sensitivity ", Color.WHITE, relativeFontSize);
        curPosY -= relativePad + mouseSensitivityLbl.getHeight();
        mouseSensitivityLbl.setPosition(startPosX + relativePad, curPosY);

        mouseSensitivitySlider = createSlider(0.01f, 5f, 0.01f, false, Color.GRAY);
        mouseSensitivitySlider.setSize(mouseSensitivitySlider.getPrefWidth(), relativePad * 2.5f);
        mouseSensitivitySlider.setValue(gameConfig.mouseSensitivity);
        mouseSensitivitySlider.setPosition(
            categoryStartX + mouseSensitivityLbl.getWidth() + relativePad * 3,
            curPosY
        );

        mouseSensitivityValueLbl = createLabel("" + mouseSensitivitySlider.getValue(), Color.WHITE, relativeFontSize);
        mouseSensitivityValueLbl.setPosition(
            startPosX + mouseSensitivityLbl.getWidth() + mouseSensitivitySlider.getWidth() + relativePad * 6,
            curPosY
        );

        // is debug UI
        debugUiBox = createCheckBox(
            "debug UI", Color.WHITE, relativeFontSize
        );
        debugUiBox.setChecked(GameConfig.isDebugUi);

        curPosY -= relativePad + debugUiBox.getHeight() + relativePad * 5;
        debugUiBox.setPosition(startPosX, curPosY);

        /* adding */
        rootGroup.addActor(interfaceSizeLbl);
        rootGroup.addActor(interfaceSizeSlider);
        rootGroup.addActor(interfaceSizeSliderValueLbl);
        rootGroup.addActor(fpsBox);
        rootGroup.addActor(versionBox);

        rootGroup.addActor(cameraMoveLbl);
        rootGroup.addActor(cameraMoveSpeedSlider);
        rootGroup.addActor(cameraMoveSpeedValueLbl);

        rootGroup.addActor(cameraZoomLbl);
        rootGroup.addActor(cameraZoomSpeedSlider);
        rootGroup.addActor(cameraZoomSpeedValueLbl);

        rootGroup.addActor(mouseSensitivityLbl);
        rootGroup.addActor(mouseSensitivitySlider);
        rootGroup.addActor(mouseSensitivityValueLbl);

        rootGroup.addActor(debugUiBox);
    }

    @Override
    public void renderUiElements() {
        interfaceSizeSliderValueLbl.setText(String.format("%.2f", interfaceSizeSlider.getValue()));

        cameraMoveSpeedValueLbl.setText(String.format("%.2f", cameraZoomSpeedSlider.getValue()));
        cameraZoomSpeedValueLbl.setText(String.format("%.2f", cameraZoomSpeedSlider.getValue()));
        mouseSensitivityValueLbl.setText(String.format("%.2f", mouseSensitivitySlider.getValue()));
    }

    public void applySettings() {
        gameConfig.interfaceSize = interfaceSizeSlider.getValue();
        gameConfig.showFps = fpsBox.isChecked();
        gameConfig.showVersion = versionBox.isChecked();

        gameConfig.cameraMoveSpeed = cameraMoveSpeedSlider.getValue();
        gameConfig.cameraZoomSpeed = cameraZoomSpeedSlider.getValue();
        gameConfig.mouseSensitivity = mouseSensitivitySlider.getValue();
        applyConfig();

        GameConfig.isDebugUi = debugUiBox.isChecked();
    }

    public void resetSettings() {
        gameConfig = gameConfig.getDefaultConfig();
        ConfigManager.addConfig("game config", gameConfig);
        applyConfig();
    }

    public void applyConfig() {
        ConfigManager.save("game config");
    }

    @Override
    public void dispose() {
    }
}
