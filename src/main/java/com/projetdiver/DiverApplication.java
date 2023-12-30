package com.projetdiver;

import com.projetdiver.diver.DiverFacade;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

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



        FXMLLoader fxmlLoader = new FXMLLoader(DiverApplication.class.getResource("/com/projetdiver/views/account/profile-view.fxml"));
        System.out.println(fxmlLoader.getLocation());
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("Login!");

        stage.getIcons().add(new Image(DiverApplication.class.getResourceAsStream("/Diving_Portal_Logo.png")));


        stage.setScene(scene);
        stage.setResizable(true);
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