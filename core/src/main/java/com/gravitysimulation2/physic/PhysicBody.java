package com.gravitysimulation2.physic;

import com.badlogic.gdx.math.Vector2;
import com.gravitysimulation2.objects.scene.GameScene;

public class PhysicBody extends Physic {
    // forces
    private final Vector2 F_total = new Vector2();
    private final Vector2 F_gravity = new Vector2();
    private final Vector2 F_tidal = new Vector2();
    private final Vector2 F_torque = new Vector2();

    public Vector2 pos;  // general position
    public float angle;  // rotation

    // sizes
    public float mass;  // mass of object
    public float radius;  // sizes (ellipse width height)

    // velocity
    public Vector2 velocity;  // general
    public float angularVelocity; // rotation

    // other
    public float structuralIntegrity;

    public float momentOfInertia;
    public float strength;

    public PhysicBody(
        Vector2 pos, float angle,
        float mass, float density, float radius,
        Vector2 velocity, float angularVelocity
    ) {
        this.pos = pos;
        this.angle = angle;

        this.mass = mass;
        this.radius = radius;

        this.velocity = velocity;
        this.angularVelocity = angularVelocity;

        this.structuralIntegrity = 1.0f;

        this.momentOfInertia = calculateMomentOfInertia(mass, radius);
        this.strength = calculateMaterialStrength(density);
    }

    // getters (available to changes)
    public Vector2 getPos() {
        return pos;
    }

    public float getMass() {
        return mass;
    }

    public float getRadius() {
        return radius;
    }

    // gravity
    public void updateGravityForce() {
        F_gravity.setZero();

        GameScene.getCurrent().objects.values()
            .forEach(obj ->
                F_gravity.add(calculateGravityForce(this.getPos(), this.getMass(), obj.getPos(), obj.getMass()))
            );
    }

    // tibal
    public void updateTidalForce() {
        F_tidal.setZero();

        GameScene.getCurrent().objects.values().forEach(other -> {
            if (this == other)
                return;

            PhysicBody primary = (this.mass > other.mass) ? this : other;
            PhysicBody satellite = (primary == this) ? other : this;

            if (satellite != this)
                return;

            F_tidal.add(
                Physic.calculateTidalForce(
                    primary.getPos(), primary.getMass(), this.getPos(), this.getRadius(), this.getMass()
                )
            );

            float crossSection = (float) (Math.PI * radius * radius);
            float stress = F_tidal.len() / crossSection;

            if (stress > strength) {
                structuralIntegrity -= 0.1f;
            }
        });
    }

    // rotation
    private void updateRotationForces() {
        F_torque.setZero();

        GameScene.getCurrent().objects.values().forEach(other -> {
            if (this == other) return;

            Vector2 r = other.getPos().cpy().sub(this.getPos());
            float crossProduct = r.crs(F_gravity.x, F_gravity.y);
            F_torque.add(crossProduct, crossProduct);
        });
    }

    // updating
    public boolean checkStructuralFailure() {
        return structuralIntegrity <= 0;
    }

    public void updateForces() {
        F_total.setZero();

        updateGravityForce();
        F_total.add(F_gravity);

        updateTidalForce();

        updateRotationForces();
    }

    public void updateAngularVelocity() {
        angularVelocity += F_torque.scl(1 / momentOfInertia).len();
    }

    public void updateAngle(float deltaTime, float simulationSpeed) {
        angle += angularVelocity * deltaTime * simulationSpeed;
    }

    public void updateVelocity(float deltaTime, float simulationSpeed) {

        Vector2 acceleration = F_total.scl(1.0f / mass);

        velocity = velocity.add(acceleration.scl(deltaTime * simulationSpeed));
    }

    public void updatePos(float deltaTime, float simulationSpeed) {
        pos.add(velocity.scl(deltaTime * simulationSpeed));
    }
}
