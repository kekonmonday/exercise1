/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise3.data;

import java.util.ArrayList;
import javafx.collections.ObservableList;

/**
 *
 * @author Влад
 */
public class Motorway {
    
    private int side;
    private static double width;
    
    public static final int LEFT_SIDE, RIGTH_SIDE;
    
    static {
        LEFT_SIDE = -1;
        RIGTH_SIDE = 1;
    }
    
    public Motorway(int side) {
        this.side = side;
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    public static double getWidth() {
        return width;
    }

    public static void setWidth(double width) {
        Motorway.width = width;
    }
    
    
       
}
