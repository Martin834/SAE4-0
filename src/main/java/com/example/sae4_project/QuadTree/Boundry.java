package com.example.sae4_project.QuadTree;

import java.io.Serializable;

public class Boundry implements Serializable {

    private QuadTree quadTree;

    private Coordinate coordinate;

    public Boundry(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    private double length;
    private double height;

    public boolean contains(Coordinate coordinate){
        if(coordinate.getX() < this.coordinate.getX()) return false;
        if(coordinate.getY() < this.coordinate.getY()) return false;
        if(coordinate.getX() >= this.coordinate.getX() + this.length) return false;
        if(coordinate.getY() >= this.coordinate.getY() + this.height) return false;
        return true;
    }

}
