/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise2.data.pane;

import com.mycompany.exercise2.data.space_objects.SpaceObject;
import com.mycompany.exercise2.data.space_objects.SpinningSpaceObject;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author Влад
 */
public class SatelliteSystemPane extends Pane{
    
    private SpaceObjectPane planetPane, satellitePane;
    private SpinningSpaceObject planet, satellite;
    private Circle circle;
    
    public SatelliteSystemPane(SpinningSpaceObject planet, SpinningSpaceObject satellite) {
        this.planetPane = new SpaceObjectPane(planet);
        this.satellitePane = new SpaceObjectPane(satellite);
        this.planet = planet;
        double heigth = (satellite.getRadius() + satellitePane.getImageView().getFitHeight() / 2) * 2;
        double width = (satellite.getRadius() + satellitePane.getImageView().getFitWidth() / 2) * 2;
        setPrefHeight(heigth);
        setPrefWidth(width);
        setLayoutX(planet.getPosition().getX() - heigth / 2 );
        setLayoutY(planet.getPosition().getY() - width / 2);
        planetPane.setLayoutX(width / 2 - planetPane.getImageView().getFitWidth() / 2);
        planetPane.setLayoutY(heigth / 2 - planetPane.getImageView().getFitHeight() / 2);
        satellitePane.setLayoutX(0);
        satellitePane.setLayoutY(width / 2 - satellitePane.getImageView().getFitHeight() / 2);
        satellite.setStartPositionX(width / 2);
        satellite.setStartPositionY(heigth / 2);
        satellite.initRoute();
        this.satellite = satellite;     
        circle = new Circle(satellite.getRadius(), Color.TRANSPARENT);
        circle.toBack();
        circle.setCenterX(width / 2);
        circle.setCenterY(heigth / 2);
        circle.setStroke(Color.RED);
        getChildren().add(circle);
        getChildren().add(planetPane);
        getChildren().add(satellitePane);
    }

    public SpaceObjectPane getPlanetPane() {
        return planetPane;
    }

    public void setPlanetPane(SpaceObjectPane planetPane) {
        this.planetPane = planetPane;
    }

    public SpaceObjectPane getSatellitePane() {
        return satellitePane;
    }

    public void setSatellitePane(SpaceObjectPane satellitePane) {
        this.satellitePane = satellitePane;
    }

    public SpinningSpaceObject getPlanet() {
        return planet;
    }

    public void setPlanet(SpinningSpaceObject planet) {
        this.planet = planet;
    }

    public SpinningSpaceObject getSatellite() {
        return satellite;
    }

    public void setSatellite(SpinningSpaceObject satellite) {
        this.satellite = satellite;
    }
    
    public void setVisibleCircle(boolean visible) {
        circle.setVisible(visible);
    }
    
}
