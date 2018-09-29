/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise2.controller;

import com.mycompany.exercise2.data.Coordinate;
import com.mycompany.exercise2.data.pane.SatelliteSystemPane;
import com.mycompany.exercise2.data.pane.SpaceObjectPane;
import com.mycompany.exercise2.data.space_objects.Planet;
import com.mycompany.exercise2.data.space_objects.Satellite;
import com.mycompany.exercise2.data.space_objects.SpaceObject;
import com.mycompany.exercise2.data.space_objects.SpinningSpaceObject;
import com.mycompany.exercise2.data.space_objects.Star;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author Влад
 */
public class MainController implements Initializable {

    @FXML
    private Pane pane;
    private ArrayList<Pane> planetPanes;
    private ArrayList<Circle> circles;
    private SpaceObject centerObject;
    private SpaceObjectPane starPane;
    private boolean isVisibleLabel = true, isVisibleCircle = true;
    private Timer timer;
    private long speed;
    private ChangePositionTimerTask changePositionTimerTask;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        planetPanes = new ArrayList<Pane>();
        circles = new ArrayList<Circle>();
        addStar(new Star(new Coordinate(pane.getPrefWidth() / 2, pane.getPrefHeight() / 2), 100000, "Солнце", new File("src/main/resources/image/sun.png")));
        Planet venera = new Planet(50000, "Венера", centerObject, 200, SpinningSpaceObject.RIGTH_SIDE, new File("src/main/resources/image/venera.png"));
        Planet mercury = new Planet(200000, "Меркурій", centerObject, 120, SpinningSpaceObject.RIGTH_SIDE, new File("src/main/resources/image/mercury.png"));
        Planet earth = new Planet(100000, "Земля", centerObject, 300, SpinningSpaceObject.LEFT_SIDE, new File("src/main/resources/image/earth.png"));
        //Planet mars = new Planet(140000, "Марс", centerObject, 420, SpinningSpaceObject.RIGTH_SIDE, new File("src/main/resources/image/mars.png"));
        Satellite moon = new Satellite(200000, "Луна", earth, 75, SpinningSpaceObject.LEFT_SIDE, new File("src/main/resources/image/moon.png"));
        addSatelliteSystem(earth, moon);
        addPlanet(venera);
        addPlanet(mercury);
        //addPlanet(mars);

