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
import com.gravitysimulation2.objects.GameScene;
import com.gravitysimulation2.screen.ScreenObject;

public class SceneScreen extends ScreenObject {
    final PauseMenu menu;
    final LoadMenu loadMenu;
    final SettingsMenu settingsMenu;

    float relativePad;
    float interfaceStartX;
    CheckBox extraSlowBtn;
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
                float curSpeed = GameScene.speeds.get("simulation");
                if (curSpeed < 1048576)
                    GameScene.speeds.put("simulation", curSpeed * 2f);
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
                float curSpeed = GameScene.speeds.get("simulation");
                if (curSpeed > 0.0625)
                    GameScene.speeds.put("simulation", curSpeed / 2f);
            }
        });
        // lbl
        simulationSpeedLbl = createLabel(
            "simulation speed: xxxxxxx.x" + GameScene.speeds.get("simulation"), Color.WHITE, relativeFontSize
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
                    GameScene.speeds.put("simulation", lastSimSpeed);
                } else {
                    lastSimSpeed = GameScene.speeds.get("simulation");
                    GameScene.speeds.put("simulation", 0.0000000001f);
                }
            }
        });

        // camera zoom
        cameraZoomLbl = createLabel("camera zoom: xxxxx", Color.WHITE, relativeFontSize);

        // background
        rootGroup.addActor(new SceneScreenBackground(
            new Vector2(
                interfaceStartX,
                relativeButtonSize + relativePad * 4 +
                    cameraZoomLbl.getHeight() + extraSlowBtn.getHeight()
            )
        ));

        // adding
        rootGroup.addActor(simulationSpeedUpBtn);
        rootGroup.addActor(simulationSpeedLbl);
        rootGroup.addActor(simulationSpeedDownBtn);

        rootGroup.addActor(cameraZoomLbl);
        rootGroup.addActor(extraSlowBtn);

        // menu
        rootGroup.addActor(menu.updateRootGroup().rootGroup);
        rootGroup.addActor(loadMenu.updateRootGroup().rootGroup);
        rootGroup.addActor(settingsMenu.updateRootGroup().rootGroup);

        // fps and version
        super.setupUI();
    }

    @Override
    public void renderUiElements() {
        super.renderUiElements();
        settingsMenu.renderUiElements();

        simulationSpeedLbl.setText("simulation speed: " + GameScene.speeds.get("simulation"));
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
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            menu.show();
        }
        super.render(delta);
    }
}
