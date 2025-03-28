package com.example.sae4_project.Entity;

public class CreatorEnemy extends Creator{

    /**
     * Calls the create method once a CreatorEnemy is made
     */
    public CreatorEnemy() {
        this.create();
    }

    /**
     * Creates a new Enemy by calling the Enemy constructor
     * @param x
     * @param y
     * @return
     */
    @Override
    public Enemy create(double x, double y) {
        return new Enemy( x, y);
    }

    /***
     * Creates an empty enemy
     * @return
     */
    public Enemy create(){return new Enemy(0,0);}
}
