package com.projetdiver;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

import java.io.IOException;


/**
 * Controller for the main page of the application
 */



public class MainPageController {

    @FXML
    private ToggleButton sidebarToggleButton;

    @FXML
    private void handleMapButton(ActionEvent event) {
        try {
            FXRouter.goTo("map");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleSessionsButton(ActionEvent event) {
        try {
            FXRouter.goTo("session");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleLessonsButton(ActionEvent event) {
        try {
            FXRouter.goTo("lesson");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleContactsButton(ActionEvent event) {
        try {
            FXRouter.goTo("contact");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleClubButton(ActionEvent event) {
        try {
            FXRouter.goTo("club");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleDiverButton(ActionEvent event) {
        try {
            FXRouter.goTo("diver-panel");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleAdminButton(ActionEvent event) {
        try {
            FXRouter.goTo("admin-panel");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleToggleSidebar(ActionEvent event) {
        // Add logic to handle the open/close tab button here
        if (sidebarToggleButton.isSelected()) {
            // Code for handling tab open state
            System.out.println("Tab Opened");
        } else {
            // Code for handling tab close state
            System.out.println("Tab Closed");
        }
    }
}
