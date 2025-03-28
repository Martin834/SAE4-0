package com.example.sae4_project.Entity;

import com.example.sae4_project.QuadTree.Map;
import javafx.scene.shape.Circle;
import java.util.Collections;
import java.util.List;

public class CircleLeaf implements CircleComponent {
    private Circle circle;
    private double[] velocity = new double[2];

    /**
     * Constructor, creates a new CircleLeaf
     * @param circle
     */
    public CircleLeaf(Circle circle) {
        this.circle = circle;
    }

    /**
     * Checks where the cursor is on the screen and sets the velocity for x and y axis, to know how fast the CircleLeaf
     * should go
     * @param posXMouse
     * @param posYMouse
     * @param maxSpeed
     */
    @Override
    public void moveTowards(double posXMouse, double posYMouse, double maxSpeed) {
        velocity = new double[]{posXMouse - this.circle.getCenterX(), posYMouse - this.circle.getCenterY()};

        double euclidianDistance = Math.sqrt((velocity[0] * velocity[0]) + (velocity[1] * velocity[1]));

        double adjustedSpeed = Math.min(euclidianDistance / 100, maxSpeed * (1 / (circle.getRadius() * circle.getRadius()))*1000);

        if (euclidianDistance > 4) {
            euclidianDistance = 4;
        }

        velocity = normalizeDouble(velocity);

        velocity[0] *= adjustedSpeed;
        velocity[1] *= adjustedSpeed;
    }

    /**
     * Moves the circle of this CircleLeaf in the direction that the velocity array describes.
     */
    @Override
    public void move() {
        if ((circle.getCenterX() + velocity[0])>=0 && (circle.getCenterX() + velocity[0])<= Map.size) {
            circle.setCenterX(circle.getCenterX() + velocity[0]*2);
        }
        if ((circle.getCenterY() + velocity[1])>=0 && (circle.getCenterY() + velocity[1])<= Map.size) {
            circle.setCenterY(circle.getCenterY() + velocity[1]*2);
        }
    }

    /**
     * Calculates the new radius of the entity according to the eaten circle's radius and changes it
     * @param entity
     * @param circle
     */
    @Override
    public void makeFatter(Entity entity, Circle circle) {
        double newRadius = Math.sqrt(circle.getRadius() * circle.getRadius() + entity.getMass());
        circle.setRadius(newRadius);
    }

    /**
     * Returns the List of circles
     * @return
     */
    @Override
    public List<Circle> getCircles() {
        return Collections.singletonList(circle);
    }

    /**
     * Normalizes an array of doubles. Related to vector normalization.
     * @param array
     * @return
     */
    private double[] normalizeDouble(double[] array) {
        double magnitude = Math.sqrt((array[0] * array[0]) + (array[1] * array[1]));
        if (array[0] != 0 || array[1] != 0) {
            return new double[]{array[0] / magnitude, array[1] / magnitude};
        }
        return new double[]{0, 0};
    }
}
