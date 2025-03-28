package com.example.sae4_project.QuadTree;

import javafx.beans.property.SimpleDoubleProperty;



public class Coordinate  {

    private SimpleDoubleProperty X = new SimpleDoubleProperty();

    private SimpleDoubleProperty Y = new SimpleDoubleProperty();

    public SimpleDoubleProperty XProperty(){
        return this.X;
    }

    public double getX() {
        return X.doubleValue();
    }

    public void setX(double x) {
        X.set(x);
    }

    public double getY() {
        return Y.doubleValue();
    }

    public void setY(double y) {
        Y.set(y);
    }

    public SimpleDoubleProperty YProperty(){
        return this.Y;
    }

    public Coordinate(double x, double y) {
        setX(x);
        setY(y);
    }
}
