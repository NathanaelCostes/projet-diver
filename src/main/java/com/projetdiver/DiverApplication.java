package com.projetdiver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App - DiverApplication
 * Creates the main scene
 */
public class DiverApplication extends Application {

    /**
     *
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DiverApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        stage.setTitle("Login!");
        //TODO mettre logo
        try {
            Image icon = new Image(getClass().getResourceAsStream("Diving_Portal_Logo.png"));
            if(icon == null){
                System.out.println("Image not found : " + getClass().getResourceAsStream("Diving_Portal_Logo.png"));
            }
            stage.getIcons().add(icon);
        } catch (Exception e) {
            System.out.println("Error loading logo: " + e.getMessage());
        }

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Launches the application
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}