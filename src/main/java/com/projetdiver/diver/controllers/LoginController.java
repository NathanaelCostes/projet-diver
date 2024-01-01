package com.projetdiver.diver.controllers;

import com.projetdiver.FXRouter;
import com.projetdiver.diver.DiverFacade;
import com.projetdiver.diver.exceptions.DiverAlreadyLoggedInException;
import com.projetdiver.diver.exceptions.DiverEmailNotFoundException;
import com.projetdiver.diver.exceptions.WrongPasswordException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Controller for the login view, connect the diver when the login button is clicked
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
     * Login the user when the login button is clicked.
     * If there is an error, displays it in the error label
     */
    public void onAction() {

        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        if(email.equals("") || password.equals("")){
            this.errorLabel.setText("Please fill all the fields");
            return;
        }

        DiverFacade diverFacade = DiverFacade.getInstance();
        try {
            diverFacade.login(email, password);


        } catch (DiverAlreadyLoggedInException e) {
            System.out.println("Diver already logged in");
            this.errorLabel.setText("Diver already logged in");
        } catch (DiverEmailNotFoundException e) {
            System.out.println("There is no diver with this email");
            this.errorLabel.setText("There is no diver with this email");
        } catch (WrongPasswordException e) {
            this.errorLabel.setText("Wrong password");
        }

    }
}