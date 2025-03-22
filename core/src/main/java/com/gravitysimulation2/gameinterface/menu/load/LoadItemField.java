package com.gravitysimulation2.gameinterface.menu.load;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gravitysimulation2.gameinterface.menu.MenuObject;

public class LoadItemField extends MenuObject {
    private final String name;
    private final float lastLoadTime;
    private float createTime;

    public LoadItemField(String name, float lastLoadTime, float createTime) {
        this.name = name;
        this.lastLoadTime = lastLoadTime;
        this.createTime = createTime;
    }

    private Table getSaveTable() {
        float relativeFontSize = getRelativeScreenHeightScalar(2f);

        float relativeButtonSizeX = getRelativeScreenWidthScalar(60f);
        float relativeButtonSizeY = getRelativeScreenHeightScalar(40f);

        float relativePad = getRelativeScreenHeightScalar(20f);

        Table table = new Table();

        Label nameLbl = new Label(name, skin);
        nameLbl.setFontScale(relativeFontSize);
        nameLbl.setSize(nameLbl.getPrefWidth(), nameLbl.getPrefHeight());
        table.add(nameLbl).left();

        TextButton backButton = new TextButton("load", skin);
        backButton.getLabel().setFontScale(relativeFontSize);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
        table.add(backButton).size(relativeButtonSizeX, relativeButtonSizeY).center().pad(relativePad);

        Label lastTimeLbl = new Label("loaded: " + lastLoadTime, skin);
        lastTimeLbl.setFontScale(relativeFontSize);
        lastTimeLbl.setSize(lastTimeLbl.getPrefWidth(), lastTimeLbl.getPrefHeight());
        table.add(lastTimeLbl).right().padRight(relativePad);

        Label createTimeLbl = new Label("created: " + lastLoadTime, skin);
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
}
