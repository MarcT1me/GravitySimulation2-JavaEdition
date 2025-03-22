package com.gravitysimulation2.gameinterface.menu.pause;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gravitysimulation2.GravitySimulation2;
import com.gravitysimulation2.gameinterface.menu.MenuObject;
import com.gravitysimulation2.objects.scene.GameScene;

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

        // settings
        TextButton settingsBtn = createTextButton("Settings", Color.WHITE, relativeButtonFontSize,
            relativeButtonSizeX, relativeButtonSizeY
        );
        curPosY -= relativePad * 3 + relativeButtonSizeY;
        settingsBtn.setPosition(startPosX, curPosY);
        settingsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GravitySimulation2.setGameScreen("settings");
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
                GameScene.getCurrent().dispose();
                GravitySimulation2.setGameScreen("main");
            }
        });

        rootGroup.addActor(pauseLabel);
        rootGroup.addActor(settingsBtn);
        rootGroup.addActor(backToMainBtn);
    }
}
