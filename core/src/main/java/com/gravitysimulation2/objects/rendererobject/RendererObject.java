package com.gravitysimulation2.objects.rendererobject;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.gravitysimulation2.objects.Camera;
import com.gravitysimulation2.objects.GameObject;
import com.gravitysimulation2.objects.IRenderer;
import com.gravitysimulation2.objects.scene.GameScene;

import java.util.Map;

public abstract class RendererObject implements IRenderer, Disposable {
    public GameObject sourceObject;
    protected ShapeRenderer shapeRenderer;
    protected Map<String, Object> rendererData;

    public RendererObject(GameObject sourceObject, Map<String, Object> rendererData) {
        this.sourceObject = sourceObject;
        this.rendererData = rendererData;
        this.shapeRenderer = GameScene.getCurrent().shapeRenderer;
    }

    public Vector2 fromWorldToScreenVector2(Vector2 vec) {
        Camera camera = GameScene.getCurrent().camera;
        return new Vector2(
            (vec.x - camera.pos.x) / camera.zoom,
            (vec.y - camera.pos.y) / camera.zoom
        );
    }

    public float fromWorldToScreenScalar(float scalar) {
        return scalar / GameScene.getCurrent().camera.zoom;
    }

    @Override
    public void render() {
        Vector2 screenPos = fromWorldToScreenVector2(sourceObject.pos);
        shapeRenderer.circle(screenPos.x, screenPos.y, fromWorldToScreenScalar(sourceObject.radius));
    }
}
