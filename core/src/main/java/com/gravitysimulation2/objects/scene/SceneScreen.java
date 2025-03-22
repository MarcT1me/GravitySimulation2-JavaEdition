package com.gravitysimulation2.objects.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.gravitysimulation2.GravitySimulation2;
import com.gravitysimulation2.gameinterface.menu.MenuObject;

public class SceneScreen extends MenuObject {
    private GameScene scene;

    public void attachToScene(GameScene scene) {
        this.scene = scene;
    }

    @Override
    public void setupUI() {
        if (scene != null) {
            scene.updateRootGroup();
            rootGroup.addActor(scene.rootGroup);
        }
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GravitySimulation2.setGameScreen("pause");
        }

        super.render(delta);
        if (scene != null) {
            scene.preUpdate(delta);
            scene.update(delta);
            scene.render();
        }
    }
}
