package com.gravitysimulation2.objects.scene;

import com.badlogic.gdx.Screen;
import com.gravitysimulation2.objects.IUpdatable;

public class SceneScreen implements Screen, IUpdatable {
    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void preUpdate(float deltaTime) {
        GameScene scene = GameScene.getCurrent();
        if (scene != null)
            scene.preUpdate(deltaTime);
    }

    @Override
    public void update(float deltaTime) {
        GameScene scene = GameScene.getCurrent();
        if (scene != null)
            scene.update(deltaTime);
    }

    @Override
    public void render(float delta) {
        preUpdate(delta);
        update(delta);
        GameScene scene = GameScene.getCurrent();
        if (scene != null){
            scene.render();
            scene.uiElement();
        }
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }
}
