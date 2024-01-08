package com.projetdiver;

import javafx.application.Application;
import javafx.stage.Stage;
import com.fxrouter.FXRouter;

import java.io.IOException;

/**
 * Main class of the application
 */
public class MainApplication extends Application {
    /**
     * Main method of the application. Launches the javafx application.
     * @param args arguments of the application
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Starts the javafx application
     * @param stage the stage of the application
     * @throws IOException if the fxml file is not found
     */
    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("Application starting...");

        FXRouter.bind(this, stage);

        FXRouter.when("login", "views/account/login-view.fxml");
        FXRouter.when("profile", "views/account/profile-view.fxml");
        
        FXRouter.when("register", "views/account/signup-view.fxml");

        FXRouter.when("main", "views/mainpage-view.fxml");
        FXRouter.when("lesson", "views/lesson/lesson-view.fxml");
      
        FXRouter.when("admin-panel", "views/admin-panel-view.fxml");

        FXRouter.when("map", "views/map/map-view.fxml");

        FXRouter.when("session", "views/session/session-view.fxml");

        FXRouter.when("lesson", "views/lesson/lesson-view.fxml");

        FXRouter.when("contact", "views/contact/contact-view.fxml");

        FXRouter.when("spot", "views/spot/spot-view.fxml");

        FXRouter.when("club", "views/club/club-view.fxml");

        System.out.println("Routes configured...");
        FXRouter.goTo("map");
    }
}
