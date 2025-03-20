package com.gravitysimulation2.menu.load;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gravitysimulation2.GravitySimulation2;
import com.gravitysimulation2.menu.MenuObject;

public class LoadMenu extends MenuObject {
    @Override
    protected Actor setupUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label graphicLbl = new Label("Load", skin);
        table.add(graphicLbl).padBottom(20).row();

        LoadItemField loadItemField = new LoadItemField("unnamed", 3600);
        table.add(loadItemField.actor).padBottom(20).row();

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(
            new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GravitySimulation2.instance.setScreen(GravitySimulation2.instance.menuMap.get("main"));
                }
            }
        );
        table.add(backButton).center().size(40, 30).colspan(2);

        return table;
    }
}
