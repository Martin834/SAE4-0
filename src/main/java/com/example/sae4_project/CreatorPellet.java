package com.example.sae4_project;

public class CreatorPellet extends Creator{
    @Override
    public Entity create() {
        return new Pellet();
    }
}
