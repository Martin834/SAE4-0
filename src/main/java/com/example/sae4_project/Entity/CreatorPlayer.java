package com.example.sae4_project;

public class CreatorPlayer extends Creator{
    @Override
    public Entity create() {
        return new Player();
    }
}
