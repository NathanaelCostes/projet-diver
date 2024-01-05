package com.projetdiver.diver.controllers;

import com.projetdiver.certification.Certification;
import com.projetdiver.certification.CertificationFacade;
import com.projetdiver.diver.Diver;
import com.projetdiver.diver.DiverFacade;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ProfileController {

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField passwordField;

    @FXML
    private ListView studentsListView;

    @FXML
    private Label studentsListLabel;

    private DiverFacade diverFacade;

    public ProfileController() {
        this.diverFacade = DiverFacade.getInstance();
    }

    /**
     * Initialize the profile page
     * Get the current diver and set the text fields with the diver's informations
     */
    @FXML
    public void initialize() {

        try {
            setCertificationListView();
            if (studentsListView.getItems() != null && !studentsListView.getItems().isEmpty()) {
                // Show the relevant components
                studentsListLabel.setVisible(true);
                studentsListView.setVisible(true);

                // Set managed property to true to include it in layout calculations
                studentsListLabel.setManaged(true);
                studentsListView.setManaged(true);

            } else {
                // If studentsListView is null or empty, hide the last two rows
                studentsListLabel.setVisible(false);
                studentsListView.setVisible(false);

                // Set managed property to false to exclude it from layout calculations
                studentsListLabel.setManaged(false);
                studentsListView.setManaged(false);
            }
            firstNameTextField.setText(this.diverFacade.getCurrentDiver().getFirstName());
            lastNameTextField.setText(this.diverFacade.getCurrentDiver().getLastName());
            emailTextField.setText(this.diverFacade.getCurrentDiver().getEmail());
            passwordField.setText(this.diverFacade.getCurrentDiver().getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setCertificationListView() {
        try{
            studentsListView.getItems().clear();

            List<Diver> studentList = diverFacade.getAllStudents(diverFacade.getCurrentDiver().getId());

            List<Certification> certificationList = new ArrayList<>();
            for (Diver student : studentList) {
                System.out.println(student);
                List<Certification> certificationListOfStudent = CertificationFacade.getInstance().getAllCertificationsOfDiver(student.getId());

                certificationList.addAll(certificationListOfStudent);
                System.out.println(certificationListOfStudent);
            }

            for (Certification certification : certificationList) {
                System.out.println(certification);
                if (certification.isPending()) {
                    Button buttonDownload = new Button("Download Certificate");
                    buttonDownload.setOnAction(event -> handleButtonActionDownload(certification));

                    Button buttonDelete = new Button("Delete Certificate");
                    buttonDelete.setOnAction(event -> handleButtonActionDelete(certification));

                    Button buttonValidate = new Button("Accept Certificate");
                    buttonValidate.setOnAction(event -> handlteButtonActionValidate(certification));

                    HBox hbox = new HBox(new Label(certification.toString()), buttonDownload, buttonDelete, buttonValidate);
                    studentsListView.getItems().add(hbox);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleButtonActionDelete(Certification certification) {
        try {
            CertificationFacade.getInstance().deleteCertification(certification.getId());
            initialize();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void handlteButtonActionValidate(Certification certification) {
        certification.setPending(false);
        CertificationFacade.getInstance().updateCertification(certification);
        initialize();
    }


    private void handleButtonActionDownload(Certification certification) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Certificate");

            // Set initial directory and suggested file name
            fileChooser.setInitialFileName(certification.getFileName());
            String userDownloadsDir = System.getProperty("user.home") + File.separator + "Downloads";
            fileChooser.setInitialDirectory(new File(userDownloadsDir));

            // Set extension filter if needed
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);

            // Show save file dialog
            File file = fileChooser.showSaveDialog(new Stage());

            if (file != null) {
                // User chose a file, proceed with downloading
                // You might want to handle the download logic here
                // For simplicity, assume the certification.getFile() returns the file content as a byte array
                byte[] fileContent = Files.readAllBytes(certification.getFile().toPath());

                // Save the fileContent to the selected file
                Files.write(file.toPath(), fileContent);

                // Optionally, you can show a success message or perform other actions
                System.out.println("Certificate downloaded successfully to: " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            System.out.println("Error while handling download: " + e.getMessage());
        }
    }

    /**
     * Edit the first name field in the profile page
     */
    public void editFirstName() {
        firstNameTextField.setDisable(false);
    }

    /**
     * Edit the last name field in the profile page
     */
    public void editLastName() {
        lastNameTextField.setDisable(false);
    }

    /**
     * Edit the email field in the profile page
     */
    public void editEmail() {
        emailTextField.setDisable(false);
    }

    /**
     * Edit the password field in the profile page
     */
    public void editPassword() {
        passwordField.setDisable(false);
    }


    /**
     * Save the new diver's first name written in the text field
     */
    public void saveFirstName() {
        diverFacade.updateDiverFirstName(firstNameTextField.getText());
        firstNameTextField.setDisable(true);
    }

    /**
     * Save the new diver's last name written in the text field
     */
    public void saveLastName() {
        diverFacade.updateDiverLastName(lastNameTextField.getText());
        lastNameTextField.setDisable(true);

    }

    /**
     * Save the new diver's email written in the text field
     */
    public void saveEmail() {
        diverFacade.updateDiverEmail(emailTextField.getText());
        emailTextField.setDisable(true);

    }

    /**
     * Save the new diver's password written in the text field
     */
    public void savePassword() {
        diverFacade.updateDiverPassword(passwordField.getText());
        passwordField.setDisable(true);

    }
}
