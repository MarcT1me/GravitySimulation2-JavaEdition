package com.gravitysimulation2.gameinterface.menu.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.gravitysimulation2.GravitySimulation2;
import com.gravitysimulation2.gameinterface.InterfaceObject;
import com.gravitysimulation2.gameinterface.menu.MenuObject;
import com.gravitysimulation2.gameinterface.menu.settings.fields.GameSettingsField;
import com.gravitysimulation2.gameinterface.menu.settings.fields.GraphicSettingsField;
import com.gravitysimulation2.gameinterface.menu.settings.fields.WindowSettingsField;

public class SettingsMenu extends MenuObject {
    GameSettingsField gameSettings;
    WindowSettingsField windowSettings;
    GraphicSettingsField graphicSettings;
    private InterfaceObject previousMenu;

    // settings fields
    final int NOT_CHOSEN_SETTINGS_FIELD = 0;
    final int GAME_SETTINGS_FIELD = 1;
    final int WINDOW_SETTINGS_FIELD = 2;
    final int GRAPHIC_SETTINGS_FIELD = 3;
    int currentField = NOT_CHOSEN_SETTINGS_FIELD;

    public void setPreviousMenu(InterfaceObject previousMenu) {
        this.previousMenu = previousMenu;
    }

    @Override
    public void setupUI() {
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

        // game group
        gameSettings = new GameSettingsField(categoryStartX);
        gameSettings.updateRootGroup();

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
                    windowSettings.hide();
                    graphicSettings.hide();
                    currentField = GAME_SETTINGS_FIELD;
                }
            }
        );

        // window group
        windowSettings = new WindowSettingsField(categoryStartX);
        windowSettings.updateRootGroup();

        TextButton openWindowSettingsBtn = new TextButton("Window", skin);

        openWindowSettingsBtn.getLabel().setFontScale(relativeButtonFontSize);
        openWindowSettingsBtn.setPosition(relativePad,
            Gdx.graphics.getHeight() - relativeOpenButtonSizeY * 2f - relativePad * 2f
        );
        openWindowSettingsBtn.setSize(relativeOpenButtonSizeX, relativeOpenButtonSizeY);
        openWindowSettingsBtn.addListener(
            new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gameSettings.hide();
                    windowSettings.show();
                    graphicSettings.hide();
                    currentField = WINDOW_SETTINGS_FIELD;
                }
            }
        );

        // graphic group
        graphicSettings = new GraphicSettingsField(categoryStartX);
        graphicSettings.updateRootGroup();

        TextButton openGraphicSettingsBtn = new TextButton("Graphics", skin);

        openGraphicSettingsBtn.getLabel().setFontScale(relativeButtonFontSize);
        openGraphicSettingsBtn.setPosition(relativePad,
            Gdx.graphics.getHeight() - relativeOpenButtonSizeY * 3f - relativePad * 3f
        );
        openGraphicSettingsBtn.setSize(relativeOpenButtonSizeX, relativeOpenButtonSizeY);
        openGraphicSettingsBtn.addListener(
            new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gameSettings.hide();
                    windowSettings.hide();
                    graphicSettings.show();
                    currentField = GRAPHIC_SETTINGS_FIELD;
                }
            }
        );

        // apply
        TextButton applyButton = new TextButton("Apply", skin);

        applyButton.getLabel().setFontScale(relativeButtonFontSize);
        applyButton.setPosition(relativePad, relativePad + 20);
        applyButton.setSize(relativeButtonSizeX, relativeButtonSizeY);
        applyButton.addListener(
            new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    SettingsMenuField field = getCurrentSettingsField();

                    if (field != null) {
                        field.applySettings();
                        updateAfterConfigChanging();
                    }
                }
            }
        );

        // reset
        TextButton resetButton = new TextButton("reset", skin);

        resetButton.getLabel().setFontScale(relativeButtonFontSize);
        resetButton.setPosition(relativePad + relativeButtonSizeX + relativePad, relativePad + 20);
        resetButton.setSize(relativeButtonSizeX, relativeButtonSizeY);
        resetButton.addListener(
            new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    SettingsMenuField field = getCurrentSettingsField();

                    if (field != null) {
                        field.resetSettings();
                    } else {
                        gameSettings.resetSettings();
                        windowSettings.resetSettings();
                        graphicSettings.resetSettings();
                    }
                    updateAfterConfigChanging();
                }
            }
        );

        // back
        TextButton backButton = new TextButton("Back", skin);

        backButton.getLabel().setFontScale(relativeButtonFontSize);
        backButton.setPosition(relativePad + relativeButtonSizeX * 2f + relativePad * 2f, relativePad + 20);
        backButton.setSize(relativeButtonSizeX, relativeButtonSizeY);
        backButton.addListener(
            new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    hide();
                    if (previousMenu != null) {
                        previousMenu.show();
                    }
                }
            }
        );

        /* adding */
        rootGroup.addActor(bgActor);
        // game
        rootGroup.addActor(gameSettings.rootGroup);
        rootGroup.addActor(openGameSettingsBtn);
        // window
        rootGroup.addActor(windowSettings.rootGroup);
        rootGroup.addActor(openWindowSettingsBtn);
        // graphics
        rootGroup.addActor(graphicSettings.rootGroup);
        rootGroup.addActor(openGraphicSettingsBtn);

        // button field
        rootGroup.addActor(applyButton);
        rootGroup.addActor(resetButton);
        rootGroup.addActor(backButton);
    }

    private void updateAfterConfigChanging() {
        GravitySimulation2.getCurrentGameScreen().applyConfigs();
        GravitySimulation2.getCurrentGameScreen().updateRootGroup();
    }

    private SettingsMenuField getCurrentSettingsField() {
        return switch (currentField) {
            case 1 -> gameSettings;
            case 2 -> windowSettings;
            case 3 -> graphicSettings;
            default -> null;
        };
    }

    @Override
    public void renderUiElements() {
        if (!isVisible()) return;
        gameSettings.renderUiElements();
        windowSettings.renderUiElements();
        graphicSettings.renderUiElements();
    }

    @Override
    public void show() {
        super.show();
        currentField = NOT_CHOSEN_SETTINGS_FIELD;
    }

    @Override
    public void dispose() {
    }
}
