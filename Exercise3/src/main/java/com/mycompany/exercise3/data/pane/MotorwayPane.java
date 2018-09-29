/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise3.data.pane;

import com.mycompany.exercise3.data.Motorway;
import com.sun.prism.paint.Color;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;

/**
 *
 * @author Влад
 */
public class MotorwayPane extends Pane {

    private Motorway motorway;
    private ObservableList<CarPane> carPanes;
    private double time, randomTime = 1;
    private boolean needAdd = false, needDelete = false;

    public MotorwayPane(Motorway motorway, double y) {
        this.motorway = motorway;
        time = 0;
        setPrefWidth(Motorway.getWidth());
        setPrefHeight(139);
        carPanes = FXCollections.observableArrayList();
        carPanes.addListener(new MotorwayListener(this));
        getStyleClass().clear();
        getStyleClass().add("motorway");
        
        toFront();
    }

    public ObservableList<CarPane> getCarPanes() {
        return carPanes;
    }

    public void setCarPanes(ObservableList<CarPane> carPanes) {
        this.carPanes = carPanes;
    }

    public double getRandomTime() {
        return randomTime;
    }

    public void setRandomTime(double randomTime) {
        this.randomTime = randomTime;
    }
    
    public void setTime() {
        this.time = 0;
    }
    
    public double getTime() {
        return this.time;
    }
    
    public void addTime(double addTime) {
        this.time += addTime;
    }

    public Motorway getMotorway() {
        return motorway;
    }

    public void setMotorway(Motorway motorway) {
        this.motorway = motorway;
    }

    public void addCarPane(CarPane carPane) {
        if (carPane != null) {
            carPanes.add(carPane);
        }
    }

    public void removeCarPane() {
        if (motorway.getSide() == Motorway.LEFT_SIDE) {
            carPanes.remove(0);
        } else if (motorway.getSide() == Motorway.RIGTH_SIDE) {
            carPanes.remove(carPanes.size() - 1);
        }
    }

    public void removeCarPane(CarPane carPane) {
        if (carPane != null) {
            carPanes.remove(carPane);
        }
    }

    private class MotorwayListener implements ListChangeListener<CarPane> {

        private MotorwayPane motorwayPane;

        MotorwayListener(MotorwayPane motorwayPane) {
            this.motorwayPane = motorwayPane;
        }

        @Override
        public void onChanged(Change<? extends CarPane> c) {
            c.next();
            if (c.wasAdded()) {
                Platform.runLater(() -> {
                    motorwayPane.getChildren().add(c.getAddedSubList().get(c.getAddedSize() - 1));
                });
            }
            if (c.wasRemoved()) {
                Platform.runLater(() -> {
                    motorwayPane.getChildren().remove(c.getRemoved().get(c.getRemovedSize() - 1));
                });
            }
        }
    }

}
