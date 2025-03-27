package com.example.sae4_project.Entity;

import com.example.sae4_project.QuadTree.Map;

public abstract class MoveableBody extends Entity {

    public double smoothing = 80;

    public double speed = 1;

    public double[] velocity = new double[2];

    public void moveTowards(double posXMouse, double posYMouse, double maxSpeed) {

        velocity = new double[]{posXMouse - this.circle.getCenterX(), posYMouse - this.circle.getCenterY()};

        double euclidianDistance = Math.sqrt((velocity[0] * velocity[0]) + (velocity[1] * velocity[1]));

        double adjustedSpeed = Math.min(euclidianDistance / 100, maxSpeed);

        if (euclidianDistance > 4) {
            euclidianDistance = 4 * speed;
        }

        velocity = normalizeDouble(velocity);

        velocity[0] *= adjustedSpeed;
        velocity[1] *= adjustedSpeed;

        //System.out.println(adjustedSpeed);
    }

    public void move() {
        if(this.circle.getCenterX() + velocity[0] >= 0 && this.circle.getCenterX() + velocity[0] <= Map.size) {
            this.circle.setCenterX(this.circle.getCenterX() + velocity[0]);
        }
        if(this.circle.getCenterY() + velocity[1] >= 0 && this.circle.getCenterY() + velocity[1] <= Map.size){
            this.circle.setCenterY(this.circle.getCenterY() + velocity[1]);
        }
    }

    public double[] normalizeDouble(double[] array){

        double magnitude = Math.sqrt( (array[0] * array[0]) + (array[1] * array[1]) );

        if (array[0] != 0 || array[1] != 0 ){
            return new double[]{array[0] / magnitude, array[1] / magnitude};
        }
        return new double[]{0,0};
    }

}
