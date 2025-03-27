package com.example.sae4_project.Online;

import com.example.sae4_project.QuadTree.Map;

import java.io.Serializable;

public class DataMap implements Serializable {

    private double size;

    public DataMap(){
        Map map = Map.getInstance();

        size = Map.size;

    }


}
