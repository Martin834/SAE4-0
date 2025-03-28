package com.example.sae4_project.Entity;

public class CreatorPlayer extends Creator{

    /**
     * Calls the create method once a CreatorPlayer is made
     */
    public CreatorPlayer() {
        this.create();
    }

    /**
     * Creates a new Player by calling the Player constructor
     * @return
     */
    @Override
    public Player create() {
        return new Player();
    }

    @Override
    public Entity create(double x, double y) {
        return null;
    }
}
