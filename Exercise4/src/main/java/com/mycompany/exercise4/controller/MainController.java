/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise4.controller;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

/**
 *
 * @author Влад
 */
public class MainController implements Initializable {
    
    @FXML
    private Pane mainPane;
    
    private Pane cityPane, countryPane, timePane;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            cityPane = FXMLLoader.load(MainController.class.getResource("/fxml/city_pane.fxml"));
            countryPane = FXMLLoader.load(MainController.class.getResource("/fxml/country_pane.fxml"));
            timePane = FXMLLoader.load(MainController.class.getResource("/fxml/time_pane.fxml"));
            showCity();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void showCity() {
        if (mainPane.getChildren().size() != 0) {
            mainPane.getChildren().clear();
        }
        mainPane.getChildren().add(cityPane);
    }

    @FXML
    void showCountry() {
        if (mainPane.getChildren().size() != 0) {
            mainPane.getChildren().clear();
        }
        mainPane.getChildren().add(countryPane);

    }

    @FXML
    void showTime() {
        if (mainPane.getChildren().size() != 0) {
            mainPane.getChildren().clear();
        }
        mainPane.getChildren().add(timePane);

    }
    
}
