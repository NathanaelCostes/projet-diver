package com.projetdiver;

import com.projetdiver.diver.DiverFacade;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * JavaFX App - DiverApplication
 * Creates the main scene
 */
public class SessionApplication extends Application {

    private final DiverFacade diverFacade;

    public SessionApplication() {
        this.diverFacade = DiverFacade.getInstance();
    }

    /**
     *
     * @param stage application stage
     * @throws IOException if the fxml file is not found
     */
    @Override
    public void start(Stage stage) throws IOException {
        try {
            diverFacade.login("yasuo.midlaner@riotgames.com", "1234");
            System.out.println("Diver logged in: " + diverFacade.getCurrentDiver());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(SessionApplication.class.getResource("/com/projetdiver/views/session/session-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Session!");

        stage.getIcons().add(new Image(Objects.requireNonNull(SessionApplication.class.getResourceAsStream("/Diving_Portal_Logo.png"))));


        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Launches the application
     * @param args arguments
     */
    public static void main(String[] args) {
        launch();
    }
}