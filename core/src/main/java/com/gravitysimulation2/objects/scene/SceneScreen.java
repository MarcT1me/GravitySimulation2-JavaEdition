package com.gravitysimulation2.objects.scene;

import com.gravitysimulation2.gameinterface.menu.MenuObject;

public class SceneScreen extends MenuObject {
    private final GameScene scene;

    public SceneScreen(GameScene scene) {
        this.scene = scene;
    }

    @Override
    public void setupUI() {
        scene.updateRootGroup();
        rootGroup.addActor(scene.rootGroup);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        scene.preUpdate(delta);
        scene.update(delta);
        scene.render();
    }

    @Override
    public void dispose() {
        scene.dispose();
    }
}
