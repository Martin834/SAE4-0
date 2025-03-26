package com.example.sae4_project.Entity;

import javafx.beans.property.SimpleDoubleProperty;

public abstract class MoveableBody extends Entity {

    public double speed = 5;
    public double smoothing = 80;
    public double[] velocity = new double[2];

    public void moveTowards(double posXMouse, double posYMouse) {
        moveTowards(posXMouse, posYMouse, this.speed);
    }

    public void moveTowards(double posXMouse, double posYMouse, double maxSpeed) {
        velocity = new double[]{posXMouse - this.circle.getCenterX(), posYMouse - this.circle.getCenterY()};

        double euclidianDistance = Math.sqrt((velocity[0] * velocity[0]) + (velocity[1] * velocity[1]));
        double adjustedSpeed = Math.min(euclidianDistance / 100, maxSpeed);

        if (euclidianDistance > 4) {
            euclidianDistance = 4 * speed;
        }

        velocity = normalizeDouble(velocity);

        velocity[0] *= adjustedSpeed;
        velocity[1] *= adjustedSpeed;
    }

    public void move() {
        this.circle.setCenterX(this.circle.getCenterX() + velocity[0]);
        this.circle.setCenterY(this.circle.getCenterY() + velocity[1]);
    }

    public double[] normalizeDouble(double[] array) {
        double magnitude = Math.sqrt((array[0] * array[0]) + (array[1] * array[1]));

        if (magnitude != 0) {
            return new double[]{array[0] / magnitude, array[1] / magnitude};
        }
        return new double[]{0, 0};
    }

    public double getDistanceTo(Pellet pellet) {
        double dx = this.circle.getCenterX() - pellet.getCircle().getCenterX();
        double dy = this.circle.getCenterY() - pellet.getCircle().getCenterY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double getDistanceTo(Player player) {
        double dx = this.circle.getCenterX() - player.getCircle().getCenterX();
        double dy = this.circle.getCenterY() - player.getCircle().getCenterY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public boolean isColliding(MoveableBody other) {
        double dx = this.circle.getCenterX() - other.circle.getCenterX();
        double dy = this.circle.getCenterY() - other.circle.getCenterY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < (this.circle.getRadius() + other.circle.getRadius()); // Vérifie si les cercles se chevauchent
    }
    public double calculateRadius() {
        double mass = this.mass.get();
        return 10 * Math.sqrt(mass);
    }

    public void makeFatter(Entity other) {
        double otherMass = other.getMass();
        double myMass = this.getMass();

        double newMass = myMass + otherMass;
        this.setMass(newMass);
        this.circle.setRadius(calculateRadius());
    }

}
