package com.example.sae4_project.Entity;

public abstract class MoveableBody extends Entity {

    public double speed = 1;
    public double smoothing = 80;

    public double[] velocity = new double[2];

    public void moveTowards(double posXMouse, double posYMouse) {

        velocity = new double[]{posXMouse - this.circle.getCenterX(), posYMouse - this.circle.getCenterY()};
        double euclidianDistance = Math.sqrt((velocity[0] * velocity[0]) + (velocity[1] * velocity[1])) / this.smoothing;

        velocity = normalizeDouble(velocity);
    }

    public void move() {
        this.circle.setCenterX(this.circle.getCenterX() + velocity[0]);
        this.circle.setCenterY(this.circle.getCenterY() + velocity[1]);
    }

    public double[] normalizeDouble(double[] array){

        double magnitude = Math.sqrt( (array[0] * array[0]) + (array[1] * array[1]) );

        if (array[0] != 0 || array[1] != 0 ){
            return new double[]{array[0] / magnitude, array[1] / magnitude};
        }
        return new double[]{0,0};
    }

}
