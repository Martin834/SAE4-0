package com.example.sae4_project.QuadTree;

import com.example.sae4_project.Entity.Entity;
import com.example.sae4_project.Entity.Pellet;

import java.util.ArrayList;
import java.util.List;

public class Map  {

    public QuadTree getQuadTree() {
        return quadTree;
    }

    public static double size = 4000;

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

    public void getAllPellet(QuadTree toFind, ArrayList<Pellet> liste) {
        if(toFind.getDepth() == 0){
            for(Entity elm : toFind.getEntities()){
                if(elm instanceof Pellet){
                    liste.add((Pellet) elm);
                }
            }
        } else {
            getAllPellet(toFind.getChild(Orientation.NORTH_WEST), liste);
            getAllPellet(toFind.getChild(Orientation.NORTH_EAST), liste);
            getAllPellet(toFind.getChild(Orientation.SOUTH_WEST), liste);
            getAllPellet(toFind.getChild(Orientation.SOUTH_EAST), liste);
        }

    }

}
