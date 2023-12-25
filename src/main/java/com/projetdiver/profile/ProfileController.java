package com.projetdiver.profile;

import com.projetdiver.diver.DiverFacade;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
public class ProfileController {

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField passwordField;

    public ProfileController() {}

    public void editFirstName() {
        firstNameTextField.setDisable(false);


    }

    public void editLastName() {
        lastNameTextField.setDisable(false);


    }

    public void editEmail() {
        emailTextField.setDisable(false);


    }

    public void editPassword() {
        passwordField.setDisable(false);


    }

    public void saveFirstName() {
        firstNameTextField.setDisable(true);

    }

    public void saveLastName() {
        lastNameTextField.setDisable(true);

    }

    public void saveEmail() {
        emailTextField.setDisable(true);

    }

    public void savePassword() {
        passwordField.setDisable(true);

    }
}
