package com.example.sae4_project.Entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Circle;



public abstract class Entity  {
    public int getIdentifier() {
        return this.identifier;
    }
    public void setIdentifier(int identifier) { this.identifier = identifier; }
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
