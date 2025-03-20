package com.gravitysimulation2.menu.settings;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gravitysimulation2.GravitySimulation2;
import com.gravitysimulation2.menu.MenuObject;

public class SettingsMenu extends MenuObject {
    @Override
    protected Actor setupUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        GraphicsMenuField graphicSettings = new GraphicsMenuField();
        table.add(graphicSettings.actor).row();

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


        return table;
    }
}
