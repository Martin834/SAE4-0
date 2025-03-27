package com.example.sae4_project.QuadTree;

import javafx.beans.property.SimpleDoubleProperty;

import java.io.Serializable;

public class Camera extends Boundry implements Serializable {

    private SimpleDoubleProperty zoom = new SimpleDoubleProperty();

    public Camera(Coordinate coordinate) {
        super(coordinate);
    }

    public SimpleDoubleProperty zoomProperty() {
        return this.zoom;
    }

    public double getZoom() {
        return this.zoom.doubleValue();
    }
}
