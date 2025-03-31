package com.gravitysimulation2.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.gravitysimulation2.objects.IRenderer;

public class DefaultBackground implements IRenderer, Disposable {
    public final SpriteBatch batch = new SpriteBatch();
    final Texture bgImg = new Texture(Gdx.files.internal("StarBg.png"));

    public void resize(ScreenViewport viewport) {
        batch.setProjectionMatrix(viewport.getCamera().combined);
    }

    @Override
    public void preRender() {
        batch.begin();
        batch.draw(bgImg, 0, 0);
        batch.end();
    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        bgImg.dispose();
    }
}
