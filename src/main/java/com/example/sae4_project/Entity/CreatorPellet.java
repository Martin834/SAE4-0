package com.example.sae4_project.Entity;

public class CreatorPellet extends Creator {

    /**
     * Calls the create method once a CreatorPellet is made
     */
    public CreatorPellet() {
        this.create();
    }

    @Override
    public Pellet create() {
        return null;
    }

    /**
     * Creates a new Pellet by calling the Pellet constructor
     * @param x
     * @param y
     * @return
     */
    @Override
    public Pellet create(double x, double y) {
        return new Pellet(x,y);
    }
}
