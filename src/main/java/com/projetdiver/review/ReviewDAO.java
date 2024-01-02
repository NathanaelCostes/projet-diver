package com.projetdiver.review;

import java.util.List;

/**
 * Abstract class for the ReviewDAO
 */
public abstract class ReviewDAO {

    /** instance de ReviewDAO Postgre */
    private static ReviewDAO instance;

    /**
     * Default constructor
     */
    public ReviewDAO() {}

    /**
     * Fetches a review from the database using its id
     * @param id the id to find in the database
     * @return the review if found, null otherwise
     */
    public abstract Review getReview(int id);

    /**
     * Fetches all reviews from the database
     * @return the list of reviews
     */
    public abstract List<Review> getAllReviews();

    /**
     * Add a review to the database
     * @param review the review to add
     * @return true if the review is added, false otherwise
     */
    public abstract boolean addReview(Review review);

    /**
     * Delete a review from the database
     * @param review the review to delete
     * @return true if the review is deleted, false otherwise
     */
    public abstract boolean deleteReview(Review review);

    /**
     * Update the title of the review
     * @param review the review to update
     * @param title the new title of the review
     */
    public abstract void updateReviewTitle(Review review, String title);

    /**
     * Update the description of the review
     * @param review the review to update
     * @param description the new description of the review
     */
    public abstract void updateReviewDescription(Review review, String description);

    /**
     * Update the rating of the review
     * @param review the review to update
     * @param rating the new rating of the review
     */
    public abstract void updateReviewRating(Review review, Integer rating);
}
