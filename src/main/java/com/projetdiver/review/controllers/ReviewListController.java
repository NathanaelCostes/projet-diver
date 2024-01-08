package com.projetdiver.review.controllers;

import com.projetdiver.review.Review;
import com.projetdiver.review.ReviewFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ReviewListController {

    private ReviewFacade reviewFacade;

    /**
     * VBox to hold the review
     */
    @FXML
    private VBox reviewListVBox;

    public ReviewListController() {
        this.reviewFacade = ReviewFacade.getInstance();
    }

    @FXML
    public void initialize(int idLesson) {
        // Fetch the list of reviews
        reviewFacade.getAllReviewsOfLesson(idLesson);

        // Populate the VBox with labels for each review
        for (Review review : reviewFacade.getAllReviewsOfLesson(idLesson)) {
            // Create a HBox to hold the review's informations
            VBox reviewVBox = new VBox();
            // Assigner la classe CSS à la VBox
            reviewVBox.getStyleClass().add("vbox-review");

            // Set the spacing between the elements of the HBox
            reviewVBox.setSpacing(10);
            // Align the elements of the HBox
            reviewVBox.setAlignment(javafx.geometry.Pos.CENTER);

            // Create title label
            Label reviewTitle = new Label(review.getTitle());
            // Create description label
            Label reviewDescription = new Label(review.getDescription());
            // Create rating label
            Label reviewRating = new Label(review.getRating()+"/5");

            // Add the labels to the HBox
            reviewVBox.getChildren().addAll(reviewTitle, reviewDescription, reviewRating);

            // Add the HBox to the VBox
            reviewListVBox.getChildren().add(reviewVBox);

        }
    }
}
