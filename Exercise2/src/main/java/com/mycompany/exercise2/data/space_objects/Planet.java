/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise2.data.space_objects;

import com.mycompany.exercise2.data.Coordinate;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Влад
 */
public class Planet extends SpinningSpaceObject {

    public Planet(double weight, String name, SpaceObject centerSpaceObject, double raduis, int side, File image) {
        super(weight, name, centerSpaceObject, raduis, side, image);       
    }
            
}
