package com.gravitysimulation2.menu.load;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gravitysimulation2.GravitySimulation2;
import com.gravitysimulation2.menu.MenuObject;

public class LoadMenu extends MenuObject {
    @Override
    protected void setupUI() {
        Table table = new Table();
        stage.addActor(table);

        Label graphicLbl = new Label("Load", skin);
        table.add(graphicLbl).padBottom(20).row();

        LoadItemField loadItemField = (LoadItemField) new LoadItemField("unnamed", 3600).updateRootGroup();
        table.addActor(loadItemField.rootGroup);
        table.row();

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(
            new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GravitySimulation2.instance.setScreen(GravitySimulation2.instance.menuMap.get("main"));
                }
            }
        );
        table.add(backButton).center().size(40, 30).colspan(2).padTop(20);

        rootGroup.addActor(table);
        table.setSize(table.getPrefWidth(), table.getPrefHeight());
        table.setPosition(table.getPrefWidth() / 2f, table.getPrefHeight() / 2f);
    }
}
