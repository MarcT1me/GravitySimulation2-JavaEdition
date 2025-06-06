package com.gravitysimulation2.screen.loading;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import com.gravitysimulation2.GravitySimulation2;
import com.gravitysimulation2.screen.ScreenObject;

public class SceneLoadingScreen extends ScreenObject {
    @Override
    public void setupUI() {
        float screenCenterX = Gdx.graphics.getWidth() / 2f;
        float screenCenterY = Gdx.graphics.getHeight() / 2f;
        float relativeFontSize = getRelativeScreenHeightScalar(10f);

        Label loadingLbl = createLabel("Loading", Color.YELLOW, relativeFontSize);
        loadingLbl.setPosition(
            screenCenterX - loadingLbl.getWidth() / 2f,
            screenCenterY - loadingLbl.getHeight() / 2f
        );

        rootGroup.addActor(loadingLbl);
        super.setupUI();
    }

    @Override
    public void renderUiElements() {
        super.renderUiElements();
        if (GravitySimulation2.getGameScene("game scene").loaded)
            switchToScene();
    }

    private static void switchToScene() {
        GravitySimulation2.switchToScreen("game scene");
        GravitySimulation2.getGameScene("game scene").simulation.start();
    }
}
