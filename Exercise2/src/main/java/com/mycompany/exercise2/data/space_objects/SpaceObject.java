/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise2.data.space_objects;

import com.mycompany.exercise2.data.Coordinate;
import java.io.File;

/**
 *
 * @author Влад
 */
public abstract class SpaceObject {
    
    protected double weight;
    protected final String name;
    protected Coordinate position;
    protected File image;

    public SpaceObject(Coordinate position, double weight, String name, File image) {
        this.position = position;
        this.weight = weight;
        this.name = name;
        this.image = image;
    }

    public final File getImage() {
        return image;
    }
   
    public final Coordinate getPosition() {
        return position;
    }

    public final double getWeight() {
        return weight;
    }

    public final String getName() {
        return name;
    }  
    
}
