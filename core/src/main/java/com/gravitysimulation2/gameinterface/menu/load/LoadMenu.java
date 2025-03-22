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
    @Override
    public void setupUI() {
        float screenCenterX = Gdx.graphics.getWidth() / 2f;

        float relativeFontSize = getRelativeScreenHeightScalar(4f);
        float relativeButtonFontSize = relativeFontSize / 2f;

        float relativeButtonSizeX = getRelativeScreenWidthScalar(60f);
        float relativeButtonSizeY = getRelativeScreenHeightScalar(40f);

        float relativePad = getRelativeScreenHeightScalar(10f);

        // load
        Label loadLbl = createLabel("Load", Color.WHITE, relativeFontSize);
        loadLbl.setPosition(screenCenterX - loadLbl.getWidth() / 2f,
            Gdx.graphics.getHeight() - loadLbl.getHeight() - relativePad
        );

        // test item
        List<LoadItemField> items = new LinkedList<>();

        items.add(createLoadItem(
                new SaveConfig("untitled"),
                screenCenterX - rootGroup.getWidth() / 2f,
                Gdx.graphics.getHeight() - rootGroup.getHeight() - loadLbl.getHeight() - relativePad * 2
            )
        );

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

    private LoadItemField createLoadItem(SaveConfig saveConfig, float x, float y) {
        LoadItemField loadItem = (LoadItemField) new LoadItemField(
            saveConfig.name, saveConfig.lastLoadTime, saveConfig.createTime
        ).updateRootGroup();

        loadItem.rootGroup.setPosition(x, y);
        return loadItem;
    }
}
