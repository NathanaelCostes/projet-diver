package com.projetdiver.lesson.controllers;

import com.projetdiver.SuccessPopup;
import com.projetdiver.diver.Diver;
import com.projetdiver.diver.DiverFacade;
import com.projetdiver.lesson.Lesson;
import com.projetdiver.lesson.LessonFacade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

/**
 * Controller for the lesson detail modal.
 * @author Costes
 */
public class LessonDetailModalController {

    @FXML
    private Label detailsLabel;

    @FXML
    private Button deleteButton;

    @FXML
    private Button setButton;

    @FXML
    private Button subscribeButton;

    @FXML
    private Button unsubscribeButton;

    @FXML
    private Label errorLabel;

    private int lesson_id;

    private Stage stage;

    /**
     * Initialize the controller
     */
    public void initialize() {
        // Check if the current diver is an admin or has created the lesson
        Diver currentDiver = DiverFacade.getInstance().getCurrentDiver();
        if (currentDiver != null) {
            boolean isAdmin = currentDiver.isAdmin();
            boolean isLessonCreator = LessonFacade.getInstance().isLessonCreator(currentDiver.getId(), lesson_id);
            boolean isSubscribed = LessonFacade.getInstance().isSubscribedToLesson(currentDiver.getId(), lesson_id);

            deleteButton.setVisible(isAdmin || isLessonCreator);
            setButton.setVisible(isAdmin || isLessonCreator);
            subscribeButton.setVisible(!isLessonCreator && !isSubscribed);
            unsubscribeButton.setVisible(isSubscribed);
        }
    }

    @FXML
    private void deleteLesson(int lesson_id) {

        boolean success = LessonFacade.getInstance().deleteLesson(lesson_id);

        if (success) {
            stage.close();
            LessonController lessonController = getLessonController();
            System.out.println(lessonController);
            if(lessonController != null) {
                lessonController.setLessonListView();
            }
            SuccessPopup.showWithOwner("You have successfully deleted the lesson", stage);
        } else {
            System.out.println("Error deleting the lesson");
            this.errorLabel.setText("Error deleting the lesson");
        }
    }


    // Helper method to get the LessonController instance
    private LessonController getLessonController() {
        Scene scene = detailsLabel.getScene();
        if (scene != null) {
            System.out.println("Scene is not null" + scene.getUserData());
            return (LessonController) scene.getUserData();
        }
        System.out.println("Scene is null");
        return null;
    }

    public void setLesson_id(int lesson_id) {
        this.lesson_id = lesson_id;
        initialize();
    }



    /**
     * Unsubscribe to the lesson
     */
    public void unsubscribeToTheLesson() {
        int currentDiverId = DiverFacade.getInstance().getCurrentDiver().getId();
        boolean success = LessonFacade.getInstance().unsubscribeToTheLesson(this.lesson_id, currentDiverId);

        if(success) {
            stage.close();
            LessonController lessonController = getLessonController();
            if(lessonController != null) {
                lessonController.setLessonListView();
            }

            SuccessPopup.showWithOwner("You have successfully unsubscribed to the lesson", stage);
        } else {
            this.errorLabel.setText("Error unsubscribing to the lesson");
        }
    }

    /**
     * Subscribe to the lesson
     */
    public void subscribeToTheLesson() {

        int currentDiverId = DiverFacade.getInstance().getCurrentDiver().getId();
        boolean success = LessonFacade.getInstance().subscribeToALesson(this.lesson_id, currentDiverId);

        if(success) {
            stage.close();
            LessonController lessonController = getLessonController();
            if(lessonController != null) {
                lessonController.setLessonListView();
            }
            SuccessPopup.showWithOwner("You have successfully subscribed to the lesson", stage);
        } else {
            this.errorLabel.setText("Error subscribing to the lesson");
        }
    }

    /**
     * Close the modal
     */
    public void closeModal() {
        if (stage != null) {
            stage.close();
        }
    }

    /**
     * Set the stage for the controller
     * @param stage The stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Set the details to display in the modal
     * @param details The details to display
     */
    public void setDetails(String details) {
        detailsLabel.setText(details);
    }


    @FXML
    private void handleDeleteButton() {
        deleteLesson(this.lesson_id);
    }

    @FXML
    private void handleUpdateButton() throws IOException {

        InputStream fxmlStream = getClass().getResourceAsStream("/com/projetdiver/lesson-modification-modal.fxml");
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(fxmlStream);

        //Create the LessonModificationController instance
        LessonModificationController modificationController = loader.getController();

        // Set the LessonModificationController instance as userData for the scene
        Scene scene = new Scene(root);
        scene.setUserData(modificationController);

        // Set the lesson details to modify in LessonModificationController
        Lesson lessonToModify = LessonFacade.getInstance().getLessonById(this.lesson_id);
        modificationController.setLessonToModify(lessonToModify);

        // Set the stage in LessonModificationController
        Stage modificationStage = new Stage();
        modificationStage.setScene(scene);
        modificationController.setStage(modificationStage);

        // Show the LessonModificationController in a new stage
        modificationStage.showAndWait();  // Show and wait for the modification stage to close

        // Handle any actions after the modification stage is closed
        handlePostModification();


    }

    // Helper method to handle actions after the modification stage is closed
    private void handlePostModification() {
        // Add any additional actions needed after the modification stage is closed
        // For example, update the LessonListView or perform other operations
        LessonController lessonController = getLessonController();
        if (lessonController != null) {
            lessonController.setLessonListView();
        }

        closeModal();

        // Show a success popup
        SuccessPopup.showWithOwner("You have successfully modified the lesson", stage);
    }
}

