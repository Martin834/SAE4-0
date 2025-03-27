package com.example.sae4_project.Entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Circle;

public abstract class Entity {

    protected int identifier;
    protected SimpleDoubleProperty mass = new SimpleDoubleProperty();
    public Circle circle;

    public double getMass() {
        return mass.get();
    }

    public SimpleDoubleProperty massProperty() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass.set(mass);
    }



}
