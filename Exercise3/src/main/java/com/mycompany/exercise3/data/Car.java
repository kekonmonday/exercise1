/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise3.data;

import com.mycompany.exercise3.data.pane.MotorwayPane;
import java.io.File;

/**
 *
 * @author Влад
 */
public class Car {
    
    private static long sId;
    
    private int id;
    private double speed, oldSpeed;
    private File image;
    private MotorwayPane motorway;
    
    private static final double LOW_SPEED, MEDIUM_SPEED, HIGH_SPEED;
    private static final String YELLOW_CAR, RED_CAR, WHITE_CAR, ORANGE_CAR;
    
    public static double[] speeds;
    public static String[] images;
    
    static {
        sId = 0;
        LOW_SPEED = 3;
        MEDIUM_SPEED = 4;
        HIGH_SPEED = 5;
        YELLOW_CAR = "src/main/resources/image/yellow_car.png";
        RED_CAR = "src/main/resources/image/red_car.png";
        WHITE_CAR = "src/main/resources/image/white_car.png";
        ORANGE_CAR = "src/main/resources/image/orange_car.png";
        speeds = new double[] {LOW_SPEED, MEDIUM_SPEED, HIGH_SPEED};
        images = new String[] {YELLOW_CAR, RED_CAR, WHITE_CAR, ORANGE_CAR};
    }
    
    public Car(MotorwayPane motorway) {
        this.id = (int) sId;
        this.motorway = motorway;
        sId++;
    }
    
    public Car(double speed, File image) {
        this.id = (int) sId;
        this.motorway = motorway;
        this.speed = speed;
        this.oldSpeed = speed;
        this.image = image;
        sId++;
    }

    public double getOldSpeed() {
        return oldSpeed;
    }

    public void setOldSpeed(double oldSpeed) {
        this.oldSpeed = oldSpeed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    } 

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Car other = (Car) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}
