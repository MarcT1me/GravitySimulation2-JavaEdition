package com.gravitysimulation2.gameinterface.menu.settings;

import com.gravitysimulation2.gameinterface.menu.MenuObject;

public abstract class SettingsMenuField extends MenuObject {
    public float categoryStartX;

    public SettingsMenuField(float categoryStartX) {
        super();
        this.categoryStartX = categoryStartX;
    }

    public abstract void applySettings();

    public abstract void resetSettings();

    public abstract void applyConfig();
}
