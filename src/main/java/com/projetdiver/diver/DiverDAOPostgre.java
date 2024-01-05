package com.projetdiver.diver;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Does the connection to the postgre database and communicate with it
 */
public class DiverDAOPostgre extends DiverDAO {

    /**
     * Default constructor
     */
    public DiverDAOPostgre() {
    }

    /**
     * dotenv to load informations from the .env
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
     * Fetches a diver from the database using its email
     *
     * @param email the email to find in the database
     * @return the diver if found, null otherwise
     */
    public Diver getDiver(String email) {
        try {
            connection();
            System.out.println("Connection to the database successful");

            // Use a prepared statement to avoid SQL injection
            String sql = "SELECT * FROM diver WHERE email=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    Diver diver = new Diver (
                            resultSet.getInt("diverId"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("lastName"),
                            resultSet.getString("firstName"));

                    resultSet.close();
                    System.out.println(diver);
                    return diver;
                } else {
                    // Handle the case when no user with the given email is found
                    System.out.println("User with email " + email + " not found");
                }
            }
        } catch (SQLException e) {
            System.err.println("Connection to the database failed");
        } finally {
            // Close the connection
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error while closing the connection");
            }
        }
        return null;
    }




    /**
     * Fetches a diver from the database using its email
     * @param id the id to find in the database
     * @return the diver if found, null otherwise
     */
    public Diver getDiver(int id) {
        try {
            connection();
            System.out.println("Connection to the database successful");

            // Use a prepared statement to avoid SQL injection
            String sql = "SELECT * FROM diver WHERE diverId=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    Diver diver = new Diver (
                            resultSet.getInt("diverId"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("lastName"),
                            resultSet.getString("firstName"));

                    resultSet.close();
                    System.out.println(diver);
                    return diver;
                } else {
                    // Handle the case when no user with the given id is found
                    System.out.println("User with id " + id + " not found");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the connection
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Adds a diver to the database
     * @param diver the diver to add to the database
     * @return true if the diver is added, false otherwise
     */
    public boolean addDiver(Diver diver) {
        try {
            connection();
            System.out.println("Connection to the database successful");

            // Use a prepared statement to avoid SQL injection
            String sql = "INSERT INTO diver (email, password, firstName , lastName) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, diver.getEmail());
                statement.setString(2, diver.getPassword());
                statement.setString(3, diver.getFirstName());
                statement.setString(4, diver.getLastName());
                statement.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } finally {
            // Close the connection
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get all the divers from the database
     *
     * @return the list of all the divers
     */
    @Override
    public List<Diver> getAllDivers() {
        // List of all the divers
        List<Diver> divers = new ArrayList<>();
        try{
            connection();
            System.out.println("Connection to the database successful");


            // Use a prepared statement to avoid SQL injection
            String sql = "SELECT * FROM diver";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Diver diver = new Diver(
                            resultSet.getInt("diverId"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("lastName"),
                            resultSet.getString("firstName"));

                    divers.add(diver);
                }
                resultSet.close();
                return divers;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            // Close the connection
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return divers;
    }

    /**
     * Update the first name of the diver
     *
     * @param diver     the diver to update
     * @param firstName the new first name of the diver
     */
    public void updateDiverFirstName(Diver diver, String firstName) {
        try {
            connection();
            System.out.println("Connection to the database successful");

            // Use a prepared statement to avoid SQL injection
            String sql = "UPDATE diver SET firstName = ? WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, firstName);
                statement.setString(2, diver.getEmail());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            // Close the connection
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Update the last name of the diver
     *
     * @param diver     the diver to update
     * @param lastName the new last name of the diver
     */
    public void updateDiverLastName(Diver diver, String lastName) {
        try {
            connection();
            System.out.println("Connection to the database successful");

            // Use a prepared statement to avoid SQL injection
            String sql = "UPDATE diver SET lastName = ? WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, lastName);
                statement.setString(2, diver.getEmail());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            // Close the connection
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Update the email of the diver
     *
     * @param diver     the diver to update
     * @param email the new email of the diver
     */
    public void updateDiverEmail(Diver diver, String email) {
        try {
            connection();
            System.out.println("Connection to the database successful");

            // Use a prepared statement to avoid SQL injection
            String sql = "UPDATE diver SET email = ? WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);
                statement.setString(2, diver.getEmail());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            // Close the connection
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Update the password of the diver
     *
     * @param diver     the diver to update
     * @param password the new password of the diver
     */
    public void updateDiverPassword(Diver diver, String password) {
        try {
            connection();
            System.out.println("Connection to the database successful");

            // Use a prepared statement to avoid SQL injection
            String sql = "UPDATE diver SET password = ? WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, password);
                statement.setString(2, diver.getEmail());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            // Close the connection
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Delete a diver from the database
     * @param email the email of the diver to delete
     */
    public void deleteDiverByEmail(String email) {
        try {
            connection();
            System.out.println("Connection to the database successful");

            // Use a prepared statement to avoid SQL injection
            String sql = "DELETE FROM diver WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            // Close the connection
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
<<<<<<< HEAD
     * Delete a diver from the database
     * @param id the id of the diver to delete
     */
    public void deleteDiverById(int id) {
        try {
            connection();
            System.out.println("Connection to the database successful");
            System.out.println("id = " + id);

            // Delete records from invitation
            String deleteInvitation = "DELETE FROM invitation WHERE receiver = ?";
            try (PreparedStatement statement = connection.prepareStatement(deleteInvitation)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Delete records from diverGivesLesson
            String deleteDiverGivesLesson = "DELETE FROM diverGivesLesson WHERE diverId = ?";
            try (PreparedStatement statement = connection.prepareStatement(deleteDiverGivesLesson)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Delete records from diverTakesLesson
            String deleteDiverTakesLesson = "DELETE FROM diverTakesLesson WHERE diverId = ?";
            try (PreparedStatement statement = connection.prepareStatement(deleteDiverTakesLesson)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Delete records from reviewLesson
            String deleteReviewLesson = "DELETE FROM lessonReview WHERE diverId = ?";
            try (PreparedStatement statement = connection.prepareStatement(deleteReviewLesson)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Delete records from review
            String deleteReview = "DELETE FROM review WHERE diverId = ?";
            try (PreparedStatement statement = connection.prepareStatement(deleteReview)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Delete the diver
            String deleteDiver = "DELETE FROM diver WHERE diverId = ?";
            try (PreparedStatement statement = connection.prepareStatement(deleteDiver)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            // Close the connection
            try {
                if (this.connection != null && !this.connection.isClosed()) {
                    this.connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

     /** Get all the students of a diver (professor)
     *
     * @param diverId the id of the professor diver
     * @return the list of all the students
     */
    @Override
    public List<Diver> getAllStudents(int diverId) {
        // List of all the students
        List<Diver> students = new ArrayList<>();
        try {
            connection();
            System.out.println("Connection to the database successful");

            // Use a prepared statement to avoid SQL injection
            String sql = "SELECT * FROM diver WHERE diverId IN (SELECT diverId FROM diverTakesLesson WHERE lessonId IN (SELECT lessonId FROM diverGivesLesson WHERE diverId = ?))";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, diverId);

                // Execute the query
                try (ResultSet resultSet = statement.executeQuery()) {
                    System.out.println("Students of Diver with ID " + diverId + ":");
                    while (resultSet.next()) {
                        Diver student = new Diver(
                                resultSet.getInt("diverId"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getString("lastName"),
                                resultSet.getString("firstName"));

                        students.add(student);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Close the connection
                try {
                    if (connection != null && !connection.isClosed()) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }
}