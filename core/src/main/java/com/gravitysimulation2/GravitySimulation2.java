package com.gravitysimulation2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gravitysimulation2.config.*;
import com.gravitysimulation2.gameinterface.InterfaceObject;
import com.gravitysimulation2.gameinterface.LoadingScreen;
import com.gravitysimulation2.gameinterface.menu.main.MainMenu;
import com.gravitysimulation2.gameinterface.menu.MenuObject;
import com.gravitysimulation2.gameinterface.menu.load.LoadMenu;
import com.gravitysimulation2.gameinterface.menu.pause.PauseMenu;
import com.gravitysimulation2.gameinterface.menu.settings.SettingsMenu;
import com.gravitysimulation2.objects.scene.SceneScreen;

import java.util.HashMap;
import java.util.Map;

public class GravitySimulation2 extends Game {
    public static GravitySimulation2 instance;

    public Map<String, MenuObject> menuMap = new HashMap<>();

    public static void setGameScreen(String name) {
        instance.setScreen(instance.menuMap.get(name));
    }

    public GravitySimulation2() {
        instance = this;
    }

    @Override
    public void create() {
        // load configs
        ConfigManager.load(
            GameConfig.class, "game config",
            ConfigManager.emptyParametersTypes,
            ConfigManager.emptyParameters
        );
        ConfigManager.load(
            WindowConfig.class, "window config",
            ConfigManager.emptyParametersTypes,
            ConfigManager.emptyParameters);
        InterfaceObject.loadUiSkin();
        // menu
        initAllMenu();
        setScreen(menuMap.get("main"));
    }

    public void setScreen(MenuObject screen) {
        super.setScreen(screen);
        if (screen != null) {
            Gdx.input.setInputProcessor(screen.stage);
        }
    }

    public void initAllMenu() {
        menuMap.put("main", new MainMenu());
        menuMap.put("load", new LoadMenu());
        menuMap.put("settings", new SettingsMenu());

        menuMap.put("pause", new PauseMenu());
        menuMap.put("scene", new SceneScreen());
        menuMap.put("loading", new LoadingScreen());
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        super.render();
    }
}
