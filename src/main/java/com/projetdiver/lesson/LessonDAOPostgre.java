package com.projetdiver.lesson;

import com.projetdiver.diver.Diver;
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
            this.connection = DriverManager.getConnection(this.DB_URL, this.DB_USER, this.DB_PASSWORD);
            this.connection.isValid(2);
            System.out.println("Connection to the database successful");

        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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

            String sql = "SELECT * FROM lesson WHERE lesson_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, String.valueOf(id));
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    Lesson lesson = new Lesson();
                    // Set the properties of the Lesson object based on the database columns
                    lesson.setId(resultSet.getInt("lesson_id"));
                    lesson.setTeacher(null); //TODO : set the teacher with a get on the id
                    lesson.setName(resultSet.getString("name"));
                    lesson.setDescription(resultSet.getString("description"));
                    lesson.setStartDate(resultSet.getDate("start_date"));
                    lesson.setEndDate(resultSet.getDate("end_date"));
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
     * @param lesson
     * @return true if the lesson was created, false otherwise
     */
    @Override
    public boolean createLesson(Lesson lesson) {
        try {
            connection();

            String sql = "INSERT INTO lesson (professor_id, name, description, start_date, end_date, type) " +
                            "VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, 1); //TODO get idOfTeacher
                statement.setString(2, lesson.getName());
                statement.setString(3, lesson.getDescription());
                statement.setDate(4, lesson.getStartDate());
                statement.setDate(5, lesson.getEndDate());
                statement.setString(6, lesson.getType().toString());

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
     * Delete a lesson
     * @param lessonId the ID of the lesson to be deleted
     * @return true if the lesson was deleted, false otherwise
     */
    @Override
    public boolean deleteLesson(int lessonId) {
        try {
            connection();

            String sql = "DELETE FROM lesson WHERE lesson_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, lessonId);

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
     * Update a lesson
     * @param lesson
     * @return true if the lesson was updated, false otherwise
     */
    @Override
    public boolean updateLesson(Lesson lesson) {
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
                    lesson.setId(resultSet.getInt("lesson_id"));
                    lesson.setTeacher(null); //TODO : set the teacher with a get on the id
                    lesson.setName(resultSet.getString("name"));
                    lesson.setDescription(resultSet.getString("description"));
                    lesson.setStartDate(resultSet.getDate("start_date"));
                    lesson.setEndDate(resultSet.getDate("end_date"));
                    lesson.setType(LessonType.valueOf(resultSet.getString("type")));

                    // Add the lesson to the list
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

}
