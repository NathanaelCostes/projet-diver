package com.projetdiver.contact;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.projetdiver.diver.DiverDAO;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * This class is the DAO of the Contact for Postgre database
 */
public class ContactDAOPostgre extends ContactDAO {

    /**
     * Default constructor
     */
    public ContactDAOPostgre() {}

    /** dotenv to load informations from the .env */
    private Dotenv dotenv = Dotenv.load();

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
            this.connection = DriverManager.getConnection(this.DB_URL, this.DB_USER, this.DB_PASSWORD);
            this.connection.isValid(2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the connection to the database
     */
    private void closeConnection() {
        // Close the connection
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param contact to create
     */
    @Override
    public void create(Contact contact) {
        try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql = "INSERT INTO contact (receiver, sender, pending) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, contact.getReceiver().getId());
                statement.setInt(2, contact.getSender().getId());
                statement.setBoolean(3, contact.isPending());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    /**
     * @param contact to update
     */
    @Override
    public void update(Contact contact) {
        try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql = "UPDATE contact SET pending = ? WHERE receiver = ? AND sender = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setBoolean(1, contact.isPending());
                statement.setInt(2, contact.getReceiver().getId());
                statement.setInt(3, contact.getSender().getId());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    /**
     * @param contact to delete
     */
    @Override
    public void delete(Contact contact) {
        try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql = "DELETE FROM contact WHERE receiver = ? AND sender = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, contact.getReceiver().getId());
                statement.setInt(2, contact.getSender().getId());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    /**
     * Get contact, order is not important
     * @param receiver id of the receiver (diver)
     * @param sender id of the sender (diver)
     * @return the contact
     */
    @Override
    public Contact getContact(int receiver, int sender) {
        Contact contact = null;
        try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql = "SELECT * FROM contact WHERE receiver = ? AND sender = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, receiver);
                statement.setInt(2, sender);

                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    contact = new Contact(
                        DiverDAO.getInstance().getDiver(result.getInt("receiver")),
                        DiverDAO.getInstance().getDiver(result.getInt("sender")),
                        result.getBoolean("pending")
                    );
                }

                result.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return contact;
    }

    /**
     * Get all the contact of a diver (if he is the sender or the receiver)
     * @param id of the diver
     * @return the contact
     */
    @Override
    public List<Contact> getAllContact(int id) {
        List<Contact> contacts = new ArrayList<Contact>();

        try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql = "SELECT * FROM contact WHERE receiver = ? OR sender = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.setInt(2, id);

                ResultSet result = statement.executeQuery();

                while (result.next()) {
                    contacts.add(new Contact(
                        DiverDAO.getInstance().getDiver(result.getInt("receiver")),
                        DiverDAO.getInstance().getDiver(result.getInt("sender")),
                        result.getBoolean("pending")
                    ));
                }

                result.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return contacts;
    }

    /**
     * Check if two divers are in contact
     * @param id1 of the first diver
     * @param id2 of the second diver
     * @return true if they are in contact, false otherwise
     */
    @Override
    public boolean isInContact(int id1, int id2) {
        boolean res = false;

        try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql = "SELECT * FROM contact WHERE (receiver = ? AND sender = ?) OR (receiver = ? AND sender = ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id1);
                statement.setInt(2, id2);
                statement.setInt(3, id2);
                statement.setInt(4, id1);

                ResultSet result = statement.executeQuery();

                while (result.next()) {
                    if (result.getBoolean("pending")) {
                        res = true;
                    }
                }

                result.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }   

        return res;
    }
}
