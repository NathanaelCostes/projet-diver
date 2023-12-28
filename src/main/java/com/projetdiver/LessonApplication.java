package com.projetdiver;

import com.projetdiver.diver.DiverFacade;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * JavaFX App - LessonApplication
 * Creates the main scene
 */
public class LessonApplication extends Application {

    /**
     * Start the application
     * @param stage the primary stage for this application
     * @throws IOException if an error occurs during loading of the FXML file
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DiverApplication.class.getResource("lesson-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 750, 750); // Set the root of the scene to the loaded FXML root

        stage.setTitle("Lesson");
        stage.getIcons().add(new Image(Objects.requireNonNull(DiverApplication.class.getResourceAsStream("/Diving_Portal_Logo.png"))));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches the application
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}
