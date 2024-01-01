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
        FXRouter.bind(this, stage);

        FXRouter.when("login", "views/account/login-view.fxml");
        FXRouter.when("profile", "views/account/profile-view.fxml");
        FXRouter.when("register", "views/account/signup-view.fxml");
        FXRouter.when("admin", "views/admin-panel-view.fxml");

        FXRouter.goTo("login");
    }
}
