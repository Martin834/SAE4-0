package com.example.sae4_project.QuadTree;

import com.example.sae4_project.Entity.CreatorEnemy;
import com.example.sae4_project.Entity.Entity;
import com.example.sae4_project.Entity.Pellet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class QuadTree  {

    public int getDepth() {
        return depth;
    }

    private int depth;
    private Boundry boundry;
    private QuadTree parent;

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    private ArrayList<Entity> entities = new ArrayList<>();

    private HashMap<Orientation, QuadTree> children;

    /**
     * Constructor for the QuadTree object.
     * Builds the QuadTree recursively
     * @param depth
     * @param parent
     * @param coordinate
     */
    public QuadTree(int depth, QuadTree parent, Coordinate coordinate) {
        this.depth = depth;

        this.parent = parent;

        this.boundry = new Boundry(coordinate);

        if(this.parent == null){
            boundry.setHeight(Map.size);
            boundry.setLength(Map.size);
            boundry.setLength(Map.size);
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
            if (baobab == 2) {
                this.entities.add(new Pellet(
                        r.nextDouble(boundry.getCoordinate().getX(), boundry.getCoordinate().getX() + boundry.getLength()),
                        r.nextDouble(boundry.getCoordinate().getY(), boundry.getCoordinate().getY() + boundry.getHeight())
                ));
            baobab = r.nextInt(20000);
            }
            baobab = r.nextInt(200);
            if (baobab == 2) {
                this.entities.add(new CreatorEnemy().create(r.nextDouble(boundry.getCoordinate().getX(), boundry.getCoordinate().getX() + boundry.getLength()),
                        r.nextDouble(boundry.getCoordinate().getY(), boundry.getCoordinate().getY() + boundry.getHeight())));
            }
        }
    }

    /**
     *
     * @return boundry of the QuadTree
     */
    public Boundry getBoundry() {
        return boundry;
    }

    /**
     * Check if the coordinates are located in the QuadTree
     * @param coordinateToFind
     * @return
     */
    public Orientation where(Coordinate coordinateToFind){

        Orientation orientation = Orientation.NONE;

        for(Orientation key : this.children.keySet()){

            if(this.children.get(key).boundry.contains(coordinateToFind)){
                orientation = key;
            }
        }

        return orientation;
    }

    /**
     * Get the QuadTree located around this QuadTree according to the specified orientation.
     * @param orientation
     * @return
     */
    public QuadTree getChild(Orientation orientation){
        return this.children.get(orientation);
    }


    /**
     *
     * @return toString of QuadTree
     */
    @Override
    public String toString() {
        String texte = this.getBoundry().getCoordinate().getX() + " : " + this.getBoundry().getCoordinate().getY();
        return texte;
    }
}
