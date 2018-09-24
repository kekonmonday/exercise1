/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exercise2.data.pane;

import com.mycompany.exercise2.data.space_objects.SpaceObject;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Влад
 */
public class SpaceObjectPane extends VBox {
    
    private ImageView imageView;
    private Label label;
    private SpaceObject spaceObject; 
    
    public SpaceObjectPane(SpaceObject spaceObject) {
        this.spaceObject = spaceObject;
        this.imageView = new ImageView();
        this.label = new Label();
        //init imageView
        Image image = new Image(spaceObject.getImage().toURI().toString());
        imageView.setImage(image);
        imageView.setFitHeight(image.getHeight());
        imageView.setFitWidth(image.getWidth());
        //init label
        label.setText(spaceObject.getName());
        label.setPrefHeight(17);
        label.setPrefWidth(imageView.getFitWidth());
        label.setTextFill(Color.WHITE);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
        label.setPadding(new Insets(10, 0, 0, 0));
        //
        setPrefHeight(imageView.getFitHeight() + 18);
        setPrefWidth(imageView.getFitWidth());
        setLayoutX(spaceObject.getPosition().getX() - imageView.getFitHeight() / 2);
        setLayoutY(spaceObject.getPosition().getY() - imageView.getFitWidth() / 2);
        imageView.toFront();
        label.toFront();
        getChildren().add(imageView);
        getChildren().add(label);

    }
    
    public void setVisibleLabel(boolean isVisible) {
        label.setVisible(isVisible);
    }
    
    public ImageView getImageView() {
        return imageView;
    }

    public SpaceObject getSpaceObject() {
        return spaceObject;
    }
    
    
        
}
