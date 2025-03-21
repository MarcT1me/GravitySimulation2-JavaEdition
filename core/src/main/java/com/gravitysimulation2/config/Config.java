package com.gravitysimulation2.config;

public interface Config<T extends Config<T>> {
    String configDir = "config";

    String getConfigPath();

    T getDefaultConfig();
}
