package com.gravitysimulation2.screen.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector4;
import com.gravitysimulation2.gameinterface.BackgroundActor;

public class SceneScreenBackground extends BackgroundActor {
    Vector2 size;

    public SceneScreenBackground(Vector2 size) {
        this.size = size;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        beginShape(batch);

        drawRect(
            Gdx.graphics.getWidth() - size.x,
            0,
            size.x,
            size.y,
            new Vector4(Color.DARK_GRAY.r, Color.DARK_GRAY.g, Color.DARK_GRAY.b, 0.33f)
        );

        endShape(batch);
    }
}
