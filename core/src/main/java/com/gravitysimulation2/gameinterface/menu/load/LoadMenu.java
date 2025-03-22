package com.gravitysimulation2.gameinterface.menu.load;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gravitysimulation2.GravitySimulation2;
import com.gravitysimulation2.gameinterface.menu.MenuObject;
import com.gravitysimulation2.save.SaveConfig;

import java.util.List;
import java.util.LinkedList;

public class LoadMenu extends MenuObject {
    List<LoadItemField> items = new LinkedList<>();
    float itemsPosY;

    float screenCenterX;
    float relativePad;

    @Override
    public void setupUI() {
        screenCenterX = Gdx.graphics.getWidth() / 2f;
        relativePad = getRelativeScreenHeightScalar(10f);

        float relativeFontSize = getRelativeScreenHeightScalar(4f);
        float relativeButtonFontSize = relativeFontSize / 2f;

        float relativeButtonSizeX = getRelativeScreenWidthScalar(60f);
        float relativeButtonSizeY = getRelativeScreenHeightScalar(40f);

        // load
        Label loadLbl = createLabel("Load", Color.WHITE, relativeFontSize);
        loadLbl.setPosition(
            screenCenterX - loadLbl.getWidth() / 2f,
            Gdx.graphics.getHeight() - loadLbl.getHeight() - relativePad
        );

        itemsPosY = Gdx.graphics.getHeight() - loadLbl.getHeight();
        for (SaveConfig saveConfig : SaveConfig.scanSaves()) {
            addLoadItem(saveConfig);
        }

        // back
        TextButton backButton = createTextButton(
            "Back", Color.WHITE, relativeButtonFontSize,
            relativeButtonSizeX, relativeButtonSizeY
        );
        backButton.setPosition(screenCenterX - backButton.getWidth() / 2f,
            relativePad
        );
        backButton.addListener(
            new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GravitySimulation2.instance.setScreen(GravitySimulation2.instance.menuMap.get("main"));
                }
            }
        );

        /* adding */
        rootGroup.addActor(loadLbl);

        // adding items
        for (LoadItemField item : items) {
            rootGroup.addActor(item.rootGroup);
        }

        rootGroup.addActor(backButton);
    }

    private void addLoadItem(SaveConfig saveConfig) {
        LoadItemField loadItem = createLoadItem(saveConfig);
        itemsPosY -= relativePad + loadItem.rootGroup.getHeight();
        loadItem.rootGroup.setPosition(
            screenCenterX - loadItem.rootGroup.getWidth() / 2f,
            itemsPosY
        );
        items.add(loadItem);
    }

    private LoadItemField createLoadItem(SaveConfig saveConfig) {
        return (LoadItemField) new LoadItemField(saveConfig).updateRootGroup();
    }
}
