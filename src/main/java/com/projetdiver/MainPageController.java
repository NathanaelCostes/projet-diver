package com.projetdiver;

import com.fxrouter.FXRouter;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Controller for the main page of the application
 */
public class MainPageController {

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleMapButton(ActionEvent event) {
        try {
            FXRouter.goTo("spot");
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
            FXRouter.goTo("profile");
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


}
