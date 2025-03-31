package com.gravitysimulation2.objects.physic;

import java.util.LinkedList;
import java.util.List;

public class SimulationSpace {
    public final List<PhysicBody> objects = new LinkedList<>();

    public List<PhysicBody> getPhysicBodyes() {
        return objects;
    }

    public void addBody(PhysicBody body) {
        objects.add(body);
    }

    public void clear() {
        objects.clear();
    }
}
