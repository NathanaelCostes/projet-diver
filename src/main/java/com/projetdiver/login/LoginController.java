package com.projetdiver.login;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    @FXML
    private Label errorLabel;

    /**
     * Default constructor
     */
    public LoginController() {}

    /**
     * Login the user
     */
    public void onAction() {

        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        DiverFacade diverFacade = DiverFacade.getInstance();
        try {
            diverFacade.login(email, password);
        } catch (Exception e) {
           this.errorLabel.setText(e.getMessage());
        }
    }
}