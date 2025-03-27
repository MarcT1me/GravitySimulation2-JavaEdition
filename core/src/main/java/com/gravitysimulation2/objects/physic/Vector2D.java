package com.gravitysimulation2.objects.physic;

public class Vector2D {
    public double x;
    public double y;

    public Vector2D() {
        this(0.0, 0.0);
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D cpy() {
        return new Vector2D(x, y);
    }

    public Vector2D add(Vector2D other) {
        x += other.x;
        y += other.y;
        return this;
    }

    public Vector2D sub(Vector2D other) {
        x -= other.x;
        y -= other.y;
        return this;
    }

    public double len() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector2D nor() {
        double length = len();
        if (length == 0) return this;
        x /= length;
        y /= length;
        return this;
    }

    public Vector2D scl(double scalar) {
        x *= scalar;
        y *= scalar;
        return this;
    }

    public double crs(Vector2D other) {
        return (x * other.y) - (y * other.x);
    }

    public Vector2D crs(double scalar) {
        Vector2D newVector = new Vector2D();
        newVector.x = -y * scalar;
        newVector.y = x * scalar;
        return newVector;
    }

    public void setZero() {
        x = 0;
        y = 0;
    }
}
