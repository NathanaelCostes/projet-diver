package com.projetdiver.login;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.*;

/**
 * 
 */
public class LoginController {

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField passwordTextField;


    /**
     * Default constructor
     */
    public LoginController() {}

    /**
     * 
     */
    public void onAction() {
        // Access the values from the text fields
        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        // TODO: Implement your login logic here using the email and password values
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
    }
}