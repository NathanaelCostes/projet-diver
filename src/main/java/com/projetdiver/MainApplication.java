package com.projetdiver;

import com.projetdiver.diver.Diver;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

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
        //FXMLLoader fxmlLoader = new FXMLLoader(DiverApplication.class.getResource("/com/projetdiver/views/account/login-view.fxml"));
        //System.out.println(fxmlLoader.getLocation());
        //Convert fxmlLoader URL to String :
        //String fxmlLoaderURL = fxmlLoader.getLocation().toString();
        //System.out.println(fxmlLoaderURL);

        // Attention \\
        FXRouter.when("login", "views/account/login-view.fxml");
        FXRouter.when("profile", "views/account/profile-view.fxml");
        FXRouter.when("register", "views/account/signup-view.fxml");
        FXRouter.when("admin", "views/admin-panel-view.fxml");

        FXRouter.goTo("login");
    }
}
