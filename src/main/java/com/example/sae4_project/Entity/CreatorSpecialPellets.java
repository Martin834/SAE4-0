package com.example.sae4_project.Entity;

public class CreatorSpecialPellets extends Creator{
    @Override
    public SpecialPellets create() {
        return new SpecialPellets();
    }

    @Override
    public SpecialPellets create(double x, double y) {
        return new SpecialPellets(x,y);
    }
}
