package com.example.sae4_project.Entity;



public class CreatorPlayer extends Creator {

    public CreatorPlayer() {
        this.create();
    }

    @Override
    public Player create() {
        return new Player();
    }
}
