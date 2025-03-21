package com.gravitysimulation2.menu.settings;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gravitysimulation2.GravitySimulation2;
import com.gravitysimulation2.menu.MenuObject;

public class SettingsMenu extends MenuObject {
    @Override
    protected void setupUI() {
        Table table = new Table();
        stage.addActor(table);

        GraphicsMenuField graphicSettings = (GraphicsMenuField) new GraphicsMenuField().updateRootGroup();
        table.addActor(graphicSettings.rootGroup);

        table.row();

        TextButton applyButton = new TextButton("Apply", skin);
        applyButton.addListener(
            new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                }
            }
        );
        table.add(applyButton).left().size(50, 30).colspan(2).padTop(20);

        TextButton resetButton = new TextButton("reset", skin);
        resetButton.addListener(
            new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                }
            }
        );
        table.add(resetButton).center().size(50, 30).colspan(2).padTop(20);

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(
            new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GravitySimulation2.instance.setScreen(GravitySimulation2.instance.menuMap.get("main"));
                }
            }
        );
        table.add(backButton).right().size(50, 30).colspan(2).padTop(20);

        rootGroup.addActor(table);
        table.setSize(table.getPrefWidth(), table.getPrefHeight());
        table.setPosition(table.getPrefWidth() / 2f, table.getPrefHeight() / 2f);
    }
}
