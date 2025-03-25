package com.example.sae4_project.strategy;

import com.example.sae4_project.Entity.Enemy;
//import com.example.sae4_project.Entity.GameWorld;
import com.example.sae4_project.Entity.Player;

public interface AIBehavior {

    void execute(Enemy enemy);
}
