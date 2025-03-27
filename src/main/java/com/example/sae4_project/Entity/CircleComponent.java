package com.example.sae4_project.Entity;

import javafx.scene.shape.Circle;
import java.util.List;

public interface CircleComponent {
    void moveTowards(double posXMouse, double posYMouse, double maxSpeed);
    void move();
    void makeFatter(Entity entity);
    List<Circle> getCircles();
}
