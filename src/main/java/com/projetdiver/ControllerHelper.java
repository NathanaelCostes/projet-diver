package com.projetdiver;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public interface ControllerHelper {

    //Javadoc for the interface ControllerHelper

    /**
     * Create a separator
     * @return the separator
     */
    public default Label separator(){
        Label separator = new Label(" | ");
        separator.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        return separator;
    }

    /**
     * Create a button
     * @param text the text of the button
     * @param color the color of the button
     * @return the button
     */
    public default Button createButton(String text, String color){
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: 'Roboto';");
        return button;
    }

    /**
     * Create a label
     * @param text the text of the label
     * @return the label
     */
    public default Label createLabel(String text){
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: 'Roboto';");
        return label;
    }

    /**
     * Create a text field
     * @param text the text of the text field
     * @return the text field
     */
    public default TextField createTextField(String text){
        TextField textField = new TextField(text);
        textField.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: 'Roboto';");
        return textField;
    }

    /**
     * Get the color
     * @param color the color or the type of element
     * @return the color
     */
    public default String getColor(String color){
        switch (color){
            case "navbar":
                return "#2d95a1";
            case "sidebar", "validation":
                return "#3ebbbe";
            case "suppression":
                return "#D94640";
            case "modification":
                return "#BAB8B7";
            case "information":
                return "#ff7f00";
            default:
                return "#ffffff";
        }
    }
}
