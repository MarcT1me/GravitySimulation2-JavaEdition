package com.gravitysimulation2.gameinterface.menu.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gravitysimulation2.GravitySimulation2;
import com.gravitysimulation2.gameinterface.menu.MenuObject;
import com.gravitysimulation2.gameinterface.menu.settings.fields.GameMenuField;
import com.gravitysimulation2.gameinterface.menu.settings.fields.GraphicsMenuField;

public class SettingsMenu extends MenuObject {
    GameMenuField gameSettings;
    GraphicsMenuField graphicSettings;

    int currentField = 0;

    @Override
    protected void setupUI() {
        float relativeButtonFontSize = getRelativeScreenHeightScalar(2f);
        float relativeButtonSizeX = getRelativeScreenWidthScalar(50f);
        float relativeButtonSizeY = getRelativeScreenHeightScalar(40f);
        float relativePad = getRelativeScreenHeightScalar(10f);
        float categoryStartX = relativePad + relativeButtonSizeX * 3f + relativePad * 4f;
        float relativeOpenButtonSizeX = categoryStartX - relativePad * 2f;
        float relativeOpenButtonSizeY = getRelativeScreenHeightScalar(60f);

        // bg actor
        SettingsMenuBackground bgActor = new SettingsMenuBackground();
        bgActor.startX = categoryStartX;

        // graphics group
        gameSettings = new GameMenuField(categoryStartX);
        gameSettings.updateRootGroup();
        gameSettings.hide();

        TextButton openGameSettingsBtn = new TextButton("Game", skin);

        openGameSettingsBtn.getLabel().setFontScale(relativeButtonFontSize);
        openGameSettingsBtn.setPosition(relativePad,
            Gdx.graphics.getHeight() - relativeOpenButtonSizeY - relativePad
        );
        openGameSettingsBtn.setSize(relativeOpenButtonSizeX, relativeOpenButtonSizeY);
        openGameSettingsBtn.addListener(
            new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gameSettings.show();
                    graphicSettings.hide();
                    currentField = 1;
                }
            }
        );

        // graphics group
        graphicSettings = new GraphicsMenuField(categoryStartX);
        graphicSettings.updateRootGroup();
        graphicSettings.hide();

        TextButton openGraphicSettings = new TextButton("Graphics", skin);

        openGraphicSettings.getLabel().setFontScale(relativeButtonFontSize);
        openGraphicSettings.setPosition(relativePad,
            Gdx.graphics.getHeight() - relativeOpenButtonSizeY * 2f - relativePad * 2f
        );
        openGraphicSettings.setSize(relativeOpenButtonSizeX, relativeOpenButtonSizeY);
        openGraphicSettings.addListener(
            new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gameSettings.hide();
                    graphicSettings.show();
                    currentField = 2;
                }
            }
        );

        // apply
        TextButton applyButton = new TextButton("Apply", skin);

        applyButton.getLabel().setFontScale(relativeButtonFontSize);
        applyButton.setPosition(relativePad, relativePad);
        applyButton.setSize(relativeButtonSizeX, relativeButtonSizeY);
        applyButton.addListener(
            new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    switch (currentField) {
                        case 1:
                            gameSettings.applySettings();
                            break;
                        case 2:
                            graphicSettings.applySettings();
                            break;
                        default:
                            return;
                    }
                    updateRootGroup();
                }
            }
        );

        // reset
        TextButton resetButton = new TextButton("reset", skin);

        resetButton.getLabel().setFontScale(relativeButtonFontSize);
        resetButton.setPosition(relativePad + relativeButtonSizeX + relativePad, relativePad);
        resetButton.setSize(relativeButtonSizeX, relativeButtonSizeY);
        resetButton.addListener(
            new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    switch (currentField) {
                        case 0:
                            gameSettings.resetSettings();
                            graphicSettings.resetSettings();
                        case 1:
                            gameSettings.resetSettings();
                            break;
                        case 2:
                            graphicSettings.resetSettings();
                            break;
                        default:
                            return;
                    }
                    updateRootGroup();
                }
            }
        );

        // back
        TextButton backButton = new TextButton("Back", skin);

        backButton.getLabel().setFontScale(relativeButtonFontSize);
        backButton.setPosition(relativePad + relativeButtonSizeX * 2f + relativePad * 2f, relativePad);
        backButton.setSize(relativeButtonSizeX, relativeButtonSizeY);
        backButton.addListener(
            new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GravitySimulation2.instance.setScreen(GravitySimulation2.instance.menuMap.get("main"));
                }
            }
        );

        /* adding */
        rootGroup.addActor(bgActor);
        // game
        rootGroup.addActor(gameSettings.rootGroup);
        // graphics
        rootGroup.addActor(graphicSettings.rootGroup);

        // button field
        rootGroup.addActor(openGameSettingsBtn);
        rootGroup.addActor(openGraphicSettings);
        rootGroup.addActor(applyButton);
        rootGroup.addActor(resetButton);
        rootGroup.addActor(backButton);
    }

    @Override
    public void show() {
        super.show();
        currentField = 0;
    }
}
