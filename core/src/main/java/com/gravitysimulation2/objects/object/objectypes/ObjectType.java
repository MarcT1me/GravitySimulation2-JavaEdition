package com.gravitysimulation2.objects.object.objectypes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;

import com.gravitysimulation2.config.ConfigManager;
import com.gravitysimulation2.config.GraphicConfig;
import com.gravitysimulation2.gameinterface.InterfaceObject;
import com.gravitysimulation2.objects.object.GameObject;
import com.gravitysimulation2.objects.IRenderer;
import com.gravitysimulation2.objects.IUpdatable;
import com.gravitysimulation2.save.SceneParser;

import java.util.Map;

public abstract class ObjectType extends InterfaceObject implements IUpdatable, IRenderer, Disposable {
    public GameObject sourceObject;
    public Map<String, Object> objectData;
    public Vector2 screenPos;
    public Vector3 color;

    protected final ShapeRenderer shapeRenderer;
    protected final TrajectoryQueue trajectoryQueue;
    protected final SimpleConditionalTimer trajectoryTimer;

    protected Label nameLbl;
    protected Label vModLbl;
    protected Label posLbl;

    GraphicConfig graphicConfig;

    public ObjectType(GameObject sourceObject, Map<String, Object> objectData) {
        this.sourceObject = sourceObject;
        this.objectData = objectData;
        this.color = SceneParser.parseVector3(objectData.get("color"));

        this.shapeRenderer = sourceObject.scene.shapeRenderer;
        this.trajectoryQueue = new TrajectoryQueue();
        this.trajectoryTimer = new SimpleConditionalTimer();

        applyConfigs();
    }

    @Override
    public void setupUI() {
        float relativeFontSize = getRelativeScreenHeightScalar(1f);

        nameLbl = createLabel(sourceObject.name, Color.WHITE, relativeFontSize);

        vModLbl = createLabel("V: ", Color.WHITE, relativeFontSize);
        vModLbl.setVisible(graphicConfig.showVMods);

        posLbl = createLabel("pos: ", Color.WHITE, relativeFontSize);
        posLbl.setVisible(graphicConfig.showPositions);

        rootGroup.addActor(nameLbl);
        rootGroup.addActor(vModLbl);
        rootGroup.addActor(posLbl);
    }

    public void applyConfigs() {
        graphicConfig = (GraphicConfig) ConfigManager.getConfig("graphic config");

        if (graphicConfig.showTrajectory) {
            this.trajectoryQueue.setMaxSize(graphicConfig.trajectoryLen);
            this.trajectoryTimer.setIntervalLength(graphicConfig.trajectoryInterval);
        } else {
            trajectoryQueue.setMaxSize(0);
        }
    }

    public Vector2 fromWorldToScreenPosition(Vector2 vec) {
        return sourceObject.scene.camera.fromWorldToScreenPosition(vec);
    }

    public float fromWorldToScreenScalar(float scalar) {
        return scalar / sourceObject.scene.camera.zoom;
    }

    @Override
    public void preUpdate(float deltaTime) {

    }

    @Override
    public void update(float deltaTime) {
        if (trajectoryTimer.update(deltaTime)) {
            trajectoryQueue.add(sourceObject.physicBody.pos);
            trajectoryTimer.restore();
        }
    }

    protected boolean isNotAllowedScreenPositions() {
        return false;
//        Vector2 screenSize =
//            new Vector2(
//                Gdx.graphics.getWidth(),
//                Gdx.graphics.getHeight()
//            );
//        return screenPos.add(screenSize.cpy().scl(
//            0.5f)
//        ).len() > screenSize.cpy().scl(
//            1.5f
//        ).len();
    }

    @Override
    public void preRender() {
        // prepare rendering
        screenPos = fromWorldToScreenPosition(sourceObject.physicBody.pos);

        if (isNotAllowedScreenPositions()) return;

        // enable all need GL functions
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        // render
        if (graphicConfig.showVVectors) renderVVector();

        if (graphicConfig.showTrajectory) renderTrajectory();
        if (graphicConfig.showDirectionLine) renderDirectionLine();

        // render end
        shapeRenderer.end();
    }

    protected void renderVVector() {
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.line(
            screenPos,
            screenPos.cpy().add(sourceObject.physicBody.velocity.cpy().scl(graphicConfig.vVectorsScale))
        );
    }

    protected void renderDirectionLine() {
        shapeRenderer.setColor(color.x, color.y, color.z, graphicConfig.dirLineAlpha);
        shapeRenderer.line(
            (
                new Vector2(
                    Gdx.graphics.getWidth() / 2f,
                    Gdx.graphics.getHeight() / 2f).cpy().add(screenPos)
            ).scl(0.5f),
            screenPos
        );
    }

    protected void renderTrajectory() {
        // render trajectory
        Vector2 prevPoins = null;
        int currentIndex = 0;
        for (Vector2 point : trajectoryQueue.getElements()) {
            point = fromWorldToScreenPosition(point);

            // calculate alpha
            float alpha = (float) currentIndex / trajectoryQueue.size();
            shapeRenderer.setColor(0.5f, 0.5f, 0.5f, alpha);

            // render
            if (prevPoins != null) {
                shapeRenderer.line(prevPoins, point);
            }

            // set prev
            prevPoins = point;
            currentIndex++;
        }
    }

    @Override
    public void render() {

    }

    @Override
    public void renderUiElements() {
        // name
        Vector2 nameLblPos = screenPos.cpy().add(
            new Vector2(
                -nameLbl.getWidth() / 2f,
                5
            )
        );
        nameLbl.setPosition(nameLblPos.x, nameLblPos.y);

        // pos
        posLbl.setText(
            String.format(
                "pos: {x: %.2f, y: %.2f}",
                sourceObject.physicBody.pos.x,
                sourceObject.physicBody.pos.y
            )
        );
        posLbl.setSize(posLbl.getPrefWidth(), posLbl.getPrefHeight());
        Vector2 posLblPos = screenPos.cpy().add(
            new Vector2(
                -posLbl.getWidth() / 2f,
                -posLbl.getHeight())
        );
        posLbl.setPosition(posLblPos.x, posLblPos.y);
        // vel
        vModLbl.setText(
            String.format(
                "vel: {x: %.2f, y: %.2f}",
                sourceObject.physicBody.velocity.x,
                sourceObject.physicBody.velocity.y
            )
        );
        float curPosY = -5f;
        vModLbl.setSize(vModLbl.getPrefWidth(), vModLbl.getPrefHeight());
        if (graphicConfig.showPositions)
            curPosY -= posLbl.getHeight();
        Vector2 vModLblPos = screenPos.cpy().add(
            new Vector2(
                -vModLbl.getWidth() / 2f,
                -vModLbl.getHeight() / 2f + curPosY)
        );
        vModLbl.setPosition(vModLblPos.x, vModLblPos.y);
    }

    @Override
    public void dispose() {
        trajectoryQueue.clear();
    }
}
