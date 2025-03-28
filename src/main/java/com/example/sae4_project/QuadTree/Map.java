package com.example.sae4_project.QuadTree;

import com.example.sae4_project.Entity.Enemy;
import com.example.sae4_project.Entity.Entity;
import com.example.sae4_project.Entity.Pellet;
import java.util.ArrayList;

public class Map  {

    public QuadTree getQuadTree() {
        return quadTree;
    }

    public static double size = 4000;

    private QuadTree quadTree;
    private static Map instance;

    public static Map getInstance() {
        if(instance == null){
            instance = new Map();
        }
        return instance;
    }

    /**
     * Constructor for the Map object
     */
    private Map(){
        this.quadTree = new QuadTree(6, null, new Coordinate(0,0));
    }

    /**
     * Finds the QuadTree of minimal depth that contains the coordinates
     * @param toFind
     * @param coordinateToFind
     * @return
     */
    public QuadTree findQuadTree(QuadTree toFind, Coordinate coordinateToFind) {
        if(toFind.getDepth() == 0){
            return toFind;
        }
        return findQuadTree( toFind.getChild(toFind.where(coordinateToFind)) , coordinateToFind);
    }

    /**
     * Retrieves all the pellets of the Map.
     * @param toFind
     * @param liste
     */
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

    /**
     * Retrieves all the enemies of the Map.
     * @param toFind
     * @param enemiesList
     */
    public void getAllEnemies(QuadTree toFind, ArrayList<Enemy> enemiesList) {
        if(toFind.getDepth() == 0) {
            for(Entity elm : toFind.getEntities()) {
                if(elm instanceof Enemy) {
                    enemiesList.add((Enemy) elm);
                }
            }
        } else {
            getAllEnemies(toFind.getChild(Orientation.NORTH_WEST), enemiesList);
            getAllEnemies(toFind.getChild(Orientation.NORTH_EAST), enemiesList);
            getAllEnemies(toFind.getChild(Orientation.SOUTH_WEST), enemiesList);
            getAllEnemies(toFind.getChild(Orientation.SOUTH_EAST), enemiesList);
        }
    }
}
