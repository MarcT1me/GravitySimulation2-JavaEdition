package com.gravitysimulation2.menu.settings;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gravitysimulation2.menu.MenuObject;

public class GraphicsMenuField extends MenuObject {
    @Override
    protected void setupUI() {
        Table table = new Table();
        stage.addActor(table);

        Label graphicLbl = new Label("Graphic", skin);
        table.add(graphicLbl).center().row();

        rootGroup.addActor(table);
        table.setSize(table.getPrefWidth(), table.getPrefHeight());
        table.setPosition(table.getPrefWidth() / 2f, table.getPrefHeight() / 2f);
    }
}
