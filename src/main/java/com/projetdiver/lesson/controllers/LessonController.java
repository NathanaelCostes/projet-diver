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
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for the lesson list view.
 * @author Costes
 */
public class LessonController implements Initializable {

    @FXML
    private ListView<HBox> lessonListView;

    /**
     * Initialize the lesson list view.
     */
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
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void handleButtonAction(ActionEvent event, Lesson lesson) {
        openLessonDetailsModal(event, lesson);
    }

    private void openLessonDetailsModal(ActionEvent event, Lesson lesson) {
        try {
            InputStream fxmlStream = getClass().getResourceAsStream("/com/projetdiver/views/lesson/lesson-detail-modal.fxml");
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fxmlStream);

            LessonDetailModalController detailsController = loader.getController();
            detailsController.setTitle(lesson.getName());
            System.out.println(lesson.getName());
            detailsController.setDetails(lesson.getDescription());
            detailsController.setDate("From the " + lesson.getStartDate() + " to the " + lesson.getEndDate());
            detailsController.setType(Character.toUpperCase(lesson.getType().toString().charAt(0)) + lesson.getType().toString().substring(1).toLowerCase(Locale.ROOT) + " lesson");

            detailsController.setLesson_id(lesson.getId());

            Stage modalStage = new Stage();
            modalStage.setTitle("Lesson Details");

            Scene scene = new Scene(root);
            scene.setUserData(this);

            String cssPath = Objects.requireNonNull(getClass().getResource("/com/projetdiver/styles/lessonDetailModalStyle.css")).toExternalForm();
            scene.getStylesheets().add(cssPath);

            modalStage.setScene(scene);
            modalStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            modalStage.initModality(Modality.WINDOW_MODAL);
            detailsController.setStage(modalStage);

            // Show the modal window
            modalStage.showAndWait();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //TODO Verify that the diver has the teacher level required to create a lesson
    @FXML
    private void openLessonCreationModal(ActionEvent event) {
        try {
            InputStream fxmlStream = getClass().getResourceAsStream("/com/projetdiver/views/lesson/lesson-creation-modal.fxml");
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fxmlStream);

            Stage modalStage = new Stage();
            modalStage.setTitle("Create Lesson");

            Scene scene = new Scene(root);
            modalStage.setScene(scene);

            String cssPath = Objects.requireNonNull(getClass().getResource("/com/projetdiver/styles/lessonCreationModalStyle.css")).toExternalForm();
            scene.getStylesheets().add(cssPath);

            // Set the current stage as the owner for the modality
            modalStage.initOwner(((Node) event.getSource()).getScene().getWindow());

            // Prevent interaction with the main window while the modal is open
            modalStage.initModality(Modality.WINDOW_MODAL);

            // Show the modal window
            modalStage.showAndWait();

            // After the lesson creation modal is closed, refresh the lesson list
            setLessonListView();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Initialize the controller.
     * @param url The url to initialize
     * @param resourceBundle The resource bundle to initialize
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLessonListView();
    }
}
