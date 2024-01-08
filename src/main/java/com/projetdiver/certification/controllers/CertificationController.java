package com.projetdiver.certification.controllers;

import com.fxrouter.FXRouter;
import com.projetdiver.certification.Certification;
import com.projetdiver.certification.CertificationFacade;
import com.projetdiver.diver.DiverFacade;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CertificationController implements Initializable {


    @FXML
    private ListView<HBox> listCertificationView;

    private void setListCertificationForDiverView() {
        try {
            listCertificationView.getItems().clear();

            List<Certification> certificationList = CertificationFacade.getInstance().getAllCertificationsOfDiver(DiverFacade.getInstance().getCurrentDiver().getId());

            for (Certification certification : certificationList) {
                Button buttonDownload = new Button("Download Certificate");
                buttonDownload.setOnAction(event -> {
                    handleButtonActionDownload(certification);
                });
                buttonDownload.getStyleClass().add("download-button"); // Set style class

                Button buttonDelete = new Button("Delete Certificate");
                buttonDelete.setOnAction(event -> {
                    handleButtonActionDelete(certification);
                });
                buttonDelete.getStyleClass().add("delete-button"); // Set style class

                Button buttonUpdate = new Button("Update Certificate");
                buttonUpdate.getStyleClass().add("update-button"); // Set style class
                buttonUpdate.setOnAction(event -> {
                    System.out.println(certification);
                    handleButtonActionUpdate(certification);
                });


                HBox hbox = new HBox(new Label(certification.toString()), buttonDownload, buttonUpdate, buttonDelete);
                listCertificationView.getItems().add(hbox);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleButtonActionUpdate(Certification certification) {
        try {

            InputStream fxmlStream = getClass().getResourceAsStream("/com/projetdiver/views/certification/certification-update-view.fxml");
            System.out.println(fxmlStream);
            FXMLLoader loader = new FXMLLoader();
            System.out.println(loader);
            Parent root = loader.load(fxmlStream);
            System.out.println(root);

            Stage modalStage = new Stage();
            modalStage.setTitle("Update Certification");

            Scene scene = new Scene(root);
            modalStage.setScene(scene);

            CertificationUpdateController certificationUpdateController = loader.getController();
            certificationUpdateController.setCertificationToModify(certification);
            certificationUpdateController.setStage(modalStage);

            String cssPath = Objects.requireNonNull(getClass().getResource("/com/projetdiver/styles/certificationCreationModalStyle.css")).toExternalForm();
            scene.getStylesheets().add(cssPath);

            // Set the current stage as the owner for the modality
            modalStage.initOwner(((Node) listCertificationView).getScene().getWindow());

            // Prevent interaction with the main window while the modal is open
            modalStage.initModality(Modality.WINDOW_MODAL);

            // Show the modal window
            modalStage.showAndWait();

            initialize(null, null);

        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void openCertificationCreationModal(ActionEvent event) {
        try {
            InputStream fxmlStream = getClass().getResourceAsStream("/com/projetdiver/views/certification/certification-creation-view.fxml");
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fxmlStream);

            Stage modalStage = new Stage();
            modalStage.setTitle("Create Certification");

            Scene scene = new Scene(root);
            modalStage.setScene(scene);

            CertificationCreationController certificationCreationController = loader.getController();
            certificationCreationController.setStage(modalStage);

            String cssPath = Objects.requireNonNull(getClass().getResource("/com/projetdiver/styles/certificationCreationModalStyle.css")).toExternalForm();
            scene.getStylesheets().add(cssPath);

            // Set the current stage as the owner for the modality
            modalStage.initOwner(((Node) event.getSource()).getScene().getWindow());

            // Prevent interaction with the main window while the modal is open
            modalStage.initModality(Modality.WINDOW_MODAL);

            // Show the modal window
            modalStage.showAndWait();

            initialize(null, null);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void handleButtonActionDelete(Certification certification) {
        try {
            CertificationFacade.getInstance().deleteCertification(certification.getId());
            initialize(null, null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void backToMainPage(){
        try {
            FXRouter.goTo("main");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleButtonActionDownload(Certification certification) {
        try {
            System.out.println("Downloading certificate: " + certification.getFileName());
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
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setListCertificationForDiverView();
    }
}
