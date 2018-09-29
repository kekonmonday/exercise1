/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise3.data.pane;

import com.mycompany.exercise3.data.Motorway;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

/**
 *
 * @author Влад
 */
public class MotorwaySystemPane extends VBox{
    
    private MotorwayPane motorway;
    private TrafficLigthPane trafficLigthPane;

    public MotorwaySystemPane(MotorwayPane motorway, double y) {
        this.motorway = motorway;
        this.trafficLigthPane = new TrafficLigthPane(750);
        setPrefWidth(Motorway.getWidth());
        setPrefHeight(139 + 71);
        setLayoutX(0);
        setLayoutY(y);
        getChildren().add(trafficLigthPane);
        getChildren().add(motorway);
    }
    
    public MotorwayPane getMotorway() {
        return motorway;
    }

    public void setMotorway(MotorwayPane motorway) {
        this.motorway = motorway;
    }

    public TrafficLigthPane getTrafficLigthPane() {
        return trafficLigthPane;
    }

    public void setTrafficLigthPane(TrafficLigthPane trafficLigthPane) {
        this.trafficLigthPane = trafficLigthPane;
    }
    
}
