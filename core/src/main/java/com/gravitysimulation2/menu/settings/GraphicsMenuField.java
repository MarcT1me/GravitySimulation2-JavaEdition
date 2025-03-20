package com.gravitysimulation2.menu.settings;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gravitysimulation2.menu.MenuObject;

public class GraphicsMenuField extends MenuObject {
    @Override
    protected Actor setupUI() {
        Table table = new Table();
        stage.addActor(table);

        Label graphicLbl = new Label("Graphic", skin);
        table.add(graphicLbl).center().row();

        return table;
    }
}
