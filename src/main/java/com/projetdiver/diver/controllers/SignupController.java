package com.projetdiver.diver.controllers;

import com.projetdiver.diver.DiverFacade;
import com.projetdiver.diver.exceptions.CreatingAccountFailed;
import com.projetdiver.diver.exceptions.DiverAlreadyExisting;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignupController {

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    public SignupController() {}

    public void onAction() {

        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String password = passwordField.getText();

        if (firstName.equals("") || lastName.equals("") || email.equals("") || password.equals("")){
            this.errorLabel.setText("Please fill all the fields");
            System.out.println("Please fill all the fields");
            return;
        }

        DiverFacade diverFacade = DiverFacade.getInstance();
        try {
            diverFacade.signup(firstName, lastName, email, password); //TODO JUNIT TEST
        } catch (DiverAlreadyExisting diverAlreadyExisting) {
            this.errorLabel.setText(diverAlreadyExisting.getMessage());
        } catch (CreatingAccountFailed creatingAccountFailed) {
            this.errorLabel.setText(creatingAccountFailed.getMessage());
        } catch (Exception e ) {
            this.errorLabel.setText(e.getMessage());
        }

    }
}
