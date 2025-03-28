package com.example.sae4_project.Entity;


import com.example.sae4_project.Online.DataPellets;

public class CreatorPellet extends Creator  {

    /**
     * Calls the create method once a CreatorPellet is made
     */
    public CreatorPellet() {
        this.create();
    }

    /**
     * Creates an empty Pellet object
     * @return
     */
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

    public Pellet createFromData(DataPellets data){
        Pellet newPellet = new Pellet(data.getX(), data.getY());
        newPellet.setMass(data.getMass());
        newPellet.setIdentifier(data.getId());
        return newPellet;
    }
}
