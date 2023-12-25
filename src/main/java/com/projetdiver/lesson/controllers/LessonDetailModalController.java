package com.projetdiver.lesson.controllers;

import com.projetdiver.lesson.LessonFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LessonDetailModalController {

    @FXML
    private Label detailsLabel;

    private int lesson_id;

    private Stage stage;

    public void initialize() {
        // You can initialize the controller, if needed
    }

    public void setDetails(String details) {
        detailsLabel.setText(details);
    }

    @FXML
    private void handleDeleteButton(ActionEvent event) {
        deleteLesson(this.lesson_id);
    }

    @FXML
    private void deleteLesson(int lesson_id) {
        // Close the modal window
        boolean success = LessonFacade.getInstance().deleteLesson(lesson_id); //TODO only a teacher or an admin can delete

        if (success) {
            stage.close();
            LessonController lessonController = getLessonController();
            System.out.println(lessonController);
            if(lessonController != null) {
                lessonController.setLessonListView();
            }
        } else {
            // TODO show an error message
        }
    }


    // Helper method to get the LessonController instance
    private LessonController getLessonController() {
        Scene scene = detailsLabel.getScene();
        if (scene != null) {
            LessonController lessonController = (LessonController) scene.getUserData();
            return lessonController;
        }
        System.out.println("Scene is null");
        return null;
    }

    public void setLesson_id(int lesson_id) {
        this.lesson_id = lesson_id;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

