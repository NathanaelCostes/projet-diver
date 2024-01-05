package com.projetdiver.admin;

import com.fxrouter.FXRouter;
import com.projetdiver.diver.Diver;
import com.projetdiver.diver.DiverFacade;
import com.projetdiver.session.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

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
