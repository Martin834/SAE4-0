package com.example.sae4_project.Entity;

import java.io.Serializable;

public class CreatorPlayer extends Creator implements Serializable {

    public CreatorPlayer() {
        this.create();
    }

    @Override
    public Player create() {
        return new Player();
    }
}
