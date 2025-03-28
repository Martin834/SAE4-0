package com.example.sae4_project.Online;

import java.io.Serializable;

public class DataMessage implements Serializable {

    public int getId() {
        return id;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public boolean isSpace() {
        return space;
    }

    private int id;

    private double dx;
    private double dy;

    private boolean space;

    public DataMessage(int id, double dx, double dy, boolean space){
        this.id = id;
        this.dx = dx;
        this.dy = dy;
        this.space = space;
    }

}
