package com.example.sae4_project.QuadTree;

public class Map {

    public QuadTree getQuadTree() {
        return quadTree;
    }

    public void setQuadTree(QuadTree quadTree) {
        this.quadTree = quadTree;
    }

    private QuadTree quadTree;
    private static Map instance;

    public static Map getInstance() {
        if(instance == null){
            instance = new Map();
        }
        return instance;
    }

    private Map(){
        this.quadTree = new QuadTree(6, null, new Coordinate(0,0));
    }

    public QuadTree findQuadTree(QuadTree toFind, Coordinate coordinateToFind) {
        if(toFind.getDepth() == 0){
            return toFind;
        }
        return findQuadTree( toFind.getChild(toFind.where(coordinateToFind)) , coordinateToFind);
    }

}
