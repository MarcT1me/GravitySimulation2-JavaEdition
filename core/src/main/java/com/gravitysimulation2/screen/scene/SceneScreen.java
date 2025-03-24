package com.gravitysimulation2.screen.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.gravitysimulation2.GravitySimulation2;
import com.gravitysimulation2.gameinterface.menu.load.LoadMenu;
import com.gravitysimulation2.gameinterface.menu.pause.PauseMenu;
import com.gravitysimulation2.gameinterface.menu.settings.SettingsMenu;
import com.gravitysimulation2.screen.ScreenObject;
import com.gravitysimulation2.objects.GameScene;

public class SceneScreen extends ScreenObject {
    private GameScene scene;
    final PauseMenu menu;
    final LoadMenu loadMenu;
    final SettingsMenu settingsMenu;

    public SceneScreen() {
        menu = new PauseMenu();
        loadMenu = (LoadMenu) GravitySimulation2.getGameMenu("load");
        settingsMenu = (SettingsMenu) GravitySimulation2.getGameMenu("settings");
    }

    public void attachToScene(GameScene scene) {
        this.scene = scene;
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
    }

    @Override
    public void renderUiElements() {
        menu.renderUiElements();
        loadMenu.renderUiElements();
        settingsMenu.renderUiElements();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            menu.show();
        }

        if (scene != null) {
            scene.preUpdate(delta);
            scene.update(delta);
            scene.render();
            scene.renderUiElements();
        }

        super.render(delta);
    }
}
