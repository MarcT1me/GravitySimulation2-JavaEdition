package com.gravitysimulation2;

import java.util.UUID;

public class Identifier {
    public final String name;
    public final UUID uuid;

    public Identifier() {
        this("unnamed");
    }

    public Identifier(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    public Identifier(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }
}
