package com.gravitysimulation2.gameinterface.menu.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.gravitysimulation2.gameinterface.menu.MenuBackgroundActor;

public class SettingsMenuBackground extends MenuBackgroundActor {
    public float startX;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        beginDraw(batch);

        drawRect(
            0, 0,
            startX, Gdx.graphics.getHeight(),
            Color.DARK_GRAY);
        drawRect(
            startX, 0,
            Gdx.graphics.getWidth() - startX, Gdx.graphics.getHeight(),
            Color.GRAY);
        drawLine(
            startX, 0,
            startX, Gdx.graphics.getHeight(),
            Color.WHITE);
        endDraw(batch);
    }
}
