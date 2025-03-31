package com.gravitysimulation2.gameinterface.menu.load;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.gravitysimulation2.GravitySimulation2;
import com.gravitysimulation2.gameinterface.InterfaceObject;
import com.gravitysimulation2.objects.GameScene;
import com.gravitysimulation2.save.SaveConfig;
import com.gravitysimulation2.screen.scene.SceneScreen;

public class LoadItemField extends InterfaceObject {
    private final SaveConfig saveConfig;

    public LoadItemField(SaveConfig saveConfig) {
        this.saveConfig = saveConfig;
    }

    private Table getSaveTable() {
        float relativeFontSize = getRelativeScreenHeightScalar(2f);

        float relativeButtonSizeX = getRelativeScreenWidthScalar(60f);
        float relativeButtonSizeY = getRelativeScreenHeightScalar(40f);

        float relativePad = getRelativeScreenHeightScalar(20f);

        Table table = new Table();

        Label nameLbl = new Label(saveConfig.name, skin);
        nameLbl.setFontScale(relativeFontSize);
        nameLbl.setSize(nameLbl.getPrefWidth(), nameLbl.getPrefHeight());
        table.add(nameLbl).left();

        TextButton backButton = new TextButton("load", skin);
        backButton.getLabel().setFontScale(relativeFontSize);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // create scene and attach to screen
                GameScene scene = new GameScene(saveConfig);
                GravitySimulation2.setGameScene(scene, "game scene");
                GravitySimulation2.getGameScreen("game scene").attachToScene(scene);

                // loading objects into new scene
                scene.loaded = false;
                new Thread(
                    () -> scene.load(false)
                ).start();  // start loading scene in new thread

                // show loading screen
                GravitySimulation2.getGameMenu("load").hide();
                GravitySimulation2.switchToScreen("loading");
            }
        });
        table.add(backButton).size(relativeButtonSizeX, relativeButtonSizeY).center().pad(relativePad);

        Label lastTimeLbl = new Label(
            "loaded: " + saveConfig.getFormattedSaveTime(saveConfig.lastLoadTime),
            skin);
        lastTimeLbl.setFontScale(relativeFontSize);
        lastTimeLbl.setSize(lastTimeLbl.getPrefWidth(), lastTimeLbl.getPrefHeight());
        table.add(lastTimeLbl).right().padRight(relativePad);

        Label createTimeLbl = new Label(
            "created: " + saveConfig.getFormattedSaveTime(saveConfig.createTime),
            skin);
        createTimeLbl.setFontScale(relativeFontSize);
        createTimeLbl.setSize(createTimeLbl.getPrefWidth(), createTimeLbl.getPrefHeight());
        table.add(createTimeLbl).right();

        table.setSize(table.getPrefWidth(), table.getPrefHeight());
        table.setPosition(0, 0);
        return table;
    }

    @Override
    public void setupUI() {
        Table table = getSaveTable();
        rootGroup.addActor(table);
        rootGroup.setSize(table.getPrefWidth(), table.getPrefHeight());
    }

    @Override
    public void renderUiElements() {

    }

    @Override
    public void dispose() {
        saveConfig.dispose();
    }
}
