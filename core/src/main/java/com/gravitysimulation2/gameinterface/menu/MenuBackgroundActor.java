package com.gravitysimulation2.gameinterface.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector4;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

public class MenuBackgroundActor extends Actor implements Disposable {
    private final ShapeRenderer shapeRenderer;

    protected void drawRect(float x, float y, float width, float height, Color color) {
        drawRect(x, y, width, height, new Vector4(color.r, color.g, color.b, color.a));
    }

    protected void drawRect(float x, float y, float width, float height, Vector4 color) {
        prepareDrawShape(ShapeRenderer.ShapeType.Filled, color);
        shapeRenderer.rect(
            x, y,
            width, height
        );
        shapeRenderer.end();
    }

    protected void drawLine(float x, float y, float endX, float endY, Color color) {
        drawLine(x, y, endX, endY, new Vector4(color.r, color.g, color.b, color.a));
    }

    protected void drawLine(float x, float y, float endX, float endY, Vector4 color) {
        prepareDrawShape(ShapeRenderer.ShapeType.Line, color);
        shapeRenderer.line(
            x, y,
            endX, endY
        );
        shapeRenderer.end();
    }

    protected void prepareDrawShape(ShapeRenderer.ShapeType shapeType, Vector4 color) {
        shapeRenderer.begin(shapeType);
        shapeRenderer.setColor(color.x, color.y, color.z, color.w);
    }

    protected MenuBackgroundActor() {
        shapeRenderer = new ShapeRenderer();
        setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    protected void beginDraw(Batch batch) {
        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
    }

    protected void endDraw(Batch batch) {
        batch.begin();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
