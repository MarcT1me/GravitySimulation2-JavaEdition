package com.gravitysimulation2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gravitysimulation2.config.*;
import com.gravitysimulation2.gameinterface.InterfaceObject;
import com.gravitysimulation2.gameinterface.menu.main.MainMenu;
import com.gravitysimulation2.gameinterface.menu.MenuObject;
import com.gravitysimulation2.gameinterface.menu.load.LoadMenu;
import com.gravitysimulation2.gameinterface.menu.settings.SettingsMenu;

import java.util.HashMap;
import java.util.Map;

public class GravitySimulation2 extends Game {
    public static GravitySimulation2 instance;

    public Map<String, MenuObject> menuMap = new HashMap<>();

    public GravitySimulation2() {
        instance = this;
    }

    @Override
    public void create() {
        // load configs
        ConfigManager.load(GameConfig.class, "game config");
        ConfigManager.load(WindowConfig.class, "window config");
        InterfaceObject.loadUiSkin();
        // menu
        initAllMenu();
        setScreen(menuMap.get("main"));
    }

    public void setScreen(MenuObject screen) {
        super.setScreen(screen);
        Gdx.input.setInputProcessor(screen.stage);
    }

    public void initAllMenu() {
        menuMap.put("main", new MainMenu());
        menuMap.put("settings", new SettingsMenu());
        menuMap.put("load", new LoadMenu());
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        super.render();
    }
}
