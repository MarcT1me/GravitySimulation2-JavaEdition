package com.gravitysimulation2.gameinterface.menu.settings.fields;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

import com.gravitysimulation2.config.ConfigManager;
import com.gravitysimulation2.config.GraphicConfig;
import com.gravitysimulation2.gameinterface.menu.settings.SettingsMenuField;

public class GraphicSettingsField extends SettingsMenuField {
    GraphicConfig graphicConfig;

    CheckBox VBox;
    CheckBox posBox;
    Slider trajectoryLenSlider;
    Label trajectoryLenValueLbl;
    Slider trajectoryIntervalSlider;
    Label trajectoryIntervalValueLbl;

    public GraphicSettingsField(float categoryStartX) {
        super(categoryStartX);
        graphicConfig = (GraphicConfig) ConfigManager.getConfig("graphic config");
    }

    @Override
    public void setupUI() {
        float relativePad = getRelativeScreenHeightScalar(10f);
        float curPosY = Gdx.graphics.getHeight();
        float startPosX = categoryStartX + relativePad;

        float relativeFontSize = getRelativeScreenHeightScalar(2f);

        // V
        VBox = createCheckBox(
            "show V vectors", Color.WHITE, relativeFontSize
        );
        VBox.setChecked(graphicConfig.showVVectors);

        curPosY -= relativePad + VBox.getHeight();
        VBox.setPosition(startPosX, curPosY);

        // pos
        posBox = createCheckBox(
            "show positions", Color.WHITE, relativeFontSize
        );
        posBox.setChecked(graphicConfig.showPositions);

        curPosY -= relativePad + posBox.getHeight();
        posBox.setPosition(startPosX, curPosY);

        // trajectory len
        Label trajectoryLenLbl = createLabel("trajectory len:", Color.WHITE, relativeFontSize);
        curPosY -= relativePad + trajectoryLenLbl.getHeight();
        trajectoryLenLbl.setPosition(startPosX + relativePad, curPosY);

        trajectoryLenSlider = createSlider(50f, 500f, 1f, false, Color.GRAY);
        trajectoryLenSlider.setValue(graphicConfig.trajectoryLen);
        trajectoryLenSlider.setPosition(
            categoryStartX + trajectoryLenLbl.getWidth() + relativePad * 3,
            curPosY
        );

        trajectoryLenValueLbl = createLabel("len: " + trajectoryLenSlider.getValue(), Color.WHITE, relativeFontSize);
        trajectoryLenValueLbl.setPosition(
            startPosX + trajectoryLenLbl.getWidth() + trajectoryLenSlider.getWidth() + relativePad * 6,
            curPosY
        );

        // trajectory interval
        Label trajectoryIntervalLbl = createLabel("trajectory interval:", Color.WHITE, relativeFontSize);
        curPosY -= relativePad + trajectoryIntervalLbl.getHeight();
        trajectoryIntervalLbl.setPosition(startPosX + relativePad, curPosY);

        trajectoryIntervalSlider = createSlider(0.01f, 2f, 0.01f, false, Color.GRAY);
        trajectoryIntervalSlider.setValue(graphicConfig.trajectoryInterval);
        trajectoryIntervalSlider.setPosition(
            categoryStartX + trajectoryIntervalLbl.getWidth() + relativePad * 3,
            curPosY
        );

        trajectoryIntervalValueLbl = createLabel("interval: " + trajectoryIntervalSlider.getValue(), Color.WHITE, relativeFontSize);
        trajectoryIntervalValueLbl.setPosition(
            startPosX + trajectoryIntervalLbl.getWidth() + trajectoryIntervalSlider.getWidth() + relativePad * 6,
            curPosY
        );

        rootGroup.addActor(VBox);
        rootGroup.addActor(posBox);

        rootGroup.addActor(trajectoryLenLbl);
        rootGroup.addActor(trajectoryLenSlider);
        rootGroup.addActor(trajectoryLenValueLbl);

        rootGroup.addActor(trajectoryIntervalLbl);
        rootGroup.addActor(trajectoryIntervalSlider);
        rootGroup.addActor(trajectoryIntervalValueLbl);
    }

    @Override
    public void renderUiElements() {
        trajectoryLenValueLbl.setText("len: " + (int) trajectoryLenSlider.getValue());
        trajectoryIntervalValueLbl.setText(String.format("interval: %.2f", trajectoryIntervalSlider.getValue()));
    }

    public void applySettings() {
        graphicConfig.showVVectors = VBox.isChecked();
        graphicConfig.showPositions = posBox.isChecked();
        graphicConfig.trajectoryLen = (int) trajectoryLenSlider.getValue();
        graphicConfig.trajectoryInterval = trajectoryIntervalSlider.getValue();
        applyConfig();
    }

    public void resetSettings() {
        graphicConfig = graphicConfig.getDefaultConfig();
        ConfigManager.addConfig("graphic config", graphicConfig);
        applyConfig();
    }

    public void applyConfig() {
        ConfigManager.save("graphic config");
    }

    @Override
    public void dispose() {
    }
}
