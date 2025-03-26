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
        Label interfaceSizeLbl = createLabel("Interface size:", Color.WHITE, relativeFontSize);
        curPosY -= relativePad + interfaceSizeLbl.getHeight();
        interfaceSizeLbl.setPosition(startPosX + relativePad, curPosY);

        interfaceSizeSlider = createSlider(0.35f, 1.25f, 0.01f, false, Color.GRAY);
        interfaceSizeSlider.setSize(interfaceSizeLbl.getPrefWidth(), relativePad * 2.5f);
        interfaceSizeSlider.setValue(gameConfig.interfaceSize);
        interfaceSizeSlider.setPosition(
            categoryStartX + interfaceSizeLbl.getWidth() + relativePad * 3,
            curPosY
        );

        interfaceSizeSliderValueLbl = createLabel("size: " + interfaceSizeSlider.getValue(), Color.WHITE, relativeFontSize);
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

        rootGroup.addActor(debugUiBox);
    }

    @Override
    public void renderUiElements() {
        interfaceSizeSliderValueLbl.setText(String.format("interface size: %.2f", interfaceSizeSlider.getValue()));
    }

    public void applySettings() {
        gameConfig.interfaceSize = interfaceSizeSlider.getValue();
        gameConfig.showFps = fpsBox.isChecked();
        gameConfig.showVersion = versionBox.isChecked();
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
