package com.projetdiver.review;

import com.projetdiver.dao.PostgreDAOFactory;
import com.projetdiver.diver.Diver;
import com.projetdiver.diver.DiverFacade;

import java.util.List;

public class ReviewFacade {

    /**
     * The instance of the ReviewFacade.
     */
    private static ReviewFacade instance = null;

    private ReviewDAO reviewDAO;

    /**
     * Default constructor.
     * Initialize the ReviewDAO.
     */
    private ReviewFacade() {
        this.reviewDAO = PostgreDAOFactory.getInstance().createReviewDAO();
    }

    /**
     * Get the instance of the ReviewFacade.
     * @return the instance of the ReviewFacade
     */
    public static ReviewFacade getInstance() {
        if (instance == null) {
            instance = new ReviewFacade();
        }
        return instance;
    }

    public boolean createReview(String typeOfReviewToManage, int idLessonOrSpot, String title, String description, Integer rating) {
        // On récupère le diver connecté
        Diver diver = DiverFacade.getInstance().getCurrentDiver();

        // On crée la review
        Review review = new Review(null, diver.getId(), title, description, rating);
        boolean reviewAdded = this.reviewDAO.addReview(review, typeOfReviewToManage, idLessonOrSpot);

        return reviewAdded;
    }

    /**
     * Update the review.
     * @param review the new review
     */
    public boolean updateReview(Review review) {
        return this.reviewDAO.updateReview(review);
    }



    /**
     * Delete a review
     * @param review the review to delete
     * @param typeOfReviewToDelete the type of review to delete (Spot or Lesson)
     */
    public boolean deleteReview(Review review, String typeOfReviewToDelete) {
        return this.reviewDAO.deleteReview(review, typeOfReviewToDelete);
    }

    /**
     * Get a review by its id.
     * @param id the id of the review
     * @return the review
     */
    public Review getReview(int id) {
        return this.reviewDAO.getReview(id);
    }

    /**
     * Get the reviews of a diver for a lesson.
     * @param diverId the id of the diver
     * @return all the reviews of the diver
     */
    public Review getReviewByLessonIdAndDiverId(int lessonId, int diverId) {
        return this.reviewDAO.getReviewByLessonIdAndDiverId(lessonId, diverId);
    }

    /**
     * Get all the reviews for a lesson.
     * @param lessonId the id of the lesson
     * @return all the reviews of the lesson
     */
    public List<Review> getAllReviewsOfLesson(int lessonId) {
        return this.reviewDAO.getAllReviewsOfLesson(lessonId);
    }

    /**
     *
     * @param diverId
     * @param lessonId
     * @return true if the diver has already reviewed the lesson, false otherwise
     */
    public boolean hasReviewedLesson(int diverId, int lessonId) {
        return this.reviewDAO.hasReviewedLesson(diverId, lessonId);
    }


}
