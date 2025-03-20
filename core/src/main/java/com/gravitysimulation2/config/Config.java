package com.gravitysimulation2.config;

public interface Config<T extends Config<T>> {
    String getConfigPath();
    T getDefaultConfig();
}
