/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise2.data.space_objects;

import com.mycompany.exercise2.data.Coordinate;
import java.io.File;
import java.util.ArrayList;
import javafx.scene.shape.Circle;

/**
 *
 * @author Влад
 */
public abstract class SpinningSpaceObject extends SpaceObject {
    
    protected SpaceObject centerSpaceObject;
    protected double radius, startPositionX, startPositionY;
    protected ArrayList<Coordinate> route;
    protected int side, currentPosition;
    
    public static final int LEFT_SIDE, RIGTH_SIDE;
    
    static {
        LEFT_SIDE = -1;
        RIGTH_SIDE = 1;
    }

    public SpinningSpaceObject(double weight, String name, SpaceObject centerSpaceObject, double radius, int side, File image) {
        super(null, weight, name, image);
        this.centerSpaceObject = centerSpaceObject;
        this.radius = radius;
        this.side = side;
        this.currentPosition = 0;
        this.startPositionX = centerSpaceObject.position.getX();
        this.startPositionY = centerSpaceObject.position.getY();
        initRoute();
    }
    
    public void next() {
        if (currentPosition == route.size() - 1) {
            currentPosition = 0;
            return;
        }
        currentPosition++;
    }
    
    public Coordinate getCurrentPosition() {
        return route.get(currentPosition);
    }
    
    public void initRoute() { 
        this.route = new ArrayList<Coordinate>();
        double i = (side == SpinningSpaceObject.LEFT_SIDE) ? -359.0 : 0,
               step = weight / 100000, 
               end = (side == SpinningSpaceObject.LEFT_SIDE) ? 0 : 359.0; 
        System.out.println("i " + i);
        System.out.println("step " + step);
        System.out.println("end " + end);
        while (i <= end) {
            System.out.println("" + i);
            route.add(getCootdinate(Math.abs(i)));
            i += step;
        }
        this.position = route.get(0);
        System.out.println(route.toString());
    }
        
    private Coordinate getCootdinate(double degree) {
        double x, y;
        x = - (- startPositionX + radius * Math.cos(Math.toRadians(degree)));
        y = - (- startPositionY + radius * Math.sin(Math.toRadians(degree)));
        return new Coordinate(x, y);
    }

    public SpaceObject getCenterSpaceObject() {
        return centerSpaceObject;
    }
    
    public void setStartPositionX(double startPositionX) {
        this.startPositionX = startPositionX;
    }

    public void setCenterSpaceObject(SpaceObject centerSpaceObject) {
        this.centerSpaceObject = centerSpaceObject;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public ArrayList<Coordinate> getRoute() {
        return route;
    }

    public void setRoute(ArrayList<Coordinate> route) {
        this.route = route;
    }

    public void setStartPositionY(double startPositionY) {
        this.startPositionY = startPositionY;
    }
    
}
