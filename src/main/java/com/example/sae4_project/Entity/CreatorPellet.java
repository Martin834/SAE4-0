package com.example.sae4_project.Entity;


import com.example.sae4_project.Online.DataPellets;

public class CreatorPellet extends Creator  {

    public CreatorPellet() {
        this.create();
    }

    @Override
    public Pellet create() {
        return null;
    }

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
