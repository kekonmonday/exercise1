/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise3.data.pane;

import com.mycompany.exercise3.data.Motorway;
import java.io.File;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        ImageView zebraImageView = new ImageView(new Image(new File("src/main/resources/image/zebra.png").toURI().toString()));
        zebraImageView.setLayoutX(750 - 50);
        zebraImageView.setLayoutY(0);
        zebraImageView.setFitHeight(139);
        zebraImageView.setFitWidth(120);
        zebraImageView.toFront();
        motorway.getChildren().add(zebraImageView);
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
