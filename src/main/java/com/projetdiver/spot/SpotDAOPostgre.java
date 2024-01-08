package com.projetdiver.spot;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;
import java.util.ArrayList;

public class SpotDAOPostgre extends SpotDAO {

    /**
     * Default constructor
     */
    public SpotDAOPostgre() {
    }

    /** dotenv to load informations from .env file */
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
     * Get a spot by its id
     *
     * @param spotId the id of the spot
     * @return the spot
     */
    public Spot getSpotById(int spotId) {
        try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql = "SELECT * FROM spot WHERE spotId=?;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, spotId);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {

                    Integer maxDepth = resultSet.getInt("maxDepth");
                    if (resultSet.wasNull()) { maxDepth = null; }

                    String poi = resultSet.getString("poi");
                    if (resultSet.wasNull()) { poi = null; }

                   Spot spot = new Spot(
                            resultSet.getInt("spotId"),
                            resultSet.getString("name"),
                            resultSet.getFloat("latitude"),
                            resultSet.getFloat("longitude"),
                            maxDepth,
                            resultSet.getString("type"),
                            poi,
                            resultSet.getString("level")
                    );

                    resultSet.close();

                    return spot;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeConnection();
        }
    }

    /**
     * Create a spot
     *
     * @param spot the spot to create
     * @return true if the spot was created, false otherwise
     */
    public boolean createSpot(Spot spot) {
        try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql = "INSERT INTO spot (name, latitude, longitude, maxDepth, type, poi, level) VALUES (?,?,?,?,?,?,?);";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, spot.getName());

                if (spot.getLatitude() == null) {
                    statement.setNull(2, Types.FLOAT);
                } else {
                    statement.setFloat(2, spot.getLatitude());
                }

                if (spot.getLongitude() == null) {
                    statement.setNull(3, Types.FLOAT);
                } else {
                    statement.setFloat(3, spot.getLongitude());
                }

                if (spot.getMaxDepth() == null) {
                    statement.setNull(4, Types.INTEGER);
                } else {
                    statement.setInt(4, spot.getMaxDepth());
                }

                statement.setString(5, spot.getType());

                if (spot.getPoi() == null) {
                    statement.setNull(6, Types.VARCHAR);
                } else {
                    statement.setString(6, spot.getPoi());
                }

                statement.setString(7, spot.getLevel());
                statement.executeUpdate();

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
    }

    /**
     * Delete a spot
     *
     * @param spotId the id of the spot
     * @return true if the spot was deleted, false otherwise
     */
    public boolean deleteSpot(int spotId) {
try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql = "DELETE FROM spot WHERE spotId=?;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, spotId);
                statement.executeUpdate();

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
    }

    /**
     * Update a spot
     *
     * @param spot the spot to update
     * @return true if the spot was updated, false otherwise
     */
    public boolean updateSpot(Spot spot) {

try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql = "UPDATE spot SET name=?, latitude=?, longitude=?, maxDepth=?, type=?, poi=?, level=? WHERE spotId=?;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, spot.getName());

                if (spot.getLatitude() == null) {
                    statement.setNull(2, Types.FLOAT);
                } else {
                    statement.setFloat(2, spot.getLatitude());
                }

                if (spot.getLongitude() == null) {
                    statement.setNull(3, Types.FLOAT);
                } else {
                    statement.setFloat(3, spot.getLongitude());
                }

                if (spot.getMaxDepth() == null) {
                    statement.setNull(4, Types.INTEGER);
                } else {
                    statement.setInt(4, spot.getMaxDepth());
                }

                statement.setString(5, spot.getType());

                if (spot.getPoi() == null) {
                    statement.setNull(6, Types.VARCHAR);
                } else {
                    statement.setString(6, spot.getPoi());
                }

                statement.setString(7, spot.getLevel());
                statement.setInt(8, spot.getSpotId());
                statement.executeUpdate();

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
    }

    /**
     * Get all the spots
     *
     * @return the list of all the spots
     */
    public ArrayList<Spot> getAllSpots() {
        try {
            connection();

            // Use a prepared statement to avoid SQL injection
            String sql = "SELECT * FROM spot;";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();

                ArrayList<Spot> allSpots = new ArrayList<>();

                while (resultSet.next()) {

                    Integer maxDepth = resultSet.getInt("maxDepth");
                    if (resultSet.wasNull()) { maxDepth = null; }

                    String poi = resultSet.getString("poi");
                    if (resultSet.wasNull()) { poi = null; }

                    Spot spot = new Spot(
                            resultSet.getInt("spotId"),
                            resultSet.getString("name"),
                            resultSet.getFloat("latitude"),
                            resultSet.getFloat("longitude"),
                            maxDepth,
                            resultSet.getString("type"),
                            poi,
                            resultSet.getString("level")
                    );

                    allSpots.add(spot);
                }

                resultSet.close();
                return allSpots;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeConnection();
        }
    }
}
