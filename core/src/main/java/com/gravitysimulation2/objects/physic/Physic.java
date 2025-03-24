package com.gravitysimulation2.objects.physic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Physic {
    static float G = 6.67430e-11f;
    static float C_LIGHT = 3e8f;

    public static float calculateMaterialStrength(float density) {
        return density * 1e6f;
    }

    public static float calculateMomentOfInertia(float mass, float radius) {
        return 0.4f * mass * radius * radius; // 0.4 = 2/5
    }

    public static Vector2 calculateTidalForce(
        Vector2 primaryPos, float primaryMass,
        Vector2 satellitePos, float satelliteRadius, float satelliteMass
    ) {
        Vector2 delta = satellitePos.cpy().sub(primaryPos);
        float distance = delta.len();
        if (distance == 0) return new Vector2();

        float tidalAcceleration = (2 * G * primaryMass * satelliteRadius) / (distance * distance);

        return delta.nor().scl(tidalAcceleration * satelliteMass);
    }

    public static Vector2 calculateGravityForce(
        Vector2 primaryPos, float primaryMass,
        Vector2 satellitePos, float satelliteMass
    ) {
        if (primaryPos == satellitePos)
            return new Vector2();

        Vector2 distVec = satellitePos.cpy().sub(primaryPos);
        float distance = distVec.len();

        float epsilon = 1e-5f;
        if (distance < epsilon) {
            return new Vector2();
        }

        float forceMagnitude = (G * primaryMass * satelliteMass) / (distance * distance);

        return distVec.nor().scl(forceMagnitude);
    }

    public static Vector2 calculateRadiationPressureForce(float power, Vector2 direction, float area) {
        float forceMagnitude = (power * area) / C_LIGHT;
        return direction.nor().scl(forceMagnitude);
    }

    public Vector3 calculateThrustForceAndMassLess(Vector2 direction, float exhaustVelocity, float deltaTime) {
        float massLoss = deltaTime * exhaustVelocity; // Упрощённо
        Vector2 thrust = direction.scl(exhaustVelocity * massLoss / deltaTime);
        return new Vector3(thrust.x, thrust.y, massLoss);
    }
}