        timer = new Timer(true);
        speed = 50;
        changePositionTimerTask = new ChangePositionTimerTask();
        timer.schedule(changePositionTimerTask, 0, speed);
    }

    private void addStar(Star star) {
        centerObject = star;
        starPane = new SpaceObjectPane(centerObject);
        pane.getChildren().add(starPane);
    }

    private void addPlanet(Planet planet) {
        SpaceObjectPane planetPane = new SpaceObjectPane(planet);
        planetPanes.add(planetPane);
        Circle circle = new Circle(planet.getRadius(), Color.TRANSPARENT);
        circle.toBack();
        circle.setCenterX(centerObject.getPosition().getX());
        circle.setCenterY(centerObject.getPosition().getY());
        circle.setStroke(Color.RED);
        circles.add(circle);
        pane.getChildren().add(circle);
        pane.getChildren().add(planetPane);
    }

    private void addSatelliteSystem(Planet planet, Satellite satellite) {
        SatelliteSystemPane satelliteSystemPane = new SatelliteSystemPane(planet, satellite);
        planetPanes.add(satelliteSystemPane);
        Circle circle = new Circle(planet.getRadius(), Color.TRANSPARENT);
        circle.toBack();
        circle.setCenterX(centerObject.getPosition().getX());
        circle.setCenterY(centerObject.getPosition().getY());
        circle.setStroke(Color.RED);
        circles.add(circle);
        pane.getChildren().add(circle);
        pane.getChildren().add(satelliteSystemPane);
    }

    @FXML
    private void changeVisibleSignatures() {
        isVisibleLabel = !isVisibleLabel;
        starPane.setVisibleLabel(isVisibleLabel);
        for (int i = 0; i < planetPanes.size(); i++) {
            if (planetPanes.get(i) instanceof SpaceObjectPane) {
                ((SpaceObjectPane) planetPanes.get(i)).setVisibleLabel(isVisibleLabel);
            } else if (planetPanes.get(i) instanceof SatelliteSystemPane) {
                ((SatelliteSystemPane) planetPanes.get(i)).getSatellitePane().setVisibleLabel(isVisibleLabel);
                ((SatelliteSystemPane) planetPanes.get(i)).getPlanetPane().setVisibleLabel(isVisibleLabel);
            }
        }
    }

    @FXML
    private void changeVisibleCircles() {
        isVisibleCircle = !isVisibleCircle;
        for (int i = 0; i < planetPanes.size(); i++) {
            if (planetPanes.get(i) instanceof SpaceObjectPane) {
                circles.get(i).setVisible(isVisibleCircle);
            } else if (planetPanes.get(i) instanceof SatelliteSystemPane) {
                circles.get(i).setVisible(isVisibleCircle);
                ((SatelliteSystemPane) planetPanes.get(i)).setVisibleCircle(isVisibleCircle);
            }
        }
    }
    
    @FXML
    private void increaseSpeed() {
        if (speed - 10 >= 20) {
            speed -= 10;
            timer.cancel();
            timer.purge();
            timer = new Timer();
            changePositionTimerTask = new ChangePositionTimerTask();
            timer.schedule(changePositionTimerTask, 0, speed);
        }
    }
    
    @FXML
    private void reduceSpeed() {
        if (speed + 10 <= 100) {
            speed += 10;
            timer.cancel();
            timer.purge();
            timer = new Timer();
            changePositionTimerTask = new ChangePositionTimerTask();
            timer.schedule(changePositionTimerTask, 0, speed);
        }
    }
    
    private class ChangePositionTimerTask extends TimerTask {

        @Override
        public void run() {
            for (int i = 0; i < planetPanes.size(); i++) {
                if (planetPanes.get(i) instanceof SpaceObjectPane) {
                    SpaceObjectPane spaceObjectPane = (SpaceObjectPane) planetPanes.get(i);
                    Planet planet = (Planet) spaceObjectPane.getSpaceObject();
                    planet.next();
                    System.out.println("-" + (planet.getCurrentPosition().getX() - spaceObjectPane.getImageView().getFitHeight() / 2 - spaceObjectPane.getLayoutX()));
                    spaceObjectPane.setTranslateX(planet.getCurrentPosition().getX() - spaceObjectPane.getImageView().getFitHeight() / 2 - spaceObjectPane.getLayoutX());
                    spaceObjectPane.setTranslateY(planet.getCurrentPosition().getY() - spaceObjectPane.getImageView().getFitWidth() / 2 - spaceObjectPane.getLayoutY());
                } else if (planetPanes.get(i) instanceof SatelliteSystemPane) {
                    SatelliteSystemPane spaceObjectPane = (SatelliteSystemPane) planetPanes.get(i);
                    Planet planet = (Planet) spaceObjectPane.getPlanet();
                    Satellite satellite = (Satellite) spaceObjectPane.getSatellite();
                    SpaceObjectPane satellitePane = spaceObjectPane.getSatellitePane();
                    planet.next();
                    satellite.next();
                    spaceObjectPane.setTranslateX(planet.getCurrentPosition().getX() - spaceObjectPane.getHeight() / 2 - spaceObjectPane.getLayoutX());
                    spaceObjectPane.setTranslateY(planet.getCurrentPosition().getY() - spaceObjectPane.getWidth() / 2 - spaceObjectPane.getLayoutY());
                    satellitePane.setTranslateX(satellite.getCurrentPosition().getX() - satellitePane.getImageView().getFitHeight() / 2 - satellitePane.getLayoutX());
                    satellitePane.setTranslateY(satellite.getCurrentPosition().getY() - satellitePane.getImageView().getFitWidth() / 2 - satellitePane.getLayoutY());
                }
            }
        }
    }

}
