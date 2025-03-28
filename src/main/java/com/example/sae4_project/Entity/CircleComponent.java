package com.example.sae4_project.Entity;

import javafx.scene.shape.Circle;
import java.util.List;

/**
 * Defines the CircleComponent interface that is implemented by other classes
 */
public interface CircleComponent {
    void moveTowards(double posXMouse, double posYMouse, double maxSpeed);
    void move();
    void makeFatter(Entity entity , Circle circle);
    List<Circle> getCircles();
}
