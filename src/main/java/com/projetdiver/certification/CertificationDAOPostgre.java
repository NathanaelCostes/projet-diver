package com.projetdiver.certification;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CertificationDAOPostgre extends CertificationDAO {


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
     * Get all the certifications of a diver from the database
     * @param diverId the ID of the diver
     * @return the list of all the certifications
     */
    @Override
    public List<Certification> getAllCertificationsOfDiver(int diverId) {
        List<Certification> certifications = new ArrayList<>();
        try  {
           connection();

            System.out.println("Getting all the of this diver : " + diverId);


            // Query to retrieve certifications of a diver
            String certificationSql = "SELECT certificationId, name, levelObtainedLevel, levelObtainedType, pending, file, filename FROM certification WHERE diverId = ?";
            try (PreparedStatement certificationStatement = connection.prepareStatement(certificationSql)) {
                certificationStatement.setInt(1, diverId);

                try (ResultSet resultSet = certificationStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int certificationId = resultSet.getInt("certificationId");
                        String name = resultSet.getString("name");
                        Integer levelObtainedLevel = resultSet.getInt("levelObtainedLevel");
                        CertificationType levelObtainedType = CertificationType.valueOf(resultSet.getString("levelObtainedType"));
                        boolean pending = resultSet.getBoolean("pending");
                        byte[] fileBytes = resultSet.getBytes("file");
                        String fileName = resultSet.getString("filename");

                        System.out.println("File name: " + fileName);

                        // Convert byte array to File object
                        File file = convertBytesToFile(fileBytes, fileName);

                        Level level = new Level(levelObtainedLevel, levelObtainedType);
                        Certification certification = new Certification(certificationId, name, level, pending, file, fileName);
                        certifications.add(certification);
                    }
                }
            }
        } catch (SQLException | IOException e) {
            System.err.println("Error while retrieving certifications");
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return certifications;
    }

    /**
     * Get all the certifications required to do a lesson
     *
     * @param lessonId the id of the lesson
     * @return the list of all the certifications required to do the lesson
     */
    @Override
    public List<Certification> getAllCertificationsOfLesson(int lessonId) {
        return null;
    }


    // Convert byte array to File object
    private File convertBytesToFile(byte[] fileBytes, String filename) throws IOException {
        // Create a temporary file
        Path tempFilePath = Files.createTempFile(filename, null);

        // Write the byte array to the temporary file
        Files.write(tempFilePath, fileBytes);

        // Convert the temporary file path to a File object
        return tempFilePath.toFile();
    }

    // Separate method to read file content as bytes
    private byte[] readFileContent(Path filePath) throws IOException {
        return Files.readAllBytes(filePath);
    }

    /**
     * Create a certification for a diver in the database
     * @param certification the certification to create
     * @param diverId       the id of the diver
     */
    @Override
    public boolean createCertification(Certification certification, int diverId) {

        try {
            connection();

            // Insert into certification table
            String certificationSql = "INSERT INTO certification (name, pending, file, fileName, levelObtainedLevel, levelObtainedType , diverId) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement certificationStatement = connection.prepareStatement(certificationSql, Statement.RETURN_GENERATED_KEYS)) {
                // Insert into certification table
                certificationStatement.setString(1, certification.getName());
                certificationStatement.setBoolean(2, certification.isPending());
                certificationStatement.setBytes(3, readFileContent(certification.getFile().toPath()));
                certificationStatement.setString(4, certification.getId() + certification.getFileName());
                certificationStatement.setInt(5, certification.getLevelObtained().getLevel());
                certificationStatement.setString(6, certification.getLevelObtained().getCertificationType().toString());
                certificationStatement.setInt(7, diverId);

                certificationStatement.executeUpdate();

            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.err.println("Error while creating the certification");
            return false;
        } finally {
            closeConnection();
        }

        return true;
    }

    /**
     * Update a certification in the database
     *
     * @param certification the certification to update
     */
    @Override
    public boolean updateCertification(Certification certification) {

        System.out.println(certification);
        try {
            connection();

            // Update certification table
            String certificationSql = "UPDATE certification SET name=?, pending=?, file=?, fileName=?, levelObtainedLevel=?, levelObtainedType=? WHERE certificationId=?";
            System.out.println(certification.getId());

            try (PreparedStatement certificationStatement = connection.prepareStatement(certificationSql)) {
                // Set parameters for the UPDATE statement
                certificationStatement.setString(1, certification.getName());
                certificationStatement.setBoolean(2, certification.isPending());
                certificationStatement.setBytes(3, Files.readAllBytes(certification.getFile().toPath()));
                certificationStatement.setString(4, certification.getFileName());
                certificationStatement.setInt(5, certification.getLevelObtained().getLevel());
                certificationStatement.setString(6, certification.getLevelObtained().getCertificationType().toString());
                certificationStatement.setInt(7, certification.getId());

                // Execute the UPDATE statement
                int rowsAffected = certificationStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // Update successful
                    return true;
                } else {
                    // No rows were updated, certification not found
                    System.err.println("Certification not found for update");
                    return false;
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.err.println("Error while updating the certification");
            return false;
        } finally {
            closeConnection();
        }
    }

    /**
     * Delete a certification in the database
     *
     * @param certificationId the id of the certification
     */
    @Override
    public void deleteCertification(int certificationId) {
        try {
            connection();

            // Delete certification
            String certificationSql = "DELETE FROM certification WHERE certificationId = ?";
            try (PreparedStatement certificationStatement = connection.prepareStatement(certificationSql)) {
                certificationStatement.setInt(1, certificationId);
                int nbRows = certificationStatement.executeUpdate();
                if (nbRows == 0) {
                    System.err.println("Error while deleting the certification");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while deleting the certification");
        } finally {
            closeConnection();

        }
    }

    /**
     * Get all the certifications in the database
     *
     * @return the list of all the certifications
     */
    @Override
    public List<Certification> getAllCertifications() {
        List<Certification> certifications = new ArrayList<>();

        try {
            connection();
            System.out.println("Getting all the certifications");
            // Query to retrieve all certifications
            String getAllCertificationsSql = "SELECT certificationId, name, pending, file, fileName, levelObtainedLevel, levelObtainedType, diverId FROM certification";
            try (PreparedStatement getAllCertificationsStatement = connection.prepareStatement(getAllCertificationsSql)) {

                try (ResultSet resultSet = getAllCertificationsStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int certificationId = resultSet.getInt("certificationId");
                        String name = resultSet.getString("name");
                        boolean pending = resultSet.getBoolean("pending");
                        byte[] fileBytes = resultSet.getBytes("file");
                        String fileName = resultSet.getString("fileName");
                        int levelObtainedLevel = resultSet.getInt("levelObtainedLevel");
                        String levelObtainedType = resultSet.getString("levelObtainedType");

                        // Convert byte array to File object
                        File file = convertBytesToFile(fileBytes, fileName);

                        Level level = new Level(levelObtainedLevel, CertificationType.valueOf(levelObtainedType));

                        Certification certification = new Certification(certificationId, name, level , pending, file, fileName);
                        certifications.add(certification);
                    }
                }
            }
        } catch (SQLException | IOException e) {
            System.err.println("Error while retrieving certifications");
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return certifications;
    }
}
