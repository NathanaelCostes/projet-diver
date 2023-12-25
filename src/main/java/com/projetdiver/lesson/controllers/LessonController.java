package com.projetdiver.lesson.controllers;

import com.projetdiver.lesson.Lesson;
import com.projetdiver.lesson.LessonFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LessonController implements Initializable {

    @FXML
    private ListView<HBox> lessonListView;

    @FXML
    private Button addLesson;

    @FXML
    public void setLessonListView() {
        try {
            lessonListView.getItems().clear();

            List<Lesson> lessonList = LessonFacade.getInstance().getAllLessons();

            for (Lesson lesson : lessonList) {
                Button button = new Button("Details");
                button.getStyleClass().add("details-button");
                button.setOnAction(event -> handleButtonAction(event, lesson));

                HBox hbox = new HBox(new Label(lesson.toString()), button);
                lessonListView.getItems().add(hbox);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleButtonAction(ActionEvent event, Lesson lesson) {
        openLessonDetailsModal(event, lesson);
    }

    private void openLessonDetailsModal(ActionEvent event, Lesson lesson) {
        try {
            InputStream fxmlStream = getClass().getResourceAsStream("/com/projetdiver/lesson-detail-modal.fxml");
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fxmlStream);

            LessonDetailModalController detailsController = loader.getController();
            detailsController.setDetails("Lesson Details: " + lesson.toString());
            detailsController.setLesson_id(lesson.getId());

            Stage modalStage = new Stage();
            modalStage.setTitle("Lesson Details");

            Scene scene = new Scene(root);
            scene.setUserData(this);

            modalStage.setScene(scene);
            modalStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            modalStage.initModality(Modality.WINDOW_MODAL);
            detailsController.setStage(modalStage);

            // Show the modal window
            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO Verify that the diver has the teacher level required to create a lesson
    @FXML
    private void openLessonCreationModal(ActionEvent event) {
        try {
            InputStream fxmlStream = getClass().getResourceAsStream("/com/projetdiver/lesson-creation-modal.fxml");
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fxmlStream);

            LessonCreationController creationController = loader.getController();
            // Additional initialization if needed

            Stage modalStage = new Stage();
            modalStage.setTitle("Create Lesson");

            Scene scene = new Scene(root);
            modalStage.setScene(scene);

            // Set the current stage as the owner for the modality
            modalStage.initOwner(((Node) event.getSource()).getScene().getWindow());

            // Prevent interaction with the main window while the modal is open
            modalStage.initModality(Modality.WINDOW_MODAL);

            // Show the modal window
            modalStage.showAndWait();

            // After the lesson creation modal is closed, refresh the lesson list
            setLessonListView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLessonListView();
    }
}
