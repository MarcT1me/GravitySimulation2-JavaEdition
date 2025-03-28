package com.gravitysimulation2.screen.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gravitysimulation2.GravitySimulation2;
import com.gravitysimulation2.gameinterface.menu.load.LoadMenu;
import com.gravitysimulation2.gameinterface.menu.pause.PauseMenu;
import com.gravitysimulation2.gameinterface.menu.settings.SettingsMenu;
import com.gravitysimulation2.screen.ScreenObject;

public class SceneScreen extends ScreenObject {
    final PauseMenu menu;
    final LoadMenu loadMenu;
    final SettingsMenu settingsMenu;

    float relativePad;
    float interfaceStartX;
    CheckBox extraSlowBtn;
    CheckBox pauseBtn;
    Label cameraZoomLbl;
    Label simulationSpeedLbl;
    TextButton simulationSpeedDownBtn;
    TextButton simulationSpeedUpBtn;

    public SceneScreen() {
        super();
        menu = new PauseMenu();
        loadMenu = (LoadMenu) GravitySimulation2.getGameMenu("load");
        settingsMenu = (SettingsMenu) GravitySimulation2.getGameMenu("settings");
    }

    @Override
    public void setupUI() {
        // scene
        if (scene != null) rootGroup.addActor(scene.updateRootGroup().rootGroup);

        relativePad = getRelativeScreenHeightScalar(10f);
        float relativeFontSize = getRelativeScreenHeightScalar(2f);
        float relativeButtonSize = getRelativeScreenHeightScalar(27.5f);

        // game interface
        // up
        simulationSpeedUpBtn = createTextButton(
            "+", Color.WHITE, relativeFontSize * 1.75f,
            relativeButtonSize, relativeButtonSize
        );
        simulationSpeedUpBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (scene.saveConfig.simulationSpeed < Math.pow(2, scene.saveConfig.maxSimulationSpeedMod))
                    scene.saveConfig.simulationSpeed *= 2;
            }
        });
        // down
        simulationSpeedDownBtn = createTextButton(
            "-", Color.WHITE, relativeFontSize * 1.75f,
            relativeButtonSize, relativeButtonSize
        );
        simulationSpeedDownBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (scene.saveConfig.simulationSpeed > 0.0625)
                    scene.saveConfig.simulationSpeed /= 2;
            }
        });
        // lbl
        simulationSpeedLbl = createLabel(
            "simulation speed: xxxxxxx.x" + scene.saveConfig.simulationSpeed, Color.WHITE, relativeFontSize
        );
        interfaceStartX = relativeButtonSize * 2 + relativePad * 2 + simulationSpeedLbl.getWidth() + relativePad * 2;

        // extra slow button
        extraSlowBtn = createCheckBox("Extra slow", Color.WHITE, relativeFontSize);
        extraSlowBtn.addListener(new ClickListener() {
            float lastSimSpeed;

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (!extraSlowBtn.isChecked()) {
                    scene.saveConfig.simulationSpeed = lastSimSpeed;
                } else {
                    lastSimSpeed = scene.saveConfig.simulationSpeed;
                    scene.saveConfig.simulationSpeed =  0.0000000001f;
                }
            }
        });

        pauseBtn = createCheckBox("Pause", Color.WHITE, relativeFontSize);
        pauseBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                scene.paused = pauseBtn.isChecked();
            }
        });

        // camera zoom
        cameraZoomLbl = createLabel("camera zoom: xxxxx", Color.WHITE, relativeFontSize);

        // background
        rootGroup.addActor(new SceneScreenBackground(
            new Vector2(
                interfaceStartX,
                relativePad + relativeButtonSize + relativePad +
                    relativePad + extraSlowBtn.getHeight() +
                    relativePad + cameraZoomLbl.getHeight() +
                    relativePad + pauseBtn.getHeight()
            )
        ));

        // adding
        rootGroup.addActor(simulationSpeedUpBtn);
        rootGroup.addActor(simulationSpeedLbl);
        rootGroup.addActor(simulationSpeedDownBtn);

        rootGroup.addActor(extraSlowBtn);

        rootGroup.addActor(cameraZoomLbl);

        rootGroup.addActor(pauseBtn);

        // menu
        rootGroup.addActor(menu.updateRootGroup().rootGroup);
        rootGroup.addActor(loadMenu.updateRootGroup().rootGroup);
        rootGroup.addActor(settingsMenu.updateRootGroup().rootGroup);

        // fps and version
        super.setupUI();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            menu.show();
        }
        super.render(delta);
    }

    @Override
    public void renderUiElements() {
        super.renderUiElements();
        settingsMenu.renderUiElements();

        simulationSpeedLbl.setText("simulation speed: " + scene.saveConfig.simulationSpeed);
        simulationSpeedLbl.setSize(simulationSpeedLbl.getPrefWidth(), simulationSpeedLbl.getPrefHeight());

        cameraZoomLbl.setText("camera zoom: " + scene.camera.zoom);
        cameraZoomLbl.setSize(cameraZoomLbl.getPrefWidth(), cameraZoomLbl.getPrefHeight());

        float curPosX = Gdx.graphics.getWidth() - interfaceStartX + relativePad;
        float curPosY = relativePad;

        simulationSpeedUpBtn.setPosition(curPosX, curPosY);

        curPosX += relativePad + simulationSpeedUpBtn.getWidth();
        simulationSpeedDownBtn.setPosition(curPosX, curPosY);

        curPosX += relativePad + simulationSpeedDownBtn.getWidth();
        simulationSpeedLbl.setPosition(curPosX, curPosY);

        // extra slow speed btn
        curPosX = Gdx.graphics.getWidth() - interfaceStartX + relativePad;
        curPosY += simulationSpeedUpBtn.getHeight() + relativePad;

        extraSlowBtn.setPosition(curPosX, curPosY);

        // camera
        curPosX = Gdx.graphics.getWidth() - interfaceStartX + relativePad;
        curPosY += extraSlowBtn.getHeight() + relativePad;

        cameraZoomLbl.setPosition(curPosX, curPosY);

        // pause
        pauseBtn.setChecked(scene.paused);
        curPosY += cameraZoomLbl.getHeight() + relativePad;
        pauseBtn.setPosition(curPosX, curPosY);
    }
}
