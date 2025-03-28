package com.example.sae4_project.Entity;

import com.example.sae4_project.Controller.MenuController;
import com.example.sae4_project.QuadTree.Map;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import java.util.ArrayList;
import java.util.List;

public class Player extends MoveableBody {
    private  String name;

    public Player() {
        super();
        this.setMass(5);
        System.out.println(getName());
        Circle circle = new Circle(400 - this.getMass() / 2, 300 - this.getMass() / 2, 15);
        circlesList.add(circle);
        circle.setFill(Color.RED);
        this.circleComposite = new CircleComposite();
        this.circleComposite.add(new CircleLeaf(circle));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pellet detectPellet(ArrayList<Pellet> all) {
        for (Circle circle : circleComposite.getCircles()) {
            for (Pellet pellet : all) {
                Shape intersect = Circle.intersect(circle, pellet.getCircle());
                if (intersect.getBoundsInLocal().getWidth() != -1) {
                    return pellet;
                }
            }
        }
        return null;
    }

    public List<Circle> getCirclesList() {
        return circleComposite.getCircles();
    }

    public double calculateRadius() {
        return 10 * Math.sqrt(this.getMass());
    }

    public double calculateMaxSpeed() {
        return (1 / this.massProperty().doubleValue()) * 10000;
    }


    public void divideItself() {
        List<CircleComponent> tempCirclesList = new ArrayList<CircleComponent>();
        List<CircleComponent> temptempCirclesList = new ArrayList<CircleComponent>();
        System.out.println("taille "+ circleComposite.getCircles().size());

        if (circleComposite.getCircles().size() >= 2) {
            this.circleComposite.getCircles().remove(1);
           // CircleComponent secondComponent = (CircleComponent) circleComposite.getCircles().get(1);
            //circleComposite.remove(secondComponent);
        }
        for (Circle circle : circleComposite.getCircles()) {
            if (circle.getRadius() >= 10) {
                tempCirclesList.add(new CircleLeaf(circle));
            }
        }
        if (tempCirclesList.isEmpty()) {
            return;
        }
        for (Circle circle : circleComposite.getCircles()) {
            if (circle.getRadius()<10){
                temptempCirclesList.add(new CircleLeaf(circle));
            }
        }

        circleComposite = new CircleComposite();
        double angleIncrement = 2 * Math.PI / tempCirclesList.size();

        for (int i = 0; i < tempCirclesList.size(); i++) {
            CircleComponent component = tempCirclesList.get(i);
            Circle originalCircle = component.getCircles().get(0);
            double newRadius = originalCircle.getRadius() / 2;
            originalCircle.setRadius(newRadius);

            double angle = i * angleIncrement;
            double newX = originalCircle.getCenterX() + Math.cos(angle) * (newRadius * 2);
            double newY = originalCircle.getCenterY() + Math.sin(angle) * (newRadius * 2);

            Circle newCircle = new Circle(newX, newY, newRadius);
            newCircle.setFill(originalCircle.getFill());

            circleComposite.add(new CircleLeaf(originalCircle));
            circleComposite.add(new CircleLeaf(newCircle));
            this.circlesList.add(newCircle);
            for (CircleComponent circle : temptempCirclesList) {
                circleComposite.add(circle);
            }
        }
        adjustCirclePositions();
    }

    private void adjustCirclePositions() {
        List<Circle> circles = circleComposite.getCircles();
        for (int i = 0; i < circles.size(); i++) {
            for (int j = i + 1; j < circles.size(); j++) {
                Circle circle1 = circles.get(i);
                Circle circle2 = circles.get(j);
                double distance = Math.sqrt(Math.pow(circle1.getCenterX() - circle2.getCenterX(), 2) +
                        Math.pow(circle1.getCenterY() - circle2.getCenterY(), 2));
                double minDistance = circle1.getRadius() + circle2.getRadius();
                if (distance < minDistance) {
                    double overlap = minDistance - distance;
                    double angle = Math.atan2(circle2.getCenterY() - circle1.getCenterY(), circle2.getCenterX() - circle1.getCenterX());
                    circle1.setCenterX(circle1.getCenterX() - Math.cos(angle) * overlap / 2);
                    circle1.setCenterY(circle1.getCenterY() - Math.sin(angle) * overlap / 2);
                    circle2.setCenterX(circle2.getCenterX() + Math.cos(angle) * overlap / 2);
                    circle2.setCenterY(circle2.getCenterY() + Math.sin(angle) * overlap / 2);
                }
            }
        }
    }

    public boolean canEat(Player player) {
        //TODO A IMPLEMENTER
        return false;
    }

    public void eat(Map map) {
        //TODO A IMPLEMENTER
    }

    public double calculateEventHorizon() {
        //TODO A IMPLEMENTER
        return 0.0;
    }


    @Override
    public void moveTowards(double posXMouse, double posYMouse, double maxSpeed) {
        circleComposite.moveTowards(posXMouse, posYMouse, maxSpeed);
    }

    @Override
    public void move() {
        circleComposite.move();
        adjustCirclePositions();
    }

    public void rassembling(Circle circle1, Circle circle2) {
        circle1.setRadius(10*Math.sqrt((circle1.getRadius()*circle1.getRadius())/100+(circle2.getRadius()*circle2.getRadius())/100));
    }
        /*
        double totalMassRetain  = 0.0;
        for (Circle circle1 : this.getCirclesList()) {
            totalMassRetain += circle1.getRadius();
        }

        Circle baseCircle = this.circlesList.get(0);
        Circle newCircle = new Circle(baseCircle.getCenterX(), baseCircle.getCenterY(), totalMassRetain);

        this.circlesList.clear();
        this.circlesList.add(newCircle);

        this.circle = newCircle;

        circleComposite.add(new CircleLeaf(newCircle));
    }

    public void makeFatter(Entity entity) {
        this.setMass(this.getMass() +  entity.getMass());
        double radius = this.calculateRadius();
        this.circle.setRadius(radius);
    }*/


}
