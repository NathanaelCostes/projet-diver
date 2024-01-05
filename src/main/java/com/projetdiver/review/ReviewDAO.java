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
     * Fetches a review from the database using its id
     * @param lessonId the id of the lesson
     * @param diverId the id of the diver
     * @return the review if found, null otherwise
     */
    public abstract Review getReviewByLessonIdAndDiverId(int lessonId, int diverId);

    /**
     * Fetches all reviews of a lesson from the database
     * @param lessonId the id of the lesson
     * @return the list of reviews for the lesson
     */
    public abstract List<Review> getAllReviewsOfLesson(int lessonId);

    /**
     * Add a review to the database
     * @param review the review to add
     * @param typeOfReviewToManage the type of review to manage (Spot or Lesson)
     * @param idLessonOrSpot the id of the lesson or spot
     * @return true if the review is added, false otherwise
     */
    public abstract boolean addReview(Review review, String typeOfReviewToManage, int idLessonOrSpot);

    /**
     * Add a review to a lesson
     * @param review the review to add
     * @param lessonId the id of the lesson
     * @return true if the review is added, false otherwise
     */
    public abstract boolean addReviewToLesson(Review review, int lessonId);

    /**
     * Delete a review from the database
     * @param review the review to delete
     * @param typeOfReviewToDelete the type of review to delete (Spot or Lesson)
     * @return true if the review is deleted, false otherwise
     */
    public abstract boolean deleteReview(Review review, String typeOfReviewToDelete);

    /**
     * Update the review
     * @param review the new review
     * @return true if the review is updated, false otherwise
     */
    public abstract boolean updateReview(Review review);



    /**
     * Check if a diver has already reviewed a lesson
     * @param diverId the id of the diver
     * @param lessonId the id of the lesson
     * @return true if the diver has already reviewed the lesson, false otherwise
     */
    public abstract boolean hasReviewedLesson(int diverId, int lessonId);

    /**
     * get the instance of the ReviewDAO
     * @return the instance of the ReviewDAO
     */
    public static ReviewDAO getInstance() {
        if (instance == null) {
            instance = new ReviewDAOPostgre();
        }
        return instance;
    }
}
