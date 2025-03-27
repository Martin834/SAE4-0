package com.example.sae4_project.Entity;

public class CreatorEnemy extends Creator{

    public CreatorEnemy() {
        this.create();
    }

    @Override
    public Enemy create(double x, double y) {
        return new Enemy( x, y);
    }

    public Enemy create(){return new Enemy(0,0);}
}
