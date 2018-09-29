/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise3.data.pane;

import com.mycompany.exercise3.data.Car;
import com.mycompany.exercise3.data.Motorway;
import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 *
 * @author Влад
 */
public class CarPane extends Pane {
    
    private ImageView imageView;
    private Car car;
    private MotorwayPane motorwayPane;
    private double currentX = -120;
    
    public CarPane(MotorwayPane motorwayPane, Car car) {
        this.motorwayPane = motorwayPane;
        this.imageView = new ImageView();
        this.car = car;
        Image image = new Image(car.getImage().toURI().toString());
        double height = image.getHeight(),
                width = image.getWidth();
        System.out.println(height);
        System.out.println(width);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        imageView.setLayoutX(-120);
        imageView.setLayoutY(0);
        imageView.setImage(image);
        setPrefHeight(height);
        setPrefWidth(width);
        setLayoutX(0);
        setLayoutY(139/2 - height/2);
        getChildren().add(imageView);
        toFront();
    }

    public MotorwayPane getMotorwayPane() {
        return motorwayPane;
    }

    public void setMotorwayPane(MotorwayPane motorwayPane) {
        this.motorwayPane = motorwayPane;
    }

    public double getCurrentX() {
        return currentX;
    }

    public void setCurrentX(double currentX) {
        this.currentX = currentX;
    }
    
    public double next() {
        if (currentX + car.getSpeed() >= Motorway.getWidth() + 120) {
            motorwayPane.getCarPanes().remove(this);
        } else {
            currentX += car.getSpeed();
        }
        return currentX;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.car);
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
        final CarPane other = (CarPane) obj;
        if (!Objects.equals(this.car, other.car)) {
            return false;
        }
        return true;
    }
       
}
