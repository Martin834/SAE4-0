package com.example.sae4_project.Entity;

import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.List;

public class CircleComposite implements CircleComponent {
    private List<CircleComponent> circleComponents = new ArrayList<>();

    /**
     * Adds a CircleComponent object to this instance's list of CircleComponents.
     * @param circleComponent
     */
    public void add(CircleComponent circleComponent) {
        circleComponents.add(circleComponent);
    }

    /**
     * Removes a CircleComponent object from this instance's list of CircleComponents.
     * @param circleComponent
     */
    public void remove(CircleComponent circleComponent) {
        circleComponents.remove(circleComponent);
    }

    /**
     * Calls the moveTowards method for every child (every item in the ArrayList) of this CircleComposite
     * @param posXMouse
     * @param posYMouse
     * @param maxSpeed
     */
    @Override
    public void moveTowards(double posXMouse, double posYMouse, double maxSpeed) {
        for (CircleComponent component : circleComponents) {
            component.moveTowards(posXMouse, posYMouse, maxSpeed);
        }
    }

    /**
     * Calls the move method for every child (every item in the ArrayList) of this CircleComposite
     */
    @Override
    public void move() {
        for (CircleComponent component : circleComponents) {
            component.move();
        }
    }

    @Override
    public void makeFatter(Entity entity , Circle circle) {
        for (CircleComponent component : circleComponents) {
            component.makeFatter(entity , circle);
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
