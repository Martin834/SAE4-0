package com.example.sae4_project.Entity;

public abstract class MoveableBody extends Entity {

    public double speed = 5;
    public double smoothing = 80;



    public void moveTowards(double posXMouse, double posYMouse) {

        double[] velocity = new double[]{posXMouse - this.circle.getCenterX(), posYMouse - this.circle.getCenterY()};
        double euclidianDistance = Math.sqrt((velocity[0] * velocity[0]) + (velocity[1] * velocity[1])) / this.smoothing;

        velocity = normalizeDouble(velocity);

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

    public double getDistanceTo(Pellet pellet) {
        double dx = this.circle.getCenterX() - pellet.getCircle().getCenterX();
        double dy = this.circle.getCenterY() - pellet.getCircle().getCenterY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double getDistanceTo(Player player) {
        double dx = this.circle.getCenterX() - player.getCircle().getCenterX();
        double dy = this.circle.getCenterY() - player.getCircle().getCenterY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
