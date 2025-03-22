package com.gravitysimulation2.gameinterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.gravitysimulation2.GravitySimulation2;
import com.gravitysimulation2.gameinterface.menu.MenuObject;
import com.gravitysimulation2.objects.scene.GameScene;

public class LoadingScreen extends MenuObject {
    @Override
    public void setupUI() {
        float screenCenterX = Gdx.graphics.getWidth() / 2f;
        float screenCenterY = Gdx.graphics.getHeight() / 2f;
        float relativeFontSize = getRelativeScreenHeightScalar(10f);

        Label loadingLbl = createLabel("Loadig", Color.YELLOW, relativeFontSize);
        loadingLbl.setPosition(
            screenCenterX - loadingLbl.getWidth() / 2f,
            screenCenterY - loadingLbl.getHeight() / 2f
        );

        rootGroup.addActor(loadingLbl);
    }

    @Override
    public void renderUiElements() {
        super.renderUiElements();
        if (GameScene.loaded)
            switchToScene();
    }

    private static void switchToScene() {
        GravitySimulation2.setGameScreen("scene");
    }
}
