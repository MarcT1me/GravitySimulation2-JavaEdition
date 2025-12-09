package com.gravitysimulation2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;

import com.gravitysimulation2.config.ConfigManager;
import com.gravitysimulation2.config.GameConfig;
import com.gravitysimulation2.config.GraphicConfig;
import com.gravitysimulation2.config.WindowConfig;
import com.gravitysimulation2.gameinterface.InterfaceObject;
import com.gravitysimulation2.gameinterface.menu.load.LoadMenu;
import com.gravitysimulation2.gameinterface.menu.main.MainMenu;
import com.gravitysimulation2.gameinterface.menu.pause.PauseMenu;
import com.gravitysimulation2.gameinterface.menu.settings.SettingsMenu;
import com.gravitysimulation2.objects.GameScene;
import com.gravitysimulation2.screen.ScreenObject;
import com.gravitysimulation2.screen.loading.SceneLoadingScreen;
import com.gravitysimulation2.screen.mainmenu.MainMenuScreen;
import com.gravitysimulation2.screen.scene.SceneScreen;

import java.util.HashMap;
import java.util.Map;

public class GravitySimulation2 extends Game {
    public static GravitySimulation2 instance;

    public static Map<String, InterfaceObject> menuMap = new HashMap<>();
    public static Map<String, ScreenObject> screenMap = new HashMap<>();
    public static Map<String, GameScene> sceneMap = new HashMap<>();

    // screen
    public static void switchToScreen(String name) {
        ScreenObject screen = screenMap.get(name);
        instance.setScreen(screen);

        SharedTest.test();
    }

    public static ScreenObject getGameScreen(String name) {
        return screenMap.get(name);
    }

    public static ScreenObject getCurrentGameScreen() {
        return instance.getScreen();
    }

    // menu
    public static InterfaceObject getGameMenu(String name) {
        return menuMap.get(name);
    }

    // scene
    public static void setGameScene(GameScene scene, String sceneName) {
        sceneMap.put(sceneName, scene);
    }

    public static GameScene getGameScene(String sceneName) {
        return sceneMap.get(sceneName);
    }

    public static GameScene popGameScene(String sceneName) {
        return sceneMap.remove(sceneName);
    }

    // game
    public GravitySimulation2() {
        instance = this;
    }

    @Override
    public void create() {
        // load configs
        ConfigManager.load(
            GameConfig.class, "game config",
            ConfigManager.emptyParametersTypes,
            ConfigManager.emptyParameters,
            true
        );
        ConfigManager.load(
            WindowConfig.class, "window config",
            ConfigManager.emptyParametersTypes,
            ConfigManager.emptyParameters,
            true);
        ConfigManager.load(
            GraphicConfig.class, "graphic config",
            ConfigManager.emptyParametersTypes,
            ConfigManager.emptyParameters,
            true);
        InterfaceObject.loadUiSkin();
        // menu
        initAllMenu();
        initAllScreen();
        switchToScreen("main menu");
        getGameMenu("main").show();
    }

    // screen
    public void setScreen(ScreenObject screen) {
        super.setScreen(screen);
        if (screen != null) {
            screen.show();
            screen.updateInputMultiplexer();
            Gdx.input.setInputProcessor(screen.inputMultiplexer);
        }
    }

    public ScreenObject getScreen() {
        return (ScreenObject) super.getScreen();
    }

    // initializing
    private void initAllMenu() {
        menuMap.put("load", new LoadMenu());
        menuMap.put("settings", new SettingsMenu());

        menuMap.put("main", new MainMenu());
        menuMap.put("pause", new PauseMenu());
    }

    private void initAllScreen() {
        screenMap.put("main menu", new MainMenuScreen());
        screenMap.put("loading", new SceneLoadingScreen());
        screenMap.put("game scene", new SceneScreen());
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    // cycle
    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        super.render();
    }

    // exiting
    @Override
    public void dispose() {
        super.dispose();
        screenMap.values().forEach(Disposable::dispose);
        menuMap.values().forEach(Disposable::dispose);
        sceneMap.values().forEach(Disposable::dispose);
        InterfaceObject.disposeSkin();
    }
}
