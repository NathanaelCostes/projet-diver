package com.projetdiver.review.controllers;

import com.projetdiver.SuccessPopup;
import com.projetdiver.review.ReviewFacade;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ReviewCreateController {

    @FXML
    private Label windowLabel;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private ChoiceBox<Integer> typeChoiceBox;

    @FXML
    private Label errorLabel;

    @FXML
    private Button submit;

    ReviewFacade reviewFacade;

    public ReviewCreateController() {
        	reviewFacade = ReviewFacade.getInstance();
    }

    /**
     * Initialize the controller.
     * Set values for the ChoiceBox.
     * In previous view, if button Create was clicked, set windowLabel to "Create a review".
     * If button Modify was clicked, set windowLabel to "Modify your review".
     * @param typeOfReviewToManage the type of review to manage (Spot or Lesson)
     * @param functionToCall the function to call (create or modify)
     * @param idLessonOrSpot the id of the lesson or spot
     */
    public void initialize(String typeOfReviewToManage, int idLessonOrSpot) {
        // Set values for rates (1 to 5)
        typeChoiceBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));


        submit.setOnAction(event -> createReview(typeOfReviewToManage, idLessonOrSpot));


    }

    /**
     * Create a review with the values from the form.
     * Review can be a SpotReview or a LessonReview. Depends on the value of typeOfReviewToManage.
     * @param typeOfReviewToManage the type of review to manage (Spot or Lesson)
     * @param idLessonOrSpot the id of the lesson or spot
     */
    public void createReview(String typeOfReviewToManage, int idLessonOrSpot ) {


        // On récupère les valeurs des champs
        String title = titleTextField.getText();
        String description = descriptionTextArea.getText();
        Integer rating = typeChoiceBox.getValue();

        // On vérifie que le titre et la note sont bien remplis
        if (title.equals("") || rating == null) {
            this.errorLabel.setText("Please fill title and rating");
            return;
        }

        // On l'ajoute à la base de données
        ReviewFacade reviewFacade = ReviewFacade.getInstance();

        boolean isCreated = reviewFacade.createReview(typeOfReviewToManage, idLessonOrSpot, title, description, rating);
        if (isCreated) {
            Stage stage = (Stage) titleTextField.getScene().getWindow();
            stage.close();
            SuccessPopup.showWithOwner("Review added succesfuly!", stage);
        } else {
            this.errorLabel.setText("An error occurred while creating the review");
        }
    }



}
