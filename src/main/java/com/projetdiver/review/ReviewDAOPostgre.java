package com.projetdiver.review;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAOPostgre extends ReviewDAO{

    /**
     * Default constructor
     */
    public ReviewDAOPostgre() {
    }

    /**
     * dotenv to load the environment variables
     */
    private Dotenv dotenv = Dotenv.load();

    private Connection connection;

    /**
     * User of the database to get in the .env
     */
    private final String DB_USER = dotenv.get("DB_USER");

    /**
     * Password of the database to get in the .env
     */
    private final String DB_PASSWORD = dotenv.get("DB_PASSWORD");

    /**
     * URL of the database to get in the .env
     */
    private final String DB_URL = dotenv.get("DB_URL");

    /**
     * Connect to the database using the informations in the .env
     */
    private void connection() {
        try {
            assert this.DB_URL != null;
            this.connection = DriverManager.getConnection(this.DB_URL, this.DB_USER, this.DB_PASSWORD);
            this.connection.isValid(2);
        } catch (SQLException e) {
            System.err.println("Connection to the database failed");
        }
    }

    /**
     * Close the connection to the database
     */
    private void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            System.err.println("Error while closing the connection to the database");
        }
    }


    /**
     * Fetches a review from the database using its id
     * @param id the id to find in the database
     * @return the review if found, null otherwise
     */
    public Review getReview(int reviewId) {
        try{
            connection();
            System.out.println("Connection to the database established");

            // Use a prepared statement to avoid SQL injection
            String sql = "SELECT * FROM review WHERE reviewId = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, reviewId);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    Review review = new Review(
                            resultSet.getInt("reviewId"),
                            resultSet.getInt("diverId"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            resultSet.getInt("rating")
                    );
                    resultSet.close();
                    return review;
                } else {
                    System.err.println("No review found with this id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error while connecting to the database");
        } finally {
            closeConnection();
        }
        return null;
    }

    /**
     * Fetches a review from the database using its id
     * @param lessonId the id of the lesson
     * @param diverId the id of the diver
     * @return the review if found, null otherwise
     */
    public Review getReviewByLessonIdAndDiverId(int lessonId, int diverId) {
        try{
            connection();
            System.out.println("Connection to the database established");

            // Use a prepared statement to avoid SQL injection
            String sql = "SELECT * FROM lessonReview WHERE lessonId = ? AND diverId = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, lessonId);
                statement.setInt(2, diverId);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    System.out.println("reviewId : " + resultSet.getInt("reviewId"));
                    Review review = getReview(resultSet.getInt("reviewId"));
                    resultSet.close();
                    return review;
                } else {
                    System.err.println("No review found with this id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error while connecting to the database");
        } finally {
            closeConnection();
        }
        return null;
    }

    /**
     * Fetches all reviews of a lesson from the database
     * @param lessonId the id of the lesson
     * @return the list of reviews for the lesson
     */
    public List<Review> getAllReviewsOfLesson(int lessonId) {
        // Création de la liste
        List<Review> listReviews = new ArrayList<>();

        try{
            connection();
            System.out.println("Connection to the database established");

            // Use a prepared statement to avoid SQL injection
            String sql = "SELECT * FROM lessonReview WHERE lessonId = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, lessonId);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    //On appelle la méthode getReview pour récupérer les infos de la review
                    Review review = getReview(resultSet.getInt("reviewId"));
                    listReviews.add(review);
                }
                resultSet.close();
                return listReviews;
            }
        } catch (SQLException e) {
            System.err.println("Error while connecting to the database");
        } finally {
            closeConnection();
        }
        return null;
    }

    /**
     * Add a review to the database
     * @param review the review to add
     * @param typeOfReviewToManage the type of review to manage (Spot or Lesson)
     * @param idLessonOrSpot the id of the lesson or spot
     * @return true if the review is added, false otherwise
     */
    public boolean addReview(Review review, String typeOfReviewToManage, int idLessonOrSpot) {
        try{
            connection();
            System.out.println("Connection to the database established");

            // Use a prepared statement to avoid SQL injection
            String sql = "INSERT INTO review (diverId, title, description, rating) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, review.getDiverId());
                statement.setString(2, review.getTitle());
                statement.setString(3, review.getDescription());
                statement.setInt(4, review.getRating());

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0){

                    // On récupère l'id de la review
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int reviewId = generatedKeys.getInt(1);
                        // On met à jour l'objet review
                        review.setId(reviewId);
                        generatedKeys.close();
                        // On ajoute la review à la table lessonReview ou spotReview
                        if (typeOfReviewToManage.equals("lesson")) {
                            return addReviewToLesson(review, idLessonOrSpot);
                        } else if (typeOfReviewToManage.equals("spot")) {
                            //return addReviewToSpot(review, idLessonOrSpot);
                        }
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error while connecting to the database");
        } finally {
            closeConnection();
        }
        return false;
    }

    /**
     * Add a review to a lesson
     * @param review the review to add
     * @param lessonId the id of the lesson
     * @return true if the review is added, false otherwise
     */
    public boolean addReviewToLesson(Review review, int lessonId) {
        try{
            connection();
            System.out.println("Connection to the database established");
            System.out.println("lessonId : " + lessonId);
            System.out.println("reviewId : " + review.getId());
            System.out.println("diverId : " + review.getDiverId());

            // Use a prepared statement to avoid SQL injection
            String sql = "INSERT INTO lessonReview (reviewId, lessonId, diverId) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, review.getId());
                statement.setInt(2, lessonId);
                statement.setInt(3, review.getDiverId());
                System.out.println("statement : " + statement);
                statement.executeUpdate();
                System.out.println("statement : " + statement);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error while connecting to the database");
        } finally {
            closeConnection();
        }
        return false;
    }

    /**
     * Delete a review from the database
     * @param review the review to delete
     * @param typeOfReviewToDelete the type of review to delete (Spot or Lesson)
     * @return true if the review is deleted, false otherwise
     */
    public boolean deleteReview(Review review, String typeOfReviewToDelete) {
        try{
            connection();
            System.out.println("Connection to the database established");
            String sqlDeleteRelation = "";

            if(typeOfReviewToDelete.equals("lesson")) {
                // Delete the relation between the review and the lesson
                sqlDeleteRelation = "DELETE FROM lessonReview WHERE reviewId = ?";
            } else if(typeOfReviewToDelete.equals("spot")) {
                // Delete the relation between the review and the spot
                //sqlDeleteRelation = "DELETE FROM spotReview WHERE reviewId = ?";
            }
            // Use a prepared statement to avoid SQL injection
            String sqlDeleteReview = "DELETE FROM review WHERE reviewId = ?";
            try (PreparedStatement deleteRelation = connection.prepareStatement(sqlDeleteRelation);
                 PreparedStatement statement2 = connection.prepareStatement(sqlDeleteReview)) {
                deleteRelation.setInt(1, review.getId());

                int rowsAffected = deleteRelation.executeUpdate();
                if(rowsAffected > 0) {
                    statement2.setInt(1, review.getId());
                    int rowsAffectedReview = statement2.executeUpdate();
                    return rowsAffectedReview > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while connecting to the database");
        } finally {
            closeConnection();
        }
        return false;
    }

    /**
     * Update the review
     * @param review the new review
     * @return true if the review is updated, false otherwise
     */
    public boolean updateReview(Review review) {
        try{
            connection();
            System.out.println("Connection to the database established");

            // Use a prepared statement to avoid SQL injection
            String sql = "UPDATE review SET title = ?, description = ?, rating = ? WHERE reviewId = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, review.getTitle());
                statement.setString(2, review.getDescription());
                statement.setInt(3, review.getRating());
                statement.setInt(4, review.getId());
                statement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error while connecting to the database");
        } finally {
            closeConnection();
        }
        return false;
    }


    /**
     * Check if a diver has already reviewed a lesson
     * @param diverId the id of the diver
     * @param lessonId the id of the lesson
     * @return true if the diver has already reviewed the lesson, false otherwise
     */
    public boolean hasReviewedLesson(int diverId, int lessonId) {
        try{
            connection();
            System.out.println("Connection to the database established");
            System.out.println("diverId : " + diverId);
            System.out.println("lessonId : " + lessonId);

            // Use a prepared statement to avoid SQL injection
            String sql = "SELECT * FROM lessonReview WHERE diverId = ? AND lessonId = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, diverId);
                statement.setInt(2, lessonId);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    // On affiche le résultat de la requête
                    System.out.println("Result of the query : ");
                    System.out.println("diverId : " + resultSet.getInt("diverId"));
                    System.out.println("lessonId : " + resultSet.getInt("lessonId"));
                    System.out.println("reviewId : " + resultSet.getInt("reviewId"));
                    resultSet.close();
                    return true;
                } else {
                    System.err.println("No review found with this id");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while connecting to the database");
        } finally {
            closeConnection();
        }
        System.out.println("Avant le return false");
        return false;
    }
}
