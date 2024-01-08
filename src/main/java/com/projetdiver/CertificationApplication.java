package com.projetdiver;

import com.projetdiver.diver.Diver;
import com.projetdiver.diver.DiverFacade;
import com.projetdiver.diver.exceptions.DiverAlreadyLoggedInException;
import com.projetdiver.diver.exceptions.DiverEmailNotFoundException;
import com.projetdiver.diver.exceptions.WrongPasswordException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CertificationApplication extends Application {

    /**
     * Start the application
     * @param stage the primary stage for this application
     * @throws IOException if an error occurs during loading of the FXML file
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DiverApplication.class.getResource("/com/projetdiver/views/certification/certification-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 750, 750); // Set the root of the scene to the loaded FXML root

        stage.setTitle("Certification");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws DiverAlreadyLoggedInException, DiverEmailNotFoundException, WrongPasswordException {
        DiverFacade.getInstance().login("zac.jungler@riotgames.com", "1234");
        launch();
    }
}
