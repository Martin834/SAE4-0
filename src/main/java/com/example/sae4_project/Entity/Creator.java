package com.example.sae4_project.Entity;

import com.example.sae4_project.Entity.Entity;

import java.io.Serializable;

public abstract class Creator implements Serializable {

    public abstract Entity create();

}
