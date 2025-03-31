package com.gravitysimulation2.gameinterface.menu.settings.fields;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.utils.Array;

import com.gravitysimulation2.config.ConfigManager;
import com.gravitysimulation2.config.WindowConfig;
import com.gravitysimulation2.gameinterface.menu.settings.SettingsMenuField;

import java.util.LinkedList;

public class WindowSettingsField extends SettingsMenuField {
    WindowConfig windowConfig;

    CheckBox fullscreenBox;
    java.util.List<String> monitorsNames;
    SelectBox<String> monitorSelect;
    SelectBox<String> resolutionSelect;
    CheckBox VSyncBox;
    Slider fpsSlider;
    Label fpsSliderValueLbl;

    public WindowSettingsField(float categoryStartX) {
        super(categoryStartX);
        windowConfig = (WindowConfig) ConfigManager.getConfig("window config");
    }

    @Override
    public void setupUI() {
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
        Label monitorLbl = createLabel("monitor:", Color.WHITE, relativeFontSize);
        curPosY -= relativePad + monitorLbl.getHeight();
        monitorLbl.setPosition(startPosX + relativePad, curPosY);

        // box
        monitorSelect = new SelectBox<>(skin);

        monitorsNames = new LinkedList<>();
        monitorsNames.add("auto");
        int i = 0;
        for (Graphics.Monitor monitor : Gdx.graphics.getMonitors()) {
            monitorsNames.add(i + "-" + monitor.name);
            i++;
        }

        Array<String> monitors = new Array<>(monitorsNames.toArray(String[]::new));
        monitorSelect.setItems(monitors);
        monitorSelect.setSelected(getCurrentMonitor());  // callback
        monitorSelect.setSize(relativeButtonSizeX, relativeButtonSizeY);

        monitorSelect.setPosition(startPosX + relativePad + monitorLbl.getWidth(), curPosY);

        // res
        Label resLbl = createLabel("resolution:", Color.WHITE, relativeFontSize);
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

        curPosY -= relativePad * 3 + VSyncBox.getHeight();
        VSyncBox.setPosition(startPosX, curPosY);

        // FPS
        Label targetFpsLbl = createLabel("target FPS ", Color.WHITE, relativeFontSize);
        curPosY -= relativePad + targetFpsLbl.getHeight();
        targetFpsLbl.setPosition(startPosX + relativePad, curPosY);

        fpsSlider = createSlider(0, 300, 1, false, Color.BLUE);
        fpsSlider.setSize(fpsSlider.getPrefWidth(), relativePad * 2.5f);
        fpsSlider.setValue(windowConfig.targetFPS);
        fpsSlider.setPosition(
            startPosX + targetFpsLbl.getWidth() + relativePad * 3,
            curPosY
        );

        fpsSliderValueLbl = createLabel("" + fpsSlider.getValue(), Color.WHITE, relativeFontSize);
        fpsSliderValueLbl.setPosition(
            startPosX + targetFpsLbl.getWidth() + fpsSlider.getWidth() + relativePad * 6,
            curPosY
        );

        /* adding */
        rootGroup.addActor(fullscreenBox);
        rootGroup.addActor(monitorLbl);
        rootGroup.addActor(monitorSelect);
        rootGroup.addActor(resLbl);
        rootGroup.addActor(resolutionSelect);

        rootGroup.addActor(VSyncBox);
        rootGroup.addActor(targetFpsLbl);
        rootGroup.addActor(fpsSlider);
        rootGroup.addActor(fpsSliderValueLbl);
    }

    @Override
    public void renderUiElements() {
        monitorSelect.setDisabled(!fullscreenBox.isChecked());
        resolutionSelect.setDisabled(fullscreenBox.isChecked());

        float maxFps;
        if (VSyncBox.isChecked()) {
            maxFps = getMonitorSelectDisplayMode().refreshRate;
        } else {
            maxFps = 300;
        }
        fpsSlider.setRange(0, maxFps);

        if (fpsSlider.getValue() > maxFps) {
            fpsSlider.setValue(maxFps);
        }

        fpsSliderValueLbl.setText("" + (fpsSlider.getValue() == 0f ? "unlimited" : (int) fpsSlider.getValue()));
    }

    private String getCurrentResolution() {
        return windowConfig.windowWidth + "x" + windowConfig.windowHeight;
    }

    private String getCurrentMonitor() {
        return windowConfig.selectedMonitor == -1 ? "auto" : monitorsNames.get(windowConfig.selectedMonitor + 1);
    }

    private Graphics.DisplayMode getMonitorSelectDisplayMode() {
        String selectedMonitor = monitorSelect.getSelected();
        if (selectedMonitor.equals("auto") ||
            Integer.parseInt(selectedMonitor.split("-")[0]) == windowConfig.selectedMonitor
        ) {
            return Gdx.graphics.getDisplayMode();
        } else {
            String[] mon = selectedMonitor.split("-");
            return Gdx.graphics.getDisplayModes()[Integer.parseInt(mon[0])];
        }
    }

    public void applySettings() {
        windowConfig.fullscreen = fullscreenBox.isChecked();

        String selectedMonitor = monitorSelect.getSelected();
        if (selectedMonitor.equals("auto")) {
            windowConfig.selectedMonitor = -1;
        } else {
            String[] mon = selectedMonitor.split("-");
            windowConfig.selectedMonitor = Integer.parseInt(mon[0]);
        }

        String[] res = resolutionSelect.getSelected().split("x");

        windowConfig.windowWidth = Integer.parseInt(res[0]);
        windowConfig.windowHeight = Integer.parseInt(res[1]);

        windowConfig.vsync = VSyncBox.isChecked();

        try {
            windowConfig.targetFPS = (int) fpsSlider.getValue();
        } catch (NumberFormatException e) {
            windowConfig.targetFPS = 60;
        }

        applyConfig();
    }

    public void resetSettings() {
        windowConfig = windowConfig.getDefaultConfig();
        ConfigManager.addConfig("window config", windowConfig);
        applyConfig();
    }

    @Override
    public void applyConfig() {
        windowConfig.apply();
        ConfigManager.save("window config");
    }

    @Override
    public void dispose() {
    }
}
