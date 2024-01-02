package com.projetdiver.diver.controllers;

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

    private DiverFacade diverFacade;

    public ProfileController() {
        this.diverFacade = DiverFacade.getInstance();
    }

    /**
     * Initialize the profile page
     * Get the current diver and set the text fields with the diver's informations
     */
    @FXML
    public void initialize() {

        try {
            System.out.println("Diver logged in: " + diverFacade.getCurrentDiver());
            firstNameTextField.setText(this.diverFacade.getCurrentDiver().getFirstName());
            lastNameTextField.setText(this.diverFacade.getCurrentDiver().getLastName());
            emailTextField.setText(this.diverFacade.getCurrentDiver().getEmail());
            passwordField.setText(this.diverFacade.getCurrentDiver().getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Edit the first name field in the profile page
     */
    public void editFirstName() {
        firstNameTextField.setDisable(false);
    }

    /**
     * Edit the last name field in the profile page
     */
    public void editLastName() {
        lastNameTextField.setDisable(false);
    }

    /**
     * Edit the email field in the profile page
     */
    public void editEmail() {
        emailTextField.setDisable(false);
    }

    /**
     * Edit the password field in the profile page
     */
    public void editPassword() {
        passwordField.setDisable(false);
    }


    /**
     * Save the new diver's first name written in the text field
     */
    public void saveFirstName() {
        diverFacade.updateDiverFirstName(firstNameTextField.getText());
        firstNameTextField.setDisable(true);
    }

    /**
     * Save the new diver's last name written in the text field
     */
    public void saveLastName() {
        diverFacade.updateDiverLastName(lastNameTextField.getText());
        lastNameTextField.setDisable(true);

    }

    /**
     * Save the new diver's email written in the text field
     */
    public void saveEmail() {
        diverFacade.updateDiverEmail(emailTextField.getText());
        emailTextField.setDisable(true);

    }

    /**
     * Save the new diver's password written in the text field
     */
    public void savePassword() {
        diverFacade.updateDiverPassword(passwordField.getText());
        passwordField.setDisable(true);

    }
}
