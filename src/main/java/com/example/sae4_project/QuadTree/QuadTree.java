package com.example.sae4_project.QuadTree;

import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.HashMap;

public class QuadTree {

    public static int initialDepth;
    private int depth;
    private Boundry boundry;
    private QuadTree parent;

    private ArrayList<Circle> entities;

    private HashMap<Orientation, QuadTree> children;

    public QuadTree(int depth, QuadTree parent, Coordinate coordinate) {
        this.depth = depth;

        this.parent = parent;
        if(this.parent == null){
            boundry.setHeight(4000);
            boundry.setLength(4000);
        } else {
            boundry.setHeight(this.parent.boundry.getHeight()/2);
            boundry.setLength(this.parent.boundry.getLength()/2);
        }
        boundry.setCoordinate(coordinate);
        this.children = new HashMap<>();
        this.entities = new ArrayList<>();
        this.boundry = new Boundry();


        if(depth > 1){
            this.children.put(Orientation.NORTH_EAST, new QuadTree(depth - 1, this, new Coordinate));
            this.children.put(Orientation.NORTH_WEST, new QuadTree(depth - 1, this));
            this.children.put(Orientation.SOUTH_EAST, new QuadTree(depth - 1, this));
            this.children.put(Orientation.SOUTH_WEST, new QuadTree(depth - 1, this));
        }

        this.addEntity(new Circle());
    }

    public void addEntity(Circle c){
        this.entities.add(c);
    }


}
