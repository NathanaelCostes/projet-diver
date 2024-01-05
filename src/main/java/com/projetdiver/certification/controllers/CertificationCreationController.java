package com.projetdiver.certification.controllers;

import com.projetdiver.certification.Certification;
import com.projetdiver.certification.CertificationFacade;
import com.projetdiver.certification.CertificationType;
import com.projetdiver.certification.Level;
import com.projetdiver.diver.DiverFacade;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class CertificationCreationController {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField obtainedLevelTextField;

    @FXML
    private ChoiceBox<CertificationType> selectTypeChoiceBox;

    @FXML
    private Label errorLabel;

    @FXML
    private Label fileLabel;

    @FXML
    private void initialize() {
        selectTypeChoiceBox.getItems().addAll(CertificationType.values());
        selectTypeChoiceBox.setValue(CertificationType.AutonomousDiver);
    }

    private Stage stage;

    private File selectedFile;

    /**
     * Set the stage for the controller
     * @param stage The stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void browseFiles(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pdf Files", "*.pdf"));
        this.selectedFile = fileChooser.showOpenDialog(stage);

        if (this.selectedFile != null) {
            System.out.println("File selected: " + this.selectedFile.getName());
            fileLabel.setText(this.selectedFile.getName());
        }
    }

    public void createCertification() {

        Certification certification = new Certification();

        certification.setName(nameTextField.getText());

        try {
            Integer obtainedLevel = Integer.parseInt(obtainedLevelTextField.getText());
            certification.setLevelObtained(new Level(obtainedLevel,selectTypeChoiceBox.getValue()));
        } catch (NumberFormatException e) {
            errorLabel.setText("Obtained level must be a number");
            return;
        }

        if (this.selectedFile == null) {
            errorLabel.setText("You must select a file");
            return;
        }

        certification.setFile(this.selectedFile);

        certification.setFileName(this.selectedFile.getName());

        System.out.println(certification);

        boolean success = CertificationFacade.getInstance().createCertification(certification, DiverFacade.getInstance().getCurrentDiver().getId());

        if (success) {
            stage.close();
        } else {
            errorLabel.setText("Error while creating certification");
        }
    }
}
