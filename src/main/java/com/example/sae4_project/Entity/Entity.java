package com.example.sae4_project.Entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Circle;

public abstract class Entity {

    protected int identifier;
    protected SimpleDoubleProperty mass = new SimpleDoubleProperty();
    public Circle circle;

    /**
     * @return the entity's mass
     */
    public double getMass() {
        return mass.get();
    }

    /**
     * @return the mass property
     */
    public SimpleDoubleProperty massProperty() {
        return mass;
    }

    /**
     * Sets the entity's mass to the specified mass parameter
     * @param mass
     */
    public void setMass(double mass) {
        this.mass.set(mass);
    }



}
