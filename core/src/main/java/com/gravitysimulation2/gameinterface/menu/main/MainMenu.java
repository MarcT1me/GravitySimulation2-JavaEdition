package com.gravitysimulation2.gameinterface.menu.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector4;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gravitysimulation2.GravitySimulation2;
import com.gravitysimulation2.gameinterface.BackgroundActor;
import com.gravitysimulation2.gameinterface.menu.MenuObject;
import com.gravitysimulation2.gameinterface.menu.settings.SettingsMenu;

public class MainMenu extends MenuObject {
    Texture gsTexture;

    @Override
    public void setupUI() {
        float screenCenterX = Gdx.graphics.getWidth() / 2f;
        float screenCenterY = Gdx.graphics.getHeight() / 2f;

        float relativePad = getRelativeScreenHeightScalar(10f);

        /* labels */
        float relativeFontSize = getRelativeScreenHeightScalar(4f);
        Label mainLbl = createLabel("Main", Color.WHITE, relativeFontSize);
        mainLbl.setPosition(screenCenterX - mainLbl.getWidth() - relativePad, screenCenterY);

        Label menuLbl = createLabel("Menu", Color.GREEN, relativeFontSize);
        menuLbl.setPosition(screenCenterX + relativePad, screenCenterY);

        /* ico */
        float relativeGsImgRes = getRelativeScreenHeightScalar(128f);

        gsTexture = new Texture(Gdx.files.internal("GS2ico32.png"));
        Image gsImg = new Image(gsTexture);

        gsImg.setSize(relativeGsImgRes, relativeGsImgRes);
        gsImg.setPosition(
            screenCenterX - gsImg.getWidth() / 2f,
            screenCenterY + menuLbl.getHeight() + relativePad * 5
        );

        /* Buttons */
        float buttonStartPosX = screenCenterY - menuLbl.getPrefHeight();

        float relativeButtonFontSize = relativeFontSize / 2f;
        float relativeButtonSizeX = getRelativeScreenWidthScalar(60f);
        float relativeButtonSizeY = getRelativeScreenHeightScalar(40f);

        // load
        TextButton loadBtn = createTextButton("Load", Color.WHITE, relativeButtonFontSize,
            relativeButtonSizeX, relativeButtonSizeY
        );
        loadBtn.setPosition(
            screenCenterX - loadBtn.getWidth() / 2f,
            buttonStartPosX - relativePad
        );
        loadBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                GravitySimulation2.getGameMenu("load").show();
            }
        });

        // settings
        TextButton settingsBtn = createTextButton("Settings", Color.WHITE, relativeButtonFontSize,
            relativeButtonSizeX, relativeButtonSizeY
        );
        settingsBtn.setPosition(
            screenCenterX - settingsBtn.getWidth() / 2f,
            buttonStartPosX - relativeButtonSizeY - relativePad * 2f
        );
        settingsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                SettingsMenu settingsMenu = (SettingsMenu) GravitySimulation2.getGameMenu("settings");
                settingsMenu.setPreviousMenu(MainMenu.this);
                settingsMenu.show();
            }
        });

        // exit
        TextButton exitBtn = createTextButton("Exit", Color.WHITE, relativeButtonFontSize,
            relativeButtonSizeX, relativeButtonSizeY
        );
        exitBtn.setPosition(
            screenCenterX - exitBtn.getWidth() / 2f,
            buttonStartPosX - relativeButtonSizeY * 2f - relativePad * 3f
        );
        exitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        /* adding in root */
        rootGroup.addActor(gsImg);

        // bg (need initialized labels and buttons)
        BackgroundActor bgActor = new BackgroundActor() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                super.draw(batch, parentAlpha);
                beginShape(batch);

                drawRect(
                    screenCenterX - menuLbl.getPrefWidth() - relativePad * 2,
                    screenCenterY + menuLbl.getHeight() + relativePad * 2,
                    menuLbl.getPrefWidth() * 2 + relativePad * 4,
                    -menuLbl.getHeight() - relativeButtonSizeY * 3f - relativePad * 9f,
                    new Vector4(Color.DARK_GRAY.r, Color.DARK_GRAY.g, Color.DARK_GRAY.b, 0.5f)
                );

                endShape(batch);
            }
        };
        rootGroup.addActor(bgActor);

        rootGroup.addActor(loadBtn);
        rootGroup.addActor(settingsBtn);
        rootGroup.addActor(exitBtn);

        rootGroup.addActor(mainLbl);
        rootGroup.addActor(menuLbl);
    }

    @Override
    public void renderUiElements() {

    }

    @Override
    public void dispose() {
        gsTexture.dispose();
    }
}
