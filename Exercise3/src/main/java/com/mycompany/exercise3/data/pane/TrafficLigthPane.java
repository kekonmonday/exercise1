/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise3.data.pane;

import java.io.File;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 *
 * @author Влад
 */
public class TrafficLigthPane extends Pane{
    
    private double time = 0;
    private boolean isGreen = true;
    
    private ImageView imageView;
    
    private static final double TIME_TO_CHANGE_COLOR = 15000;
    private static final Image RED_COLOR, GREEN_COLOR;
    private final double currentX;
    private double timeRed = 0;
    
    static {
        RED_COLOR = new Image(new File("src/main/resources/image/traffic_ligh_red.png").toURI().toString());
        GREEN_COLOR = new Image(new File("src/main/resources/image/traffic_ligh_green.png").toURI().toString());
    }
    
    public TrafficLigthPane(double currentX) {
        this.currentX = currentX;
        imageView = new ImageView();
        imageView.setImage(GREEN_COLOR);
        imageView.setFitHeight(70);
        imageView.setFitWidth(41);
        imageView.setLayoutX(currentX);
        setPrefHeight(71);
        setPrefWidth(41 + currentX);
        getChildren().add(imageView);
    }

    public double getCurrentX() {
        return currentX;
    }
    
    public void changeColor() {
        if (isGreen) {
            imageView.setImage(RED_COLOR);
            isGreen = false;
        } else {
            timeRed = 0;
            isGreen = true;
            imageView.setImage(GREEN_COLOR);
        }
    }
    
    public void addTime() {
        if (time + 30 >= TIME_TO_CHANGE_COLOR) {
            changeColor();
            time = 0;
        } else {
            time += 30;
        }
    }
    
    public void addTimeRed() {
        timeRed += 30;
    }
    
    public double getTimeRed() {
        return this.timeRed;
    }

    public boolean isGreen() {
        return isGreen;
    }

    public void setIsGreen(boolean isGreen) {
        this.isGreen = isGreen;
    }
 
}
