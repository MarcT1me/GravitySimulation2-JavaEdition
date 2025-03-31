package com.gravitysimulation2.gameinterface.menu.pause;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector4;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.gravitysimulation2.GravitySimulation2;
import com.gravitysimulation2.gameinterface.BackgroundActor;
import com.gravitysimulation2.gameinterface.menu.MenuObject;
import com.gravitysimulation2.gameinterface.menu.settings.SettingsMenu;
import com.gravitysimulation2.objects.GameScene;
import com.gravitysimulation2.save.SaveConfig;

public class PauseMenu extends MenuObject {
    @Override
    public void setupUI() {
        float relativePad = getRelativeScreenHeightScalar(10f);

        float curPosY = Gdx.graphics.getHeight();
        float startPosX = 0 + relativePad * 3;

        /* labels */
        float relativeFontSize = getRelativeScreenHeightScalar(4f);

        Label pauseLabel = createLabel("Pause", Color.WHITE, relativeFontSize);
        curPosY -= relativePad * 3 + pauseLabel.getHeight();
        pauseLabel.setPosition(startPosX, curPosY);

        /* buttons */
        float relativeButtonFontSize = relativeFontSize / 2f;
        float relativeButtonSizeX = getRelativeScreenWidthScalar(60f);
        float relativeButtonSizeY = getRelativeScreenHeightScalar(40f);

        // resume
        TextButton resumeBtn = createTextButton("Resume", Color.WHITE, relativeButtonFontSize,
            relativeButtonSizeX, relativeButtonSizeY
        );
        curPosY -= relativePad * 3 + relativeButtonSizeY;
        resumeBtn.setPosition(startPosX, curPosY);
        resumeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                GravitySimulation2.getGameScene("game scene").simulation.paused = false;
            }
        });

        // restart
        TextButton restartBtn = createTextButton("Restart", Color.WHITE, relativeButtonFontSize,
            relativeButtonSizeX, relativeButtonSizeY
        );
        curPosY -= relativePad + relativeButtonSizeY;
        restartBtn.setPosition(startPosX, curPosY);
        restartBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GravitySimulation2.switchToScreen("loading");

                // get current scene and save config
                GameScene scene = GravitySimulation2.getGameScene("game scene");
                scene.dispose();
                SaveConfig saveConfig = scene.saveConfig;

                // loading objects into old scene
                scene.loaded = false;
                new Thread(
                    () -> saveConfig.loadScene(scene)
                ).start();  // start loading scene in new thread
            }
        });

        // settings
        TextButton settingsBtn = createTextButton("Settings", Color.WHITE, relativeButtonFontSize,
            relativeButtonSizeX, relativeButtonSizeY
        );
        curPosY -= relativePad * 3 + relativeButtonSizeY;
        settingsBtn.setPosition(startPosX, curPosY);
        settingsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                SettingsMenu settingsMenu = (SettingsMenu) GravitySimulation2.getGameMenu("settings");
                settingsMenu.setPreviousMenu(PauseMenu.this);
                settingsMenu.show();
            }
        });

        // main
        TextButton backToMainBtn = createTextButton("Main Manu", Color.WHITE, relativeButtonFontSize,
            relativeButtonSizeX, relativeButtonSizeY
        );
        curPosY -= relativePad + relativeButtonSizeY;
        backToMainBtn.setPosition(startPosX, curPosY);
        backToMainBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                GravitySimulation2.popGameScene("game scene").dispose();
                GravitySimulation2.switchToScreen("main menu");
                GravitySimulation2.getGameMenu("main").show();
            }
        });

        BackgroundActor bgActor = new BackgroundActor() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                super.draw(batch, parentAlpha);
                beginShape(batch);

                drawRect(
                    0, 0,
                    startPosX + relativeButtonSizeX + relativePad * 3, Gdx.graphics.getHeight(),
                    new Vector4(Color.DARK_GRAY.r, Color.DARK_GRAY.g, Color.DARK_GRAY.b, 0.5f)
                );

                endShape(batch);
            }
        };
        rootGroup.addActor(bgActor);
        rootGroup.addActor(pauseLabel);
        rootGroup.addActor(resumeBtn);
        rootGroup.addActor(restartBtn);
        rootGroup.addActor(settingsBtn);
        rootGroup.addActor(backToMainBtn);
    }

    @Override
    public void show() {
        super.show();
        GravitySimulation2.getGameScene("game scene").simulation.paused = true;
    }

    @Override
    public void renderUiElements() {

    }

    @Override
    public void dispose() {
    }
}
