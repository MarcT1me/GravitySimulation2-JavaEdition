package com.gravitysimulation2.objects.rendererobject;

public enum ObjectTypes {
    STAR("Star", "big space Light"),
    PLANET("Planet", "just a planet"),
    SATELLITE("Satellite", "planet satellite");

    public final String name;
    public final String description;

    ObjectTypes(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
