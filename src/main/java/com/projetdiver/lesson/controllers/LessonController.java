package com.projetdiver.lesson.controllers;

import com.fxrouter.FXRouter;
import com.projetdiver.diver.Diver;
import com.projetdiver.diver.DiverFacade;
import com.projetdiver.lesson.Lesson;
import com.projetdiver.lesson.LessonFacade;
import com.projetdiver.review.ReviewFacade;
import com.projetdiver.review.controllers.ReviewCreateController;
import com.projetdiver.review.controllers.ReviewListController;
import com.projetdiver.review.controllers.ReviewModifyController;
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
import java.util.*;

/**
 * Controller for the lesson list view.
 * @author Costes
 */
public class LessonController implements Initializable {

    @FXML
    private ListView<HBox> lessonListView;

    @FXML
    private void backToMainPage(){
        try{
            FXRouter.goTo("main");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }



    /**
     * Initialize the lesson list view.
     */
    @FXML
    public void setLessonListView() {
        try {
            lessonListView.getItems().clear();

            List<Lesson> lessonList = LessonFacade.getInstance().getAllLessons();
            Date currentDate = new Date(); // Obtenir la date actuelle

            for (Lesson lesson : lessonList) {
                if (lesson.getStartDate().after(currentDate)) {
                    // Ajout du bouton pour afficher les détails de la lesson
                    Button button = new Button("Details");
                    button.getStyleClass().add("details-button");
                    button.setOnAction(event -> handleButtonAction(event, lesson));

                    // Ajout du bouton pour voir toutes les reviews de la lesson
                    Button allReviewsButton = new Button("Reviews");
                    allReviewsButton.getStyleClass().add("reviews-button");
                    allReviewsButton.setOnAction(event -> openLessonReviewList(event, lesson.getId()));


                    // On récupère le diver connecté
                    Diver diver = DiverFacade.getInstance().getCurrentDiver();

                    HBox hBox = new HBox();

                    // Si le diver connecté est subscribed à la lesson, on affiche un bouton pour mettre une review
                    if (LessonFacade.getInstance().isSubscribedToLesson(diver.getId(), lesson.getId())) {
                        Button reviewButton;
                        System.out.print("hasReviewedLesson : ");
                        boolean hasReviewedLesson = ReviewFacade.getInstance().hasReviewedLesson(diver.getId(), lesson.getId());
                        // Si le diver a déjà mis une review, on affiche un bouton pour modifier la review
                        if (hasReviewedLesson) {
                            reviewButton = new Button("Modify review");
                            reviewButton.getStyleClass().add("review-button");
                            reviewButton.setOnAction(event -> openLessonReviewModify(event, lesson.getId()));
                        }
                        // Sinon on affiche un bouton pour ajouter une review
                        else {
                            reviewButton = new Button("Add review");
                            reviewButton.getStyleClass().add("review-button");
                            reviewButton.setOnAction(event -> openLessonReviewCreation(event,  lesson.getId()));

                        }
                        hBox = new HBox(new Label(lesson.toString()), allReviewsButton, reviewButton, button);
                        lessonListView.getItems().add(hBox);
                    } else {
                        hBox = new HBox(new Label(lesson.toString()), allReviewsButton, button);
                        lessonListView.getItems().add(hBox);
                    }

                    // Ajout d'un spacing entre les éléments de la HBox
                    hBox.setSpacing(10);
                    // Alignement des éléments de la HBox
                    hBox.setAlignment(javafx.geometry.Pos.CENTER);

                }

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

            //String cssPath = Objects.requireNonNull(getClass().getResource("/com/projetdiver/styles/lessonCreationModalStyle.css")).toExternalForm();
            //scene.getStylesheets().add(cssPath);

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


    @FXML
    private void openLessonReviewCreation(ActionEvent e, int idLesson) {
        try{
            InputStream fxmlStream = getClass().getResourceAsStream("/com/projetdiver/views/review/review-create-view.fxml");

            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fxmlStream);

            // On passe en paramètre de l'initialize du controller de la modal la fonction à appeler
            // lorsqu'il va créer une review ( createLessonReview ou createSpotReview : ici createLessonReview)
            ReviewCreateController reviewCreateController = loader.getController();
            reviewCreateController.initialize("lesson", idLesson);

            Stage modalStage = new Stage();
            modalStage.setTitle("Create Review");

            Scene scene = new Scene(root);
            modalStage.setScene(scene);

            //String cssPath = Objects.requireNonNull(getClass().getResource("/com/projetdiver/styles/reviewModifyCreateStyle.css")).toExternalForm();
            //scene.getStylesheets().add(cssPath);

            // Set the current stage as the owner for the modality
            modalStage.initOwner(((Node) e.getSource()).getScene().getWindow());

            // Prevent interaction with the main window while the modal is open
            modalStage.initModality(Modality.WINDOW_MODAL);

            // Show the modal window
            modalStage.showAndWait();

            // After the lesson creation modal is closed, refresh the lesson list
            setLessonListView();

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    private void openLessonReviewModify(ActionEvent e, int idLesson) {
        try{
            InputStream fxmlStream = getClass().getResourceAsStream("/com/projetdiver/views/review/review-modify-view.fxml");

            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fxmlStream);

            // On passe en paramètre de l'initialize du controller de la modal la fonction à appeler
            // lorsqu'il va créer une review ( createLessonReview ou createSpotReview : ici createLessonReview)
            ReviewModifyController reviewModifyController = loader.getController();
            reviewModifyController.initialize("lesson", idLesson);

            Stage modalStage = new Stage();
            modalStage.setTitle("Modify Review");

            Scene scene = new Scene(root);
            modalStage.setScene(scene);

            //String cssPath = Objects.requireNonNull(getClass().getResource("/com/projetdiver/styles/reviewModifyCreateStyle.css")).toExternalForm();
            //scene.getStylesheets().add(cssPath);

            // Set the current stage as the owner for the modality
            modalStage.initOwner(((Node) e.getSource()).getScene().getWindow());

            // Prevent interaction with the main window while the modal is open
            modalStage.initModality(Modality.WINDOW_MODAL);

            // Show the modal window
            modalStage.showAndWait();

            // After the lesson creation modal is closed, refresh the lesson list
            setLessonListView();

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    private void openLessonReviewList(ActionEvent e, int idLesson) {
        try {
            InputStream fxmlStream = getClass().getResourceAsStream("/com/projetdiver/views/review/review-list-view.fxml");

            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fxmlStream);

            ReviewListController reviewListController = loader.getController();
            reviewListController.initialize(idLesson);

            Stage modalStage = new Stage();
            modalStage.setTitle("Review List");

            Scene scene = new Scene(root);
            modalStage.setScene(scene);

            //String cssPath = Objects.requireNonNull(getClass().getResource("/com/projetdiver/styles/reviewModifyCreateStyle.css")).toExternalForm();
            //scene.getStylesheets().add(cssPath);

            // Set the current stage as the owner for the modality
            modalStage.initOwner(((Node) e.getSource()).getScene().getWindow());

            // Prevent interaction with the main window while the modal is open
            modalStage.initModality(Modality.WINDOW_MODAL);

            // Show the modal window
            modalStage.showAndWait();

            // After the lesson creation modal is closed, refresh the lesson list
            setLessonListView();

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
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
