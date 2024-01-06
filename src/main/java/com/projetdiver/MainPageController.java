package com.projetdiver;

import com.fxrouter.FXRouter;
import com.projetdiver.diver.DiverFacade;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Controller for the main page of the application
 */
public class MainPageController {

    @FXML
    private Button adminButton;

    @FXML
    private ToggleButton sidebarToggleButton;

    @FXML
    private AnchorPane rightPanel;  // Reference to the right panel

    private TranslateTransition sidebarTransition = new TranslateTransition(Duration.millis(250)); // Animation duration

    @FXML
    private void initialize() {
        adminButton.setVisible(DiverFacade.getInstance().getCurrentDiver().isAdmin());
        // Set up right panel animation
        sidebarTransition.setNode(rightPanel);
        sidebarTransition.setFromX(0);
        sidebarTransition.setToX(176); // Adjust the value based on your right panel width
    }

    @FXML
    private void handleMapButton(ActionEvent event) {
        try {
            FXRouter.goTo("map");
            closeSidebar(); // Close the sidebar after navigating to a new page
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleSessionsButton(ActionEvent event) {
        try {
            FXRouter.goTo("session");
            closeSidebar(); // Close the sidebar after navigating to a new page
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleLessonsButton(ActionEvent event) {
        try {
            FXRouter.goTo("lesson");
            closeSidebar(); // Close the sidebar after navigating to a new page
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleContactsButton(ActionEvent event) {
        try {
            FXRouter.goTo("contact");
            closeSidebar(); // Close the sidebar after navigating to a new page
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleCertificateButton() {
        try {
            FXRouter.goTo("certification");
            closeSidebar(); // Close the sidebar after navigating to a new page
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleDiverButton(ActionEvent event) {
        try {
            FXRouter.goTo("profile");
            closeSidebar(); // Close the sidebar after navigating to a new page
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleAdminButton(ActionEvent event) {
        try {
            FXRouter.goTo("admin-panel");
            closeSidebar(); // Close the sidebar after navigating to a new page
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleToggleSidebar(ActionEvent event) {
        // Toggle right panel with animation
        if (sidebarToggleButton.isSelected()) {
            // Code for handling tab open state
            sidebarTransition.setRate(1);
        } else {
            // Code for handling tab close state
            sidebarTransition.setRate(-1);
        }

        sidebarTransition.play();
    }

    private void closeSidebar() {
        sidebarToggleButton.setSelected(false);
        sidebarTransition.setRate(-1);
        sidebarTransition.play();
    }
}
