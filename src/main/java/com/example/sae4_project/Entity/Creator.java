package com.example.sae4_project.Entity;

/**
 * Defines the methods that inherited Creator classes will use
 */
public abstract class Creator {

    public abstract Entity create();

    public abstract Entity create(double x, double y);

}
