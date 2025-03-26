package com.gravitysimulation2.gameinterface.menu.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector4;

import com.gravitysimulation2.gameinterface.BackgroundActor;

public class SettingsMenuBackground extends BackgroundActor {
    public float startX;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        beginDraw(batch);

        drawRect(
            0, 0,
            startX, Gdx.graphics.getHeight(),
            new Vector4(Color.DARK_GRAY.r, Color.DARK_GRAY.g, Color.DARK_GRAY.b, 0.5f)
        );
        drawRect(
            startX, 0,
            Gdx.graphics.getWidth() - startX, Gdx.graphics.getHeight(),
            new Vector4(Color.GRAY.r, Color.GRAY.g, Color.GRAY.b, 0.5f)
        );
        drawLine(
            startX, 0,
            startX, Gdx.graphics.getHeight(),
            new Vector4(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, 0.5f)
        );
        endDraw(batch);
    }
}
