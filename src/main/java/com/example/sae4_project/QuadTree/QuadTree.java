package com.example.sae4_project.QuadTree;

import com.example.sae4_project.Entity.*;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class QuadTree {

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    private int depth;
    private Boundry boundry;
    private QuadTree parent;

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    /*public ArrayList<SpecialPellets> getSpecial() {
        return sp;
    }*/

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    private ArrayList<Entity> entities = new ArrayList<>();

    private HashMap<Orientation, QuadTree> children;

    public QuadTree(int depth, QuadTree parent, Coordinate coordinate) {
        this.depth = depth;

        this.parent = parent;

        this.boundry = new Boundry(coordinate);
        if (this.parent == null) {
            boundry.setHeight(4000);
            boundry.setLength(4000);
        } else {
            boundry.setHeight(this.parent.boundry.getHeight() / 2);
            boundry.setLength(this.parent.boundry.getLength() / 2);
        }

        this.children = new HashMap<>();

        if (depth > 0) {
            this.children.put(Orientation.NORTH_EAST, new QuadTree(depth - 1, this, new Coordinate(coordinate.getX(), coordinate.getY() + this.boundry.getLength() / 2)));
            this.children.put(Orientation.NORTH_WEST, new QuadTree(depth - 1, this, coordinate));
            this.children.put(Orientation.SOUTH_EAST, new QuadTree(depth - 1, this, new Coordinate(coordinate.getX() + this.boundry.getLength() / 2, coordinate.getY() + this.boundry.getLength() / 2)));
            this.children.put(Orientation.SOUTH_WEST, new QuadTree(depth - 1, this, new Coordinate(coordinate.getX() + this.boundry.getLength() / 2, coordinate.getY())));
        } else {
            Random r = new Random();
            int baobab = r.nextInt(3);
            if (baobab == 1) {
                this.entities.add(new Pellet(
                        r.nextDouble(boundry.getCoordinate().getX(), boundry.getCoordinate().getX() + boundry.getLength()),
                        r.nextDouble(boundry.getCoordinate().getY(), boundry.getCoordinate().getY() + boundry.getHeight())
                ));
            }
            baobab = r.nextInt(100);
            if (baobab == 2) {
                this.entities.add(new CreatorEnemy().create(r.nextDouble(boundry.getCoordinate().getX(), boundry.getCoordinate().getX() + boundry.getLength()),
                        r.nextDouble(boundry.getCoordinate().getY(), boundry.getCoordinate().getY() + boundry.getHeight())));
            }
        }
    }

    public Boundry getBoundry() {
        return boundry;
    }

    public void setBoundry(Boundry boundry) {
        this.boundry = boundry;
    }

    public QuadTree getParent() {
        return parent;
    }

    public void setParent(QuadTree parent) {
        this.parent = parent;
    }


    public Orientation where(Coordinate coordinateToFind){

        Orientation orientation = Orientation.NONE;

        for(Orientation key : this.children.keySet()){

            if(this.children.get(key).boundry.contains(coordinateToFind)){
                orientation = key;
            }
        }

        return orientation;
    }

    public QuadTree getChild(Orientation orientation){
        return this.children.get(orientation);
    }


    @Override
    public String toString() {
        String texte = this.getBoundry().getCoordinate().getX() + " : " + this.getBoundry().getCoordinate().getY();
        return texte;
    }
}
