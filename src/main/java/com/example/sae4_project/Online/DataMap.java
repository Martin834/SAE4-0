package com.example.sae4_project.Online;

import com.example.sae4_project.Entity.*;
import com.example.sae4_project.QuadTree.Map;
import javafx.scene.shape.Circle;

import java.io.Serializable;
import java.util.ArrayList;

public class DataMap implements Serializable {

    private double size;
    private ArrayList<DataPellets> dataPellets = new ArrayList<DataPellets>();
    private ArrayList<DataPlayer> dataPlayers = new ArrayList<>();

    public DataMap(){
        Map map = Map.getInstance();

        size = Map.size;

        ArrayList<Pellet> pellets = new ArrayList<>();
        map.getAllPellet(map.getQuadTree(), pellets);
        for(Pellet pellet : pellets){
            dataPellets.add(new DataPellets(
                    pellet.getIdentifier(),
                    pellet.getCircle().getCenterX(),
                    pellet.getCircle().getCenterY(),
                    pellet.getMass()
            ));
        }

        ArrayList<Player> players = new ArrayList<>();

        for(Player player : players){
            DataPlayer dataPlayer = new DataPlayer(
                    player.getIdentifier(),
                    player.getMass()
            );
            for(Circle circle : player.getCirclesList()){
                dataPlayer.getDataCircles().add(
                   new DataCircle(circle.getCenterX(), circle.getCenterY(), circle.getRadius())
                );
            }

        }

    }

    public ArrayList<Pellet> recreatePellets(){
        ArrayList<Pellet> pellets = new ArrayList<>();
        for(DataPellets dataPellet : dataPellets){
            pellets.add(new CreatorPellet().createFromData(dataPellet));
        }
        return pellets;
    }

    public ArrayList<Player> recreatePlayers(){
        ArrayList<Player> players = new ArrayList<>();
        for(DataPlayer dataPlayer : dataPlayers){
            players.add(new CreatorPlayer().createFromData(dataPlayer));
        }
        return players;
    }


}
