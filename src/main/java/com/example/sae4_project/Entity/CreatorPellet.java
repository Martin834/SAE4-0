package com.example.sae4_project.Entity;

public class CreatorPellet extends Creator {

    public CreatorPellet() {
        this.create();
    }
    @Override
    public Pellet create() {
        return new Pellet();
    }

    @Override
    public Pellet create(double x, double y) {
        return new Pellet(x,y);
    }
}
