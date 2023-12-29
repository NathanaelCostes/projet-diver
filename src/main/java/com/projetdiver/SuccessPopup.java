package com.projetdiver;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Popup to display a success message.
 * @author Costes
 */
public class SuccessPopup {

    /**
     * Show a success popup with the given message.
     * @param message The message to display
     * @param ownerStage The stage that owns the popup
     */
    public static void showWithOwner(String message, Stage ownerStage) {
        Stage successPopup = new Stage();
        successPopup.initOwner(ownerStage);
        successPopup.initModality(Modality.WINDOW_MODAL);

        Label label = new Label(message);
        label.setWrapText(true);

        successPopup.setTitle("Success");
        successPopup.setScene(new Scene(label));

        // Dynamically set the size based on the preferred size of the content
        successPopup.sizeToScene();

        // Close the popup after a certain duration (3s here)
        Duration duration = Duration.seconds(3);
        Timeline timeline = new Timeline(new KeyFrame(duration, event -> successPopup.close()));
        timeline.play();

        successPopup.show();
    }
}
