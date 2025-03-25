package com.example.sae4_project.strategy;

import com.example.sae4_project.Application.AgarioApplication;
import com.example.sae4_project.Entity.Enemy;
//import com.example.sae4_project.Entity.MovableBody;
import com.example.sae4_project.Entity.Player;

public class HunterAI implements AIBehavior{
    @Override
    public void execute(Enemy enemy) {
        return;
        /*
        Player closestEntity = AgarioApplication.player;

        AgarioApplication.root.getChildren().forEach(entity ->{
            if (entity instanceof MovableBody){
                MovableBody movingEntity = (MovableBody) entity;
                if (entity != this){
                    if (distanceTo(movingEntity.getPosition()) < enemy.closestEntityDistance) {
                        closestEntityDistance = distanceTo(movingEntity.getPosition());
                        closestEntity = movingEntity;

                    }
                }
            }

        });

        moveToward(closestEntity.getPosition());
        //check if player is colliding with anything
        checkCollision();*/
    }
}
