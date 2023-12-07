package com.projetdiver.login;

import com.projetdiver.diver.DiverFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
            diverFacade.login(email, password); //TODO JUNIT TEST

            diverFacade.getCurrentDiver().getNom();
            diverFacade.getCurrentDiver().getPrenom();
        } catch (Exception e) {
           this.errorLabel.setText(e.getMessage());
        }
    }
}