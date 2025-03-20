package com.gravitysimulation2.menu.load;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gravitysimulation2.menu.MenuObject;

public class LoadItemField extends MenuObject {
    private String name;
    private float time;

    public LoadItemField(String name, float time) {
        super();

        this.name = name;
        this.time = time;

        actor = setupUI();
    }

    @Override
    protected Actor setupUI() {
        Table table = new Table();
        stage.addActor(table);
        table.defaults().expandX();

        Label nameLbl = new Label(name, skin);
        table.add(nameLbl).left().padLeft(215);

        TextButton backButton = new TextButton("load", skin);
        backButton.addListener(
            new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                }
            }
        );
        table.add(backButton).size(40, 30).center().pad(10);


        Label timeLbl = new Label(String.valueOf(time), skin);
        table.add(timeLbl).right().padRight(225);

        return table;
    }
}
