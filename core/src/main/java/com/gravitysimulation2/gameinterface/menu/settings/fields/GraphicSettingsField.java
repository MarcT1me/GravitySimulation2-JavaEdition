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

    CheckBox vBox;
    CheckBox vModBox;
    CheckBox posBox;
    CheckBox showTrajectoryBox;
    CheckBox showDirectionBox;

    Slider trajectoryLenSlider;
    Label trajectoryLenValueLbl;
    Slider trajectoryIntervalSlider;
    Label trajectoryIntervalValueLbl;

    Slider vScaleSlider;
    Label vScaleValueLbl;

    Slider dirLineSlider;
    Label dirLineValueLbl;

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
        vBox = createCheckBox(
            "show V vectors", Color.WHITE, relativeFontSize
        );
        vBox.setChecked(graphicConfig.showVVectors);

        curPosY -= relativePad + vBox.getHeight();
        vBox.setPosition(startPosX, curPosY);

        // V scale
        Label vScaleLbl = createLabel("V vectors scale ", Color.WHITE, relativeFontSize);
        curPosY -= relativePad + vScaleLbl.getHeight();
        vScaleLbl.setPosition(startPosX + relativePad, curPosY);

        vScaleSlider = createSlider(0.5f, 20f, 0.5f, false, Color.GRAY);
        vScaleSlider.setSize(vScaleSlider.getPrefWidth(), relativePad * 2.5f);
        vScaleSlider.setValue(graphicConfig.vVectorsScale);
        vScaleSlider.setPosition(
            categoryStartX + vScaleLbl.getWidth() + relativePad * 3,
            curPosY
        );

        vScaleValueLbl = createLabel("" + vScaleSlider.getValue(), Color.WHITE, relativeFontSize);
        vScaleValueLbl.setPosition(
            startPosX + vScaleLbl.getWidth() + vScaleSlider.getWidth() + relativePad * 6,
            curPosY
        );

        // V module
        vModBox = createCheckBox(
            "show V Modules", Color.WHITE, relativeFontSize
        );
        vModBox.setChecked(graphicConfig.showVMods);

        curPosY -= relativePad + vModBox.getHeight();
        vModBox.setPosition(startPosX, curPosY);

        // pos
        posBox = createCheckBox(
            "show positions", Color.WHITE, relativeFontSize
        );
        posBox.setChecked(graphicConfig.showPositions);

        curPosY -= relativePad * 3 + posBox.getHeight();
        posBox.setPosition(startPosX, curPosY);

        // direction line
        showDirectionBox = createCheckBox("render direction line", Color.WHITE, relativeFontSize);
        showDirectionBox.setChecked(graphicConfig.showDirectionLine);
        curPosY -= relativePad * 3 + showDirectionBox.getHeight();
        showDirectionBox.setPosition(categoryStartX + relativePad, curPosY);

        // direction line alpha
        Label dirLineLbl = createLabel("direction line alpha ", Color.WHITE, relativeFontSize);
        curPosY -= relativePad + dirLineLbl.getHeight();
        dirLineLbl.setPosition(startPosX + relativePad, curPosY);

        dirLineSlider = createSlider(0.01f, 1f, 0.01f, false, Color.GRAY);
        dirLineSlider.setSize(dirLineSlider.getPrefWidth(), relativePad * 2.5f);
        dirLineSlider.setValue(graphicConfig.dirLineAlpha);
        dirLineSlider.setPosition(
            categoryStartX + dirLineLbl.getWidth() + relativePad * 3,
            curPosY
        );

        dirLineValueLbl = createLabel("" + dirLineSlider.getValue(), Color.WHITE, relativeFontSize);
        dirLineValueLbl.setPosition(
            startPosX + dirLineLbl.getWidth() + dirLineSlider.getWidth() + relativePad * 6,
            curPosY
        );

        // render trajectory
        showTrajectoryBox = createCheckBox("render trajectory", Color.WHITE, relativeFontSize);
        showTrajectoryBox.setChecked(graphicConfig.showTrajectory);
        curPosY -= relativePad * 3 + showTrajectoryBox.getHeight();
        showTrajectoryBox.setPosition(categoryStartX + relativePad, curPosY);

        // trajectory len
        Label trajectoryLenLbl = createLabel("trajectory len ", Color.WHITE, relativeFontSize);
        curPosY -= relativePad + trajectoryLenLbl.getHeight();
        trajectoryLenLbl.setPosition(startPosX + relativePad, curPosY);

        trajectoryLenSlider = createSlider(50f, 5000f, 1f, false, Color.GRAY);
        trajectoryLenSlider.setSize(trajectoryLenSlider.getPrefWidth(), relativePad * 2.5f);
        trajectoryLenSlider.setValue(graphicConfig.trajectoryLen);
        trajectoryLenSlider.setPosition(
            categoryStartX + trajectoryLenLbl.getWidth() + relativePad * 3,
            curPosY
        );

        trajectoryLenValueLbl = createLabel("" + trajectoryLenSlider.getValue(), Color.WHITE, relativeFontSize);
        trajectoryLenValueLbl.setPosition(
            startPosX + trajectoryLenLbl.getWidth() + trajectoryLenSlider.getWidth() + relativePad * 6,
            curPosY
        );

        // trajectory interval
        Label trajectoryIntervalLbl = createLabel("trajectory interval ", Color.WHITE, relativeFontSize);
        curPosY -= relativePad + trajectoryIntervalLbl.getHeight();
        trajectoryIntervalLbl.setPosition(startPosX + relativePad, curPosY);

        trajectoryIntervalSlider = createSlider(0.001f, 1.0f, 0.001f, false, Color.GRAY);
        trajectoryIntervalSlider.setSize(trajectoryIntervalSlider.getPrefWidth(), relativePad * 2.5f);
        trajectoryIntervalSlider.setValue(graphicConfig.trajectoryInterval);
        trajectoryIntervalSlider.setPosition(
            categoryStartX + trajectoryIntervalLbl.getWidth() + relativePad * 3,
            curPosY
        );

        trajectoryIntervalValueLbl = createLabel("" + trajectoryIntervalSlider.getValue(), Color.WHITE, relativeFontSize);
        trajectoryIntervalValueLbl.setPosition(
            startPosX + trajectoryIntervalLbl.getWidth() + trajectoryIntervalSlider.getWidth() + relativePad * 6,
            curPosY
        );

        /* adding */
        // V
        rootGroup.addActor(vBox);

        rootGroup.addActor(vScaleLbl);
        rootGroup.addActor(vScaleSlider);
        rootGroup.addActor(vScaleValueLbl);

        rootGroup.addActor(vModBox);

        // pos
        rootGroup.addActor(posBox);

        // dir line
        rootGroup.addActor(showDirectionBox);

        rootGroup.addActor(dirLineLbl);
        rootGroup.addActor(dirLineSlider);
        rootGroup.addActor(dirLineValueLbl);

        // trajectory
        rootGroup.addActor(showTrajectoryBox);

        rootGroup.addActor(trajectoryLenLbl);
        rootGroup.addActor(trajectoryLenSlider);
        rootGroup.addActor(trajectoryLenValueLbl);

        rootGroup.addActor(trajectoryIntervalLbl);
        rootGroup.addActor(trajectoryIntervalSlider);
        rootGroup.addActor(trajectoryIntervalValueLbl);
    }

    @Override
    public void renderUiElements() {
        // c scale
        vScaleSlider.setDisabled(!vBox.isChecked());

        vScaleValueLbl.setText(String.format("%.1f", vScaleSlider.getValue()));

        // trajectory
        trajectoryLenSlider.setDisabled(!showTrajectoryBox.isChecked());
        trajectoryIntervalSlider.setDisabled(!showTrajectoryBox.isChecked());

        trajectoryLenValueLbl.setText("" + (int) trajectoryLenSlider.getValue());
        trajectoryIntervalValueLbl.setText(String.format("%.3f", trajectoryIntervalSlider.getValue()));

        // dir line
        dirLineSlider.setDisabled(!showDirectionBox.isChecked());

        dirLineValueLbl.setText(String.format("%.2f", dirLineSlider.getValue()));
    }

    public void applySettings() {
        graphicConfig.showVVectors = vBox.isChecked();
        graphicConfig.showVMods = vModBox.isChecked();
        graphicConfig.vVectorsScale = vScaleSlider.getValue();

        graphicConfig.showPositions = posBox.isChecked();

        graphicConfig.showTrajectory = showTrajectoryBox.isChecked();
        graphicConfig.trajectoryLen = (int) trajectoryLenSlider.getValue();
        graphicConfig.trajectoryInterval = trajectoryIntervalSlider.getValue();

        graphicConfig.showDirectionLine = showDirectionBox.isChecked();
        graphicConfig.dirLineAlpha = dirLineSlider.getValue();
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
