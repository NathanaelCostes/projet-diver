package com.projetdiver.spot.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public interface ControllerHelper {
    public default Label separator(){
        Label separator = new Label(" | ");
        separator.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        return separator;
    }

    public default Button createButton(String text, String color){
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: 'Roboto';");
        return button;
    }

    public default Label createLabel(String text){
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: 'Roboto';");
        return label;
    }

    public default TextField createTextField(String text){
        TextField textField = new TextField(text);
        textField.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: 'Roboto';");
        return textField;
    }
}