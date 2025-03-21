package com.gravitysimulation2.save;

import com.gravitysimulation2.config.Config;

public class SaveConfig implements Config<SaveConfig> {
    public String name;
    public float lastLoadTime;
    public float createTime;

    @Override
    public String getConfigPath() {
        return "";
    }

    @Override
    public SaveConfig getDefaultConfig() {
        return null;
    }
}
