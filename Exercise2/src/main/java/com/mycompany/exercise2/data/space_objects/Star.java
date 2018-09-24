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
public class Star extends SpaceObject {

    public Star(Coordinate position, double weight, String name, File image) {
        super(position, weight, name, image);
    }
    
}
