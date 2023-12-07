package com.projetdiver.login;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

/**
 * 
 */
public class DiverDAOPostgre extends DiverDAO {

    /**
     * Default constructor
     */
    public DiverDAOPostgre() {

    }

    Dotenv dotenv = Dotenv.load();

    private Connection connection;

    private final String DB_USER = dotenv.get("DB_USER");
    private final String DB_PASSWORD = dotenv.get("DB_PASSWORD");
    private final String DB_URL = dotenv.get("DB_URL");

    /**
     *
     */
    private void connection() {

        try {
            this.connection = DriverManager.getConnection(this.DB_URL, this.DB_USER, this.DB_PASSWORD);
            this.connection.isValid(2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     */public Diver getDiver(String email) {
        try {
            connection();
            System.out.println("Connection to the database successful");

            // Use a prepared statement to avoid SQL injection
            String sql = "SELECT * FROM users WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    Diver diver = new Diver(resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("last_name"),
                            resultSet.getString("first_name"));

                    resultSet.close();
                    System.out.println(diver);
                    return diver;
                } else {
                    // Handle the case when no user with the given email is found
                    System.out.println("User with email " + email + " not found");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the connection in a finally block to ensure it's always closed
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null; // Placeholder for handling errors
    }

}