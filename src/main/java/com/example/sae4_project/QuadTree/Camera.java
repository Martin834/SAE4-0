package com.example.sae4_project.QuadTree;

import javafx.beans.property.SimpleDoubleProperty;

public class Camera extends Boundry{

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
