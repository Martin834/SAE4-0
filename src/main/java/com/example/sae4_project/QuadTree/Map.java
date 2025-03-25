package com.example.sae4_project.QuadTree;

public class Map {

    private QuadTree quadTree;
    private static Map instance;

    public static Map getInstance() {
        if(instance == null){
            instance = new Map();
        }
        return instance;
    }

    private Map(){
        this.quadTree = new QuadTree(6, null);
    }

}
