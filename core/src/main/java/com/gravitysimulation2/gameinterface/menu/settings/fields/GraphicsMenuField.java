package com.gravitysimulation2.gameinterface.menu.settings.fields;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.gravitysimulation2.config.ConfigManager;
import com.gravitysimulation2.config.WindowConfig;
import com.gravitysimulation2.gameinterface.menu.MenuObject;

import java.util.LinkedList;

public class GraphicsMenuField extends MenuObject {
    public float categoryStartX;
    WindowConfig windowConfig;

    TextField fpsField;
    CheckBox VSyncBox;
    CheckBox fullscreenBox;
    SelectBox<String> monitorSelect;
    SelectBox<String> resolutionSelect;

    public GraphicsMenuField(float categoryStartX) {
        this.categoryStartX = categoryStartX;
        windowConfig = (WindowConfig) ConfigManager.getConfig("window config");
    }

    @Override
    protected void setupUI() {
        float relativePad = getRelativeScreenHeightScalar(10f);
        float startPosX = categoryStartX + relativePad;
        float curPosY = Gdx.graphics.getHeight();

        float relativeFontSize = getRelativeScreenHeightScalar(2f);
        float relativeButtonSizeX = getRelativeScreenWidthScalar(60f);
        float relativeButtonSizeY = getRelativeScreenHeightScalar(30f);

        // full
        fullscreenBox = createCheckBox(
            "fullscreen", Color.WHITE, relativeFontSize
        );
        fullscreenBox.setChecked(windowConfig.fullscreen);

        curPosY -= relativePad + fullscreenBox.getHeight();
        fullscreenBox.setPosition(startPosX, curPosY);

        // monitor
        Label monitorLbl = new Label("monitor: ", skin);

        monitorLbl.setFontScale(relativeFontSize);
        monitorLbl.setColor(Color.WHITE);
        monitorLbl.setSize(monitorLbl.getPrefWidth(), monitorLbl.getPrefHeight());

        curPosY -= relativePad + monitorLbl.getHeight();
        monitorLbl.setPosition(startPosX + relativePad, curPosY);

        // box
        monitorSelect = new SelectBox<>(skin);

        java.util.List<String> monitorsNames = new LinkedList<>();
        int i = 0;
        for (Graphics.Monitor monitor : Gdx.graphics.getMonitors()) {
            monitorsNames.add(i + "-" + monitor.name);
            i++;
        }

        Array<String> monitors = new Array<>(monitorsNames.toArray(String[]::new));
        monitorSelect.setItems(monitors);
        monitorSelect.setSelected(getCurrentResolution());  // callback
        monitorSelect.setSize(relativeButtonSizeX, relativeButtonSizeY);

        monitorSelect.setPosition(startPosX + relativePad + monitorLbl.getWidth(), curPosY);

        // res
        Label resLbl = new Label("resolution: ", skin);

        resLbl.setFontScale(relativeFontSize);
        resLbl.setColor(Color.WHITE);
        resLbl.setSize(resLbl.getPrefWidth(), resLbl.getPrefHeight());

        curPosY -= relativePad + resLbl.getHeight();
        resLbl.setPosition(startPosX + relativePad, curPosY);

        // box
        resolutionSelect = new SelectBox<>(skin);

        Array<String> resolutions = new Array<>(
            new String[]{
                "1920x1200",
                "1920x1080",
                "1600x900",
                "1280x720",
                "1440x940",
                "720x480"
            }
        );
        resolutionSelect.setItems(resolutions);
        resolutionSelect.setSelected(getCurrentResolution());  // callback
        resolutionSelect.setSize(relativeButtonSizeX, relativeButtonSizeY);

        resolutionSelect.setPosition(startPosX + relativePad + resLbl.getWidth(), curPosY);

        // vsync
        VSyncBox = createCheckBox(
            "VSync", Color.WHITE, relativeFontSize
        );
        VSyncBox.setChecked(windowConfig.vsync);

        curPosY -= relativePad + VSyncBox.getHeight();
        VSyncBox.setPosition(startPosX, curPosY);

        // FPS
        Label targetFpsLbl = new Label("Target FPS: ", skin);

        targetFpsLbl.setFontScale(relativeFontSize);
        targetFpsLbl.setColor(Color.WHITE);
        targetFpsLbl.setSize(targetFpsLbl.getPrefWidth(), targetFpsLbl.getPrefHeight());

        curPosY -= relativePad + targetFpsLbl.getHeight();
        targetFpsLbl.setPosition(startPosX + relativePad, curPosY);

        // field
        fpsField = new TextField(String.valueOf(windowConfig.targetFPS), skin);

        fpsField.setMaxLength(3);
        fpsField.setTextFieldFilter((textField, c) -> Character.isDigit(c));
        fpsField.setSize(relativePad * 4, relativePad * 3);
        fpsField.setPosition(
            startPosX + targetFpsLbl.getWidth() + relativePad,
            curPosY);

        /* adding */
        rootGroup.addActor(fullscreenBox);
        rootGroup.addActor(resLbl);
        rootGroup.addActor(resolutionSelect);

        rootGroup.addActor(VSyncBox);
        rootGroup.addActor(targetFpsLbl);
        rootGroup.addActor(fpsField);

        rootGroup.addActor(monitorLbl);
        rootGroup.addActor(monitorSelect);
    }

    private String getCurrentResolution() {
        return windowConfig.windowWidth + "x" + windowConfig.windowHeight;
    }

    public void applySettings() {
        windowConfig.fullscreen = fullscreenBox.isChecked();

        String[] mon = monitorSelect.getSelected().split("-");
        windowConfig.selectedMonitor = Integer.parseInt(mon[0]);

        String[] res = resolutionSelect.getSelected().split("x");

        windowConfig.windowWidth = Integer.parseInt(res[0]);
        windowConfig.windowHeight = Integer.parseInt(res[1]);

        windowConfig.vsync = VSyncBox.isChecked();

        try {
            windowConfig.targetFPS = Integer.parseInt(fpsField.getText());
        } catch (NumberFormatException e) {
            windowConfig.targetFPS = 60;
        }

        applyGraphicsSettings();
    }

    public void resetSettings() {
        windowConfig = new WindowConfig();
        ConfigManager.addConfig("window config", windowConfig);
        applyGraphicsSettings();
    }

    private void applyGraphicsSettings() {
        windowConfig.apply();
        ConfigManager.save("window config");
    }
}
