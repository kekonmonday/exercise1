/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise3.controller;

import com.mycompany.exercise3.data.Car;
import com.mycompany.exercise3.data.Motorway;
import com.mycompany.exercise3.data.pane.CarPane;
import com.mycompany.exercise3.data.pane.MotorwayPane;
import com.mycompany.exercise3.data.pane.MotorwaySystemPane;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

/**
 *
 * @author Влад
 */
public class MainController implements Initializable {

    @FXML
    private Pane pane;

    private static final Random r = new Random();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Motorway.setWidth(pane.getPrefWidth());
        addMotorway(new Motorway(Motorway.RIGTH_SIDE), pane.getPrefHeight() / 2 - 139 / 2);
    }

    private void addMotorway(Motorway motorway, double y) {
        MotorwayPane motorwayPane = new MotorwayPane(motorway, y);
        MotorwaySystemPane msp = new MotorwaySystemPane(motorwayPane, y);
        pane.getChildren().add(msp);
        Timer timer = new Timer(true);
        timer.schedule(new RigthMotorwayTimerTask(msp), 0, 30);
    }

    private class RigthMotorwayTimerTask extends TimerTask {

        private MotorwaySystemPane msp;

        public RigthMotorwayTimerTask(MotorwaySystemPane msp) {
            this.msp = msp;
        }

        @Override
        public void run() {
            ObservableList<CarPane> carPanes = msp.getMotorway().getCarPanes();
            if (carPanes.size() != 0) {
                msp.getTrafficLigthPane().addTime();
                if (!msp.getTrafficLigthPane().isGreen()) {
                    msp.getTrafficLigthPane().addTimeRed();
                }
                System.err.println(msp.getTrafficLigthPane().isGreen());
                for (int i = carPanes.size() - 1; i >= 0; i--) {
                    if (i != 0) {
                        if (Math.abs(carPanes.get(i).getCurrentX() - carPanes.get(i - 1).getCurrentX()) <= 240) {
                            if (carPanes.get(i).getCar().getSpeed() != 0) {
                                carPanes.get(i).getCar().setSpeed(carPanes.get(i - 1).getCar().getSpeed());
                                carPanes.get(i).getCar().setOldSpeed(carPanes.get(i - 1).getCar().getOldSpeed());
                            }
                        }
                    }
                    if (!msp.getTrafficLigthPane().isGreen()) {
                        if (Math.abs(carPanes.get(i).getCurrentX() - msp.getTrafficLigthPane().getCurrentX() + 130) <= 5) {
                            carPanes.get(i).getCar().setSpeed(0);
                        }
                    } else {
                        carPanes.get(i).getCar().setSpeed(carPanes.get(i).getCar().getOldSpeed());
                    }
                    carPanes.get(i).next();
                    carPanes.get(i).setTranslateX(carPanes.get(i).getCurrentX() - carPanes.get(i).getLayoutX());
                }
            }
            if (msp.getMotorway().getTime() + 30 < msp.getMotorway().getRandomTime() * 1000) {
                msp.getMotorway().addTime(30);
            } else {
                msp.getMotorway().setTime();
                msp.getMotorway().setRandomTime(r.nextInt(5) + 2);
                if (msp.getTrafficLigthPane().isGreen() || msp.getTrafficLigthPane().getTimeRed() <= 4000) {
                    Car car = new Car(Car.speeds[r.nextInt(3)], new File(Car.images[r.nextInt(4)]));
                    CarPane cp = new CarPane(msp.getMotorway(), car);
                    msp.getMotorway().addCarPane(cp);
                }
            }

        }

    }

}
