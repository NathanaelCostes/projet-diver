package com.projetdiver.admin;

import com.fxrouter.FXRouter;

import com.projetdiver.certification.Certification;
import com.projetdiver.certification.CertificationFacade;
import com.projetdiver.diver.Diver;
import com.projetdiver.diver.DiverFacade;
import com.projetdiver.session.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import java.io.IOException;
import java.util.List;

public class AdminPanelController {

    /**
     * The instance of the AdminFacade
     */
    private AdminFacade adminFacade;

    /**
     * VBox to hold the diver labels
     */
    @FXML
    private VBox diverListVBox;


    @FXML
    private ListView<HBox> listCertificationView;

    /**
     * Default constructor
     */
    public AdminPanelController() {
        this.adminFacade = AdminFacade.getInstance();
    }

    @FXML
    private void backToMainPage(){
        try{
            FXRouter.goTo("main");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Initialize method to be called after FXML fields are injected
     */
    @FXML
    public void initialize() {
        setDiverListView();
        setCertificationListView();
    }

    private void setDiverListView() {
        // Fetch the list of divers
        adminFacade.getDivers();

        // Populate the VBox with labels for each diver
        for (Diver diver : adminFacade.getDivers()) {
            // Create a HBox to hold the diver's informations and the button to delete (ban) the diver
            HBox diverHBox = new HBox();
            // Set the spacing between the elements of the HBox
            diverHBox.setSpacing(10);
            // Align the elements of the HBox
            diverHBox.setAlignment(javafx.geometry.Pos.CENTER);
            // Set the style of the HBox
            diverHBox.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 5px;");

            // Create first name label
            Label diverFirstNameLabel = new Label(diver.getFirstName());
            // Create last name label
            Label diverLastNameLabel = new Label(diver.getLastName());
            // Create email label
            Label diverEmailLabel = new Label(diver.getEmail());

            // Create the button to delete the diver
            Button deleteDiverButton = new Button("Delete");
            // Set the action of the button
            deleteDiverButton.setOnAction(e -> deleteDiver(diver.getId()));
            // Set the style of the button
            deleteDiverButton.setStyle("-fx-background-color: #D94640; -fx-text-fill: white;");

            // Add labels to the HBox
            diverHBox.getChildren().add(diverFirstNameLabel);
            diverHBox.getChildren().add(diverLastNameLabel);
            diverHBox.getChildren().add(diverEmailLabel);
            // Add the button to the HBox
            diverHBox.getChildren().add(deleteDiverButton);

            // Add the HBox to the VBox
            diverListVBox.getChildren().add(diverHBox);
        }
    }


    private void setCertificationListView() {
        try{
            listCertificationView.getItems().clear();

            List<Certification> certificationList = CertificationFacade.getInstance().getAllCertifications();

            for (Certification certification : certificationList) {
                if(certification.isPending()) {
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


                    Button buttonValidate = new Button("Accept Certificate");
                    buttonValidate.setOnAction(event -> {
                        handleButtonActionValidate(certification);
                    });
                    buttonValidate.getStyleClass().add("validate-button"); // Set style class


                    HBox hbox = new HBox(new Label(certification.toString()), buttonDownload, buttonDelete, buttonValidate);
                    listCertificationView.getItems().add(hbox);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleButtonActionDelete(Certification certification) {
        try {
            CertificationFacade.getInstance().deleteCertification(certification.getId());
            setCertificationListView();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleButtonActionValidate(Certification certification) {
        certification.setPending(false);
        CertificationFacade.getInstance().updateCertification(certification);
        setCertificationListView();
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
     * Delete the diver in the same HBox as the button
     * @param diverId the id of the diver to delete
     */
    public void deleteDiver(int diverId) {
        // Get the HBox that contains the button
        HBox diverHBox = (HBox) ((Button) (diverListVBox.getScene().getFocusOwner())).getParent();
        // Get email label
        Label diverEmailLabel = (Label) diverHBox.getChildren().get(2);

        // Je récupère le diver à partir de son id
        Diver diver = DiverFacade.getInstance().getDiver(diverId);

        SessionDAO sessionDAO = SessionDAO.getInstance();
        List<Session> listSession = sessionDAO.getAllSessionsWhereDiverIsOwner(diver);
        SessionFacade sessionFacade = SessionFacade.getInstance();
        for (Session session : listSession) {
            sessionFacade.deleteSessionCascade(session);
        }

        // Je récupère toutes les invitations du diver
        List<Invitation> listInvitation = sessionDAO.getInstance().getAllInvitationsToDiver(diver);
        // Pour chaque invitation du diver
        for (Invitation invitation : listInvitation) {
            // Je supprime l'invitation
            sessionDAO.getInstance().deleteInvitation(invitation);
        }

        // Remove the diver from the database
        adminFacade.deleteDiverById(diverId);


        // Remove the HBox from the VBox
        diverListVBox.getChildren().remove(diverHBox);
    }
}
