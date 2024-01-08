package com.projetdiver.review.controllers;

import com.projetdiver.SuccessPopup;
import com.projetdiver.diver.Diver;
import com.projetdiver.diver.DiverFacade;
import com.projetdiver.review.Review;
import com.projetdiver.review.ReviewFacade;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ReviewModifyController {

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
    private Button update;

    @FXML
    private Button delete;

    ReviewFacade reviewFacade;

    public ReviewModifyController() {
        	reviewFacade = ReviewFacade.getInstance();
    }

    /**
     * Initialize the controller.
     * Set values for the ChoiceBox.
     * In previous view, if button Create was clicked, set windowLabel to "Create a review".
     * If button Modify was clicked, set windowLabel to "Modify your review".
     * @param typeOfReviewToManage the type of review to manage (Spot or Lesson)
     * @param idLessonOrSpot the id of the lesson or spot
     */
    public void initialize(String typeOfReviewToManage , int idLessonOrSpot) {
        // Set values for rates (1 to 5)
        typeChoiceBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));

        // On récupère le diver connecté
        Diver diver = DiverFacade.getInstance().getCurrentDiver();

        // Récupération de la review à modifier
        Review reviewToModify;
        if (typeOfReviewToManage.equals("spot")) {
            windowLabel.setText("Modify your spot review");
        } else {
            windowLabel.setText("Modify your lesson review");
            reviewToModify = reviewFacade.getReviewByLessonIdAndDiverId(idLessonOrSpot, diver.getId());
            titleTextField.setText(reviewToModify.getTitle());
            descriptionTextArea.setText(reviewToModify.getDescription());
            typeChoiceBox.setValue(reviewToModify.getRating());
            // On applique le onAction au bouton submit
            update.setOnAction(event -> modifyReview(reviewToModify ,typeOfReviewToManage));
            // On applique le onAction au bouton delete
            delete.setOnAction(event -> deleteReview(reviewToModify ,typeOfReviewToManage));
        }





    }




    /**
     * Modify a review with the values from the form.
     * Review can be a SpotReview or a LessonReview. Depends on the value of typeOfReviewToManage.
     * @param typeOfReviewToManage the type of review to manage (Spot or Lesson)
     * @param newReview the new review
     */
    public void modifyReview(Review newReview ,String typeOfReviewToManage) {

        System.out.println("modifyReview");
        System.out.println("newReview: " + newReview);
        System.out.println("typeOfReviewToManage: " + typeOfReviewToManage);

        // On récupère les valeurs des champs
        String title = titleTextField.getText();
        String description = descriptionTextArea.getText();
        Integer rating = typeChoiceBox.getValue();

        newReview.setTitle(title);
        newReview.setDescription(description);
        newReview.setRating(rating);

        // On vérifie que le titre et la note sont bien remplis
        if (title.equals("") || rating == null) {
            this.errorLabel.setText("Please fill title and rating");
            return;
        }

        if(typeOfReviewToManage.equals("lesson")){

            boolean reviewUpdated = reviewFacade.updateReview(newReview);
            if (reviewUpdated) {
                Stage stage = (Stage) titleTextField.getScene().getWindow();
                stage.close();
                SuccessPopup.showWithOwner("Review updated successfuly!", stage);
            } else {
                errorLabel.setText("An error occurred while modifying the review");
            }
        }
    }

    /**
     * Delete a review.
     * Review can be a SpotReview or a LessonReview. Depends on the value of typeOfReviewToManage.
     * @param reviewToDelete the review to delete
     * @param typeOfReviewToManage the type of review to manage (Spot or Lesson)
     */
    public void deleteReview(Review reviewToDelete , String typeOfReviewToManage) {

        if (reviewFacade.deleteReview(reviewToDelete, typeOfReviewToManage)){
            Stage stage = (Stage) titleTextField.getScene().getWindow();
            stage.close();
            SuccessPopup.showWithOwner("Review deleted successfuly!", stage);
        } else {
            errorLabel.setText("An error occurred while deleting the review");
        }
    }


}

