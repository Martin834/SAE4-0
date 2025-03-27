package com.example.sae4_project.Entity;

import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.List;

public class CircleComposite implements CircleComponent {
    private List<CircleComponent> circleComponents = new ArrayList<>();

    public void add(CircleComponent circleComponent) {
        circleComponents.add(circleComponent);
    }

    public void remove(CircleComponent circleComponent) {
        circleComponents.remove(circleComponent);
    }

    @Override
    public void moveTowards(double posXMouse, double posYMouse, double maxSpeed) {
        for (CircleComponent component : circleComponents) {
            component.moveTowards(posXMouse, posYMouse, maxSpeed);
        }
    }

    @Override
    public void move() {
        for (CircleComponent component : circleComponents) {
            component.move();
        }
    }

    @Override
    public void makeFatter(Entity entity) {
        for (CircleComponent component : circleComponents) {
            component.makeFatter(entity);
        }
    }

    @Override
    public List<Circle> getCircles() {
        List<Circle> circles = new ArrayList<>();
        for (CircleComponent component : circleComponents) {
            circles.addAll(component.getCircles());
        }
        return circles;
    }
}
