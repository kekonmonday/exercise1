/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise6.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mycompany.exercise6.Main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;

/**
 *
 * @author Влад
 */
public class MainController implements Initializable {

	private final FileChooser fileChooser = new FileChooser();
	public static StringBuilder sb = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	void create() {
		Parent parent = null;
		try {
			parent = FXMLLoader.load(MainController.class.getResource("/fxml/create_pane.fxml"));
		} catch (IOException ex) {
			Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
		}
		Main.primaryStage.setScene(new Scene(parent));
	}

	@FXML
	void open() {
		File chosenFile = fileChooser.showOpenDialog(Main.primaryStage);
		sb = new StringBuilder();
		try (FileReader reader = new FileReader(chosenFile)) {
			int c;
			while ((c = reader.read()) != -1) {
				sb.append((char) c);
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		System.out.println(sb.toString());
		create();
	}

}
