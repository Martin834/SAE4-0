package com.example.sae4_project.QuadTree;

import java.util.HashMap;

public class QuadTree {

    public static int initialDepth;
    private int depth;
    private Boundry boundry;
    private QuadTree parent;

    HashMap<Orientation, QuadTree> children;

    public QuadTree(int depth, QuadTree parent) {
        this.depth = depth;
        this.parent = parent;
        this.children = new HashMap<>();

        if(depth > 1){
            this.children.put(Orientation.NORTH_EAST, new QuadTree(depth - 1, this));
            this.children.put(Orientation.NORTH_WEST, new QuadTree(depth - 1, this));
            this.children.put(Orientation.SOUTH_EAST, new QuadTree(depth - 1, this));
            this.children.put(Orientation.SOUTH_WEST, new QuadTree(depth - 1, this));
        }

    }


}
