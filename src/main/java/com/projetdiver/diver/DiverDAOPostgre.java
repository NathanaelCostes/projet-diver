package com.projetdiver.diver;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;

/**
 * Does the connection to the postgre database and communicate with it
 */
public class DiverDAOPostgre extends DiverDAO {

    /**
     * Default constructor
     */
    public DiverDAOPostgre() {}

    /** dotenv to load informations from the .env */
    Dotenv dotenv = Dotenv.load();

    private Connection connection;

    /** User of the database to get in the .env */
    private final String DB_USER = dotenv.get("DB_USER");

    /** Password of the database to get in the .env */
    private final String DB_PASSWORD = dotenv.get("DB_PASSWORD");

    /** URL of the database to get in the .env */
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
                    Diver diver = new Diver
                            (resultSet.getInt("diverId"),
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
     * @param diverId the id to find in the database
     * @return the diver if found, null otherwise
     */
    public Diver getDiver(int diverId) {
        try {
            connection();
            System.out.println("Connection to the database successful");

            // Use a prepared statement to avoid SQL injection
            String sql = "SELECT * FROM diver WHERE diverId=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, diverId);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    Diver diver = new Diver(
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
                    System.out.println("User with id " + diverId + " not found");
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

}