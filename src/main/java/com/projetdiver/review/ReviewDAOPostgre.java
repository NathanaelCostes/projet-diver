package com.projetdiver.review;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;
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
    public Review getReview(int id) {
        try{
            connection();
            System.out.println("Connection to the database established");

            // Use a prepared statement to avoid SQL injection
            String sql = "SELECT * FROM review WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    Review review = new Review(
                            resultSet.getInt("id"),
                            resultSet.getInt("diver_id"),
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
            System.err.println("Error while connecting to the database");
        } finally {
            closeConnection();
        }
        return null;
    }

    /**
     * Fetches all reviews from the database
     * @return the list of reviews
     */
    public List<Review> getAllReviews() {
        // TODO implement here
        return null;
    }

    /**
     * Add a review to the database
     * @param review the review to add
     * @return true if the review is added, false otherwise
     */
    public boolean addReview(Review review) {
        try{
            connection();
            System.out.println("Connection to the database established");

            // Use a prepared statement to avoid SQL injection
            String sql = "INSERT INTO review (diver_id, title, description, rating) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, review.getDiverId());
                statement.setString(2, review.getTitle());
                statement.setString(3, review.getDescription());
                statement.setInt(4, review.getRating());
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
     * Delete a review from the database
     * @param review the review to delete
     * @return true if the review is deleted, false otherwise
     */
    public boolean deleteReview(Review review) {
        // TODO implement here
        return false;
    }

    /**
     * Update the title of the review
     * @param review the review to update
     * @param title the new title of the review
     */
    public void updateReviewTitle(Review review, String title) {
        // TODO implement here
    }

    /**
     * Update the description of the review
     * @param review the review to update
     * @param description the new description of the review
     */
    public void updateReviewDescription(Review review, String description) {
        // TODO implement here
    }

    /**
     * Update the rating of the review
     * @param review the review to update
     * @param rating the new rating of the review
     */
    public void updateReviewRating(Review review, Integer rating) {
        // TODO implement here
    }
}
