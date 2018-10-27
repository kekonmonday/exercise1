/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Влад
 */
public class Main extends Application {
    
    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        Parent parent = FXMLLoader.load(Main.class.getResource("/fxml/main.fxml"));
        Main.primaryStage.setScene(new Scene(parent));
        Main.primaryStage.show();
    }
    
    public static void main(String... args) {
        launch(args);
    }
    
}
