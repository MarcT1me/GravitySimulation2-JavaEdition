package com.gravitysimulation2.objects.physic;

public class Physic {
    static double G = 6.67430e-11f;
//    static double C_LIGHT = 3e8f;

    public static double calculateMaterialStrength(double density) {
        return density * 1e6;
    }

    public static double calculateMomentOfInertia(double mass, double radius) {
        return 0.4 * mass * radius * radius; // 0.4 = 2/5
    }

    public static Vector2D calculateTidalForce(
        Vector2D primaryPos, double primaryMass,
        Vector2D satellitePos, double satelliteRadius, double satelliteMass
    ) {
        Vector2D delta = satellitePos.cpy().sub(primaryPos);
        double distance = delta.len();
        if (distance == 0) return new Vector2D();

        double tidalAcceleration = (2 * G * primaryMass * satelliteRadius) / (distance * distance);

        return delta.nor().scl(tidalAcceleration * satelliteMass);
    }

    public static Vector2D calculateGravityForce(
        Vector2D primaryPos, double primaryMass,
        Vector2D satellitePos, double satelliteMass
    ) {
        if (primaryPos == satellitePos)
            return new Vector2D();

        Vector2D distVec = satellitePos.cpy().sub(primaryPos);
        double distance = distVec.len();

        double epsilon = 1e-5;
        if (distance < epsilon) {
            return new Vector2D();
        }

        double forceMagnitude = (G * primaryMass * satelliteMass) / (distance * distance);

        Vector2D force = distVec.nor().scl(forceMagnitude);

        return force;
    }

//    public static Vector2 calculateRadiationPressureForce(float power, Vector2 direction, float area) {
//        float forceMagnitude = (power * area) / C_LIGHT;
//        return direction.nor().scl(forceMagnitude);
//    }

//    public Vector3 calculateThrustForceAndMassLess(Vector2 direction, float exhaustVelocity, float deltaTime) {
//        float massLoss = deltaTime * exhaustVelocity; // Упрощённо
//        Vector2 thrust = direction.scl(exhaustVelocity * massLoss / deltaTime);
//        return new Vector3(thrust.x, thrust.y, massLoss);
//    }
}
