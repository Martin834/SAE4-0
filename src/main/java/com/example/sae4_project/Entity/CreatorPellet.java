package com.example.sae4_project.Entity;

import java.io.Serializable;

public class CreatorPellet extends Creator implements Serializable {

    public CreatorPellet() {
        this.create();
    }
    @Override
    public Pellet create() {
        return new Pellet();
    }
}
