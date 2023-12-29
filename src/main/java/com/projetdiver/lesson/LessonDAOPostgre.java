package com.projetdiver.lesson;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class LessonDAOPostgre extends LessonDAO{

    /**
     * Default constructor
     */
    public LessonDAOPostgre() {}

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
            System.out.println("Connection to the database successful");

        } catch (SQLException e) {
            System.err.println("Error while connecting to the database");
        }
    }

    /**
     * Close the connection to the database
     */
    private void closeConnection(){
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error while closing the connection to the database");
        }
    }

    /**
     * Get a lesson by its id
     * @param id the id of the lesson
     * @return the lesson
     */
    @Override
    public Lesson getLessonById(int id) {
        try {
            connection();

            String sql = "SELECT * FROM lesson WHERE lessonId = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    Lesson lesson = new Lesson();
                    // Set the properties of the Lesson object based on the database columns
                    lesson.setId(resultSet.getInt("lessonId"));
                    lesson.setName(resultSet.getString("name"));
                    lesson.setDescription(resultSet.getString("description"));
                    lesson.setStartDate(resultSet.getDate("startDate"));
                    lesson.setEndDate(resultSet.getDate("endDate"));
                    lesson.setType(LessonType.valueOf(resultSet.getString("type")));

                    resultSet.close();
                    System.out.println(lesson);
                    return lesson;
                } else {
                    System.out.println("Lesson with id " + id + " not found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            // Close the connection
            closeConnection();
        }

        return null;
    }

    /**
     * Create a lesson
     * @param lesson the lesson to be created
     * @param teacherId the id of the teacher that creates the lesson
     * @return true if the lesson was created, false otherwise
     */
    @Override
    public boolean createLesson(Lesson lesson, int teacherId) {
        try {
            connection();

            // Insert into lesson table
            String lessonSql = "INSERT INTO lesson (name, description, startDate, endDate, type) " +
                    "VALUES (?, ?, ?, ?, ?)";

            // Insert into diverGivesLesson table
            String diverLessonSql = "INSERT INTO diverGivesLesson (diverId, lessonId) VALUES (?, ?)";

            try (PreparedStatement lessonStatement = connection.prepareStatement(lessonSql, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement diverLessonStatement = connection.prepareStatement(diverLessonSql)) {

                // Insert into lesson table
                lessonStatement.setString(1, lesson.getName());
                lessonStatement.setString(2, lesson.getDescription());
                lessonStatement.setDate(3, lesson.getStartDate());
                lessonStatement.setDate(4, lesson.getEndDate());
                lessonStatement.setString(5, lesson.getType().toString());

                int rowsAffected = lessonStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // Get the auto-generated lesson ID
                    ResultSet generatedKeys = lessonStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int lessonId = generatedKeys.getInt(1);

                        // Insert into diverGivesLesson table
                        diverLessonStatement.setInt(1, teacherId);
                        diverLessonStatement.setInt(2, lessonId);

                        int diverLessonRowsAffected = diverLessonStatement.executeUpdate();

                        return diverLessonRowsAffected > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return false;
    }


    /**
     * Delete a lesson and related records in diverTakesLesson and diverGivesLesson tables
     * @param lessonId the ID of the lesson to be deleted
     * @return true if the lesson and related records were deleted, false otherwise
     */
    @Override
    public boolean deleteLesson(int lessonId) {
        try {
            connection();
            boolean success = false;

            // Delete from diverTakesLesson table
            String deleteDiverTakesLessonSql = "DELETE FROM diverTakesLesson WHERE lessonId = ?";
            success = deleteRelatedRecords(lessonId, deleteDiverTakesLessonSql, "diverTakesLesson");

            // If diverTakesLesson deletion was successful, proceed to delete from diverGivesLesson table
            if (success) {
                String deleteDiverGivesLessonSql = "DELETE FROM diverGivesLesson WHERE lessonId = ?";
                success = deleteRelatedRecords(lessonId, deleteDiverGivesLessonSql, "diverGivesLesson");
            }

            // If diverGivesLesson deletion was successful, proceed to delete from lesson table
            if (success) {
                String deleteLessonSql = "DELETE FROM lesson WHERE lessonId = ?";
                success = deleteRecord(lessonId, deleteLessonSql);
            }

            return success;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return false;
    }



    // Helper method to close PreparedStatement
    private void closeStatement(PreparedStatement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Subscribe a diver to a lesson
     * @param lessonId the id of the lesson
     * @param diverId the diver that wants to subscribe to the lesson
     * @return true if the subscription was successful, false otherwise
     */
    @Override
    public boolean subscribeToALesson(int lessonId, int diverId) {
        try {
            connection();

            String diverLessonSql = "INSERT INTO diverTakesLesson (diverId, lessonId) VALUES (?, ?)";

            try (PreparedStatement diverLessonStatement = connection.prepareStatement(diverLessonSql)) {

                diverLessonStatement.setInt(1, diverId);
                diverLessonStatement.setInt(2, lessonId);

                int diverLessonRowsAffected = diverLessonStatement.executeUpdate();

                return diverLessonRowsAffected > 0;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return false;
    }


    /**
     * Update a lesson
     * @param lesson
     * @return true if the lesson was updated, false otherwise
     */
    @Override
    public boolean updateLesson(Lesson lesson) {
        try {
            connection();

            String sql = "UPDATE lesson SET name = ?, description = ?, startDate = ?, endDate = ?, type = ? WHERE lessonId = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, lesson.getName());
                statement.setString(2, lesson.getDescription());
                statement.setDate(3, lesson.getStartDate());
                statement.setDate(4, lesson.getEndDate());
                statement.setString(5, lesson.getType().toString());
                statement.setInt(6, lesson.getId());

                int rowsAffected = statement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return false;
    }

    /**
     * Get all the lessons
     *
     * @return the list of all the lessons
     */
    public List<Lesson> getAllLessons() {

        List<Lesson> lessons = new ArrayList<>();

        try {
            connection();
            System.out.println("Connection to the database successful");

            String sql = "SELECT * FROM lesson";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Lesson lesson = new Lesson();
                    // Set the properties of the Lesson object based on the database columns
                    lesson.setId(resultSet.getInt("lessonId"));
                    lesson.setName(resultSet.getString("name"));
                    lesson.setDescription(resultSet.getString("description"));
                    lesson.setStartDate(resultSet.getDate("startDate"));
                    lesson.setEndDate(resultSet.getDate("endDate"));
                    lesson.setType(LessonType.valueOf(resultSet.getString("type")));

                    lessons.add(lesson);

                }
                resultSet.close();
                return lessons;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
           closeConnection();
        }

        return null;
    }

    @Override
    public boolean isLessonCreator(int diverId, int lessonId) {

        try {
            connection();

            String sql = "SELECT * FROM diverGivesLesson WHERE diverId = ? AND lessonId = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, diverId);
                statement.setInt(2, lessonId);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return false;
    }

    public boolean isSubscribedToLesson(int diverId, int lessonId) {

        try {
            connection();

            String sql = "SELECT * FROM diverTakesLesson WHERE diverId = ? AND lessonId = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, diverId);
                statement.setInt(2, lessonId);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return false;
    }

    /**
     * Unsubscribe a diver from a lesson
     * @param lessonId the id of the lesson
     * @param diverId the id of the diver
     * @return true if the diver was unsubscribed from the lesson, false otherwise
     */
    @Override
    public boolean unsubscribeToTheLesson(int lessonId, int diverId) {
        try {
            connection();

            String sql = "DELETE FROM diverTakesLesson WHERE lessonId = ? AND diverId = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, lessonId);
                statement.setInt(2, diverId);

                int rowsAffected = statement.executeUpdate();

                // If at least one row was affected, consider it a success
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }



    /**
     * Helper method to delete related records
     * @param id the ID of the record to be deleted
     * @param sql the SQL query for deletion
     * @param tableName the name of the table
     * @return true if the related records were deleted, false otherwise
     * @throws SQLException if a SQL exception occurs
     */
    private boolean deleteRelatedRecords(int id, String sql, String tableName) throws SQLException {
        PreparedStatement statement = null;
        try {
            connection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();

            // Check if there are still rows with the specified ID after deletion
            int remainingRows = countRowsWithId(id, tableName);

            return remainingRows == 0;
        } finally {
            closeStatement(statement);
            closeConnection();
        }
    }


    /**
     * Helper method to delete a single record
     * @param id the ID of the record to be deleted
     * @param sql the SQL query for deletion
     * @return true if the record was deleted, false otherwise
     * @throws SQLException if a SQL exception occurs
     */

    private boolean deleteRecord(int id, String sql) throws SQLException {
        PreparedStatement statement = null;
        try {
            connection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } finally {
            closeStatement(statement);
            closeConnection();
        }
    }

    /**
     * Count the number of rows with the specified ID in a table
     * @param id the ID to count
     * @param tableName the name of the table
     * @return the number of rows with the specified ID
     * @throws SQLException if a SQL exception occurs
     */
    private int countRowsWithId(int id, String tableName) throws SQLException {
        PreparedStatement statement = null;
        try {
            connection();
            String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE lessonId = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } finally {
            closeStatement(statement);
            closeConnection();
        }

        return 0;
    }

}
