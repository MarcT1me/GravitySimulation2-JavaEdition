package com.gravitysimulation2.objects.physic;

import com.gravitysimulation2.objects.IUpdatable;

import java.util.List;
import java.util.stream.Stream;

public class PhysicBody extends Physic implements IUpdatable {
    // forces
    public final Vector2D F_total = new Vector2D();
    public final Vector2D F_gravity = new Vector2D();
    public final Vector2D F_tidal = new Vector2D();
    public final Vector2D F_torque = new Vector2D();
    // simulation
    public Simulation simulation;

    public Vector2D pos;  // general position
    public double angle;  // rotation

    // sizes
    public double mass;  // mass of object
    public double radius;  // sizes (ellipse width height)

    // velocity
    public Vector2D velocity;  // general
    public double angularVelocity; // rotation

    // other
    public double structuralIntegrity;

    public double momentOfInertia;
    public double strength;

    public PhysicBody(
        Simulation simulation,
        Vector2D pos, double angle,
        double mass, double density, double radius,
        Vector2D velocity, double angularVelocity
    ) {
        this.simulation = simulation;

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
    public Vector2D getPos() {
        return pos;
    }

    public double getMass() {
        return mass;
    }

    public double getRadius() {
        return radius;
    }

    // gravity
    public void updateGravityForce(List<PhysicBody> objects) {
        F_gravity.setZero();

        objects
            .forEach(obj -> {
                if (obj == this) return; // Игнорируем себя

                Vector2D force = calculateGravityForce(
                    this.getPos(), this.getMass(),
                    obj.getPos(), obj.getMass()
                );

                F_gravity.add(
                    force
                );
            });
    }

    public void addGravityForceToTotal() {
        F_total.add(F_gravity);
    }

    // tibal
    public void updateTidalForce(Stream<PhysicBody> objects) {
        F_tidal.setZero();

        objects.forEach(other -> {
            if (this == other)
                return;

            PhysicBody primary = (this.mass > other.mass) ? this : other;
            PhysicBody satellite = (primary == this) ? other : this;

            if (satellite != this)
                return;

            F_tidal.add(
                Physic.calculateTidalForce(
                    this.getPos(), this.getMass(), satellite.getPos(), satellite.getRadius(), satellite.getMass()
                )
            );

            double crossSection = Math.PI * radius * radius;
            double stress = F_tidal.len() / crossSection;

            if (stress > strength) {
                structuralIntegrity -= 0.1f;
            }
        });
    }

    // rotation
    private void updateRotationForces(Stream<PhysicBody> objects) {
        F_torque.setZero();

        objects.forEach(other -> {
            if (this == other) return;

            Vector2D r = other.getPos().cpy().sub(this.getPos());
            double crossProduct = r.crs(new Vector2D(F_gravity.x, F_gravity.y));
            F_torque.add(new Vector2D(crossProduct, crossProduct));
        });
    }

    // updating
    public boolean checkStructuralFailure() {
        return structuralIntegrity <= 0;
    }

    public void updateAngularVelocity() {
        angularVelocity += F_torque.cpy().scl(1 / momentOfInertia).len();
    }

    public void updateAngle(float deltaTime, float simulationSpeed) {
        angle += angularVelocity * deltaTime * simulationSpeed;
    }

    public void updateVelocity(float deltaTime) {
        Vector2D acceleration = F_total.cpy().scl(1.0f / mass);
        velocity.add(acceleration.scl(deltaTime));
    }

    public void updatePos(float deltaTime) {
        pos.add(velocity.cpy().scl(deltaTime));
    }


    @Override
    public void preUpdate(float deltaTime) {
        F_total.setZero();

        List<PhysicBody> bodyStream = simulation.space.getPhysicBodyes();

        updateGravityForce(bodyStream);
        addGravityForceToTotal();
    }

    @Override
    public void update(float deltaTime) {
        updateVelocity(deltaTime);
        updatePos(deltaTime);
    }
}
