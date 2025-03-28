package com.example.sae4_project.Entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Circle;

import java.util.Random;


public abstract class Entity  {

    public Entity() {
        this.identifier = new Random().nextInt();
    }

    public int getIdentifier() {
        return this.identifier;
    }
    public void setIdentifier(int identifier) { this.identifier = identifier; }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return identifier == entity.identifier;
    }

}
