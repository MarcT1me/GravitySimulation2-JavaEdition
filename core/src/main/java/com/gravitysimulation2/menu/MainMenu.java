package com.gravitysimulation2.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gravitysimulation2.GravitySimulation2;

public class MainMenu extends MenuObject {
    @Override
    protected Actor setupUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.defaults().expandX();

        Label mainLbl = new Label("Main", skin);
        mainLbl.setColor(Color.WHITE);
        table.add(mainLbl).left().padLeft(290).padBottom(20);

        Label menuLbl = new Label("Menu", skin);
        menuLbl.setColor(Color.GREEN);
        table.add(menuLbl).right().padRight(290).padBottom(20).row();

        TextButton loadBtn = new TextButton("load", skin);
        loadBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GravitySimulation2.instance.setScreen(GravitySimulation2.instance.menuMap.get("load"));
            }
        });
        table.add(loadBtn).center().size(60, 30).colspan(2).padTop(20).row();

        TextButton settingsBtn = new TextButton("Settings", skin);
        settingsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GravitySimulation2.instance.setScreen(GravitySimulation2.instance.menuMap.get("settings"));
            }
        });
        table.add(settingsBtn).center().size(60, 30).colspan(2).pad(10).row();

        TextButton exitBtn = new TextButton("Exit", skin);
        exitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        table.add(exitBtn).center().size(60, 30).colspan(2);

        return table;
    }
}
