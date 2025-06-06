package com.gravitysimulation2.screen.mainmenu;

import com.gravitysimulation2.GravitySimulation2;
import com.gravitysimulation2.gameinterface.menu.load.LoadMenu;
import com.gravitysimulation2.gameinterface.menu.main.MainMenu;
import com.gravitysimulation2.gameinterface.menu.settings.SettingsMenu;
import com.gravitysimulation2.screen.ScreenObject;

public class MainMenuScreen extends ScreenObject {
    final MainMenu menu;
    final LoadMenu loadMenu;
    final SettingsMenu settingsMenu;

    public MainMenuScreen() {
        menu = (MainMenu) GravitySimulation2.getGameMenu("main");
        loadMenu = (LoadMenu) GravitySimulation2.getGameMenu("load");
        settingsMenu = (SettingsMenu) GravitySimulation2.getGameMenu("settings");
    }

    @Override
    public void setupUI() {
        if (scene != null) {
            scene.updateRootGroup();
            rootGroup.addActor(scene.rootGroup);
        }
        rootGroup.addActor(menu.updateRootGroup().rootGroup);
        rootGroup.addActor(loadMenu.updateRootGroup().rootGroup);
        rootGroup.addActor(settingsMenu.updateRootGroup().rootGroup);
        super.setupUI();
    }

    @Override
    public void renderUiElements() {
        super.renderUiElements();
        settingsMenu.renderUiElements();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}
