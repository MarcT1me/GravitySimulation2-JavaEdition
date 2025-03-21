package com.gravitysimulation2.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gravitysimulation2.GravitySimulation2;

public class MainMenu extends MenuObject {
    @Override
    protected void setupUI() {
        float screenCenterX = Gdx.graphics.getWidth() / 2f;
        float labelStartPosY = Gdx.graphics.getHeight() - 200;

        // main
        Label mainLbl = new Label("Main", skin);
        rootGroup.addActor(mainLbl);

        mainLbl.setFontScale(3f);
        mainLbl.setColor(Color.WHITE);
        mainLbl.setSize(mainLbl.getPrefWidth(), mainLbl.getPrefHeight());
        mainLbl.setPosition(screenCenterX - mainLbl.getWidth() - 10, labelStartPosY);

        // menu
        Label menuLbl = new Label("Menu", skin);
        rootGroup.addActor(menuLbl);

        menuLbl.setFontScale(3f);
        menuLbl.setColor(Color.GREEN);
        menuLbl.setSize(menuLbl.getPrefWidth(), menuLbl.getPrefHeight());
        menuLbl.setPosition(screenCenterX + 10, labelStartPosY);

        float buttonStartPosX = labelStartPosY - menuLbl.getPrefHeight();

        // load
        TextButton loadBtn = new TextButton("Load", skin);
        rootGroup.addActor(loadBtn);

        loadBtn.setSize(60, 30);
        loadBtn.setPosition(screenCenterX - loadBtn.getWidth() / 2f, buttonStartPosX - 10);
        loadBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GravitySimulation2.instance.setScreen(GravitySimulation2.instance.menuMap.get("load"));
            }
        });

        // settings
        TextButton settingsBtn = new TextButton("Settings", skin);
        rootGroup.addActor(settingsBtn);

        settingsBtn.setSize(60, 30);
        settingsBtn.setPosition(screenCenterX - settingsBtn.getWidth() / 2f, buttonStartPosX - 50);
        settingsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GravitySimulation2.instance.setScreen(GravitySimulation2.instance.menuMap.get("settings"));
            }
        });

        // exit
        TextButton exitBtn = new TextButton("Exit", skin);
        rootGroup.addActor(exitBtn);

        exitBtn.setSize(60, 30);
        exitBtn.setPosition(screenCenterX - exitBtn.getWidth() / 2f, buttonStartPosX - 90);
        exitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }
}
