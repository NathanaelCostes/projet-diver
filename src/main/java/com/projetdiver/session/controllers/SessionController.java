package com.projetdiver.session.controllers;

import com.projetdiver.diver.Diver;
import com.projetdiver.diver.DiverFacade;
import com.projetdiver.session.Session;
import com.projetdiver.session.exceptions.NotConnectedException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;

import com.projetdiver.session.SessionFacade;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class SessionController implements Initializable, ControllerHelper {
    /**
     * The instance of the SessionFacade
     */
    private final SessionFacade facade;

    /**
     * VBox to hold the diver labels
     */
    @FXML
    private VBox sessionListVBox;

    /**
     * HBoc to hold menu buttons
     */
    @FXML
    private HBox sessionMenuHBox;

    /**
     * Default constructor
     */
    public SessionController() {
        this.facade = SessionFacade.getInstance();
    }

    @FXML
    public void setSessionMenuHBox(){
        sessionMenuHBox.getChildren().clear();

        Button sessionCreateButton = createButton("Create", "green");
        sessionCreateButton.setOnAction(event -> { 
            openCreateSession(event); 
            setSessionListView();
        });
        sessionMenuHBox.getChildren().add(sessionCreateButton);

        Button sessionRefreshButton = createButton("Request", "lightgray");
        sessionRefreshButton.setOnAction(event -> {
            openInvitationSession(event);
            setSessionListView(); 
        });
        sessionMenuHBox.getChildren().add(sessionRefreshButton);
    }
    
    @FXML
    private void openInvitationSession(ActionEvent event) {
        try {
            InputStream fxmlStream = getClass().getResourceAsStream("/com/projetdiver/views/session/session-invitation-view.fxml");
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fxmlStream);

            SessionInvitationController sessionInvitationController = loader.getController();
            sessionInvitationController.setSessionInvitation(DiverFacade.getInstance().getCurrentDiver());

            Stage modalStage = new Stage();
            modalStage.setTitle("Session invitation");

            Scene scene = new Scene(root, 1500, 400);
            scene.setUserData(this);

            modalStage.setScene(scene);
            modalStage.initOwner(((Button) event.getSource()).getScene().getWindow());
            modalStage.initModality(Modality.WINDOW_MODAL);

            modalStage.showAndWait();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void setSessionListView(){
        sessionListVBox.getChildren().clear();
        try {
            facade.getAllSessions().forEach(session -> {
                HBox sessionHBox = new HBox();
                sessionHBox.setSpacing(5);
                sessionHBox.setAlignment(javafx.geometry.Pos.CENTER);
                sessionHBox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1px; -fx-border-radius: 5px;");

                sessionHBox.getChildren().add(createLabel(session.getTitle()));

                sessionHBox.getChildren().add(separator());

                if (session.getDate() != null) {
                    sessionHBox.getChildren().add(createLabel(session.getDate().toString()));

                    sessionHBox.getChildren().add(separator());
                }

                sessionHBox.getChildren().add(createLabel(session.getOwner().getLastName() + " " + session.getOwner().getFirstName()));

                sessionHBox.getChildren().add(separator());

                if(session.getOwner().getId() == facade.getCurrentDiver().getId()) {
                    Button sessionModifyButton = createButton("Modify", "green");
                    sessionModifyButton.setOnAction(event -> { 
                        openModifySession(event, session);
                        setSessionListView();
                    });
                    sessionHBox.getChildren().add(sessionModifyButton);
                }

                Button sessionInfoButton = createButton("Info", "blue");
                sessionInfoButton.setOnAction(event -> { openDetailsSession(event, session); });
                sessionHBox.getChildren().add(sessionInfoButton);

                sessionListVBox.setSpacing(10);
                sessionListVBox.getChildren().add(sessionHBox);
            });
        } catch (NotConnectedException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void openDetailsSession(Event event, Session session) {
        try {
            InputStream fxmlStream = getClass().getResourceAsStream("/com/projetdiver/views/session/session-details-view.fxml");
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fxmlStream);

            SessionDetailsController sessionDetailsController = loader.getController();
            sessionDetailsController.setSessionDetails(session);

            Stage modalStage = new Stage();
            modalStage.setTitle("Session details");

            Scene scene = new Scene(root, 600, 400);
            scene.setUserData(this);

            modalStage.setScene(scene);
            modalStage.initOwner(((Button) event.getSource()).getScene().getWindow());
            modalStage.initModality(Modality.WINDOW_MODAL);

            modalStage.showAndWait();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void openModifySession(Event event, Session session) {
        try {
            InputStream fxmlStream = getClass().getResourceAsStream("/com/projetdiver/views/session/session-modify-create-view.fxml");
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fxmlStream);

            SessionModifyCreateController sessionModifyCreateController = loader.getController();
            sessionModifyCreateController.setSessionModify(session);

            Stage modalStage = new Stage();
            modalStage.setTitle("Session modify");

            Scene scene = new Scene(root, 600, 600);
            scene.setUserData(this);

            modalStage.setScene(scene);
            modalStage.initOwner(((Button) event.getSource()).getScene().getWindow());
            modalStage.initModality(Modality.WINDOW_MODAL);

            modalStage.showAndWait();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void openCreateSession(Event event) {
        try {
            InputStream fxmlStream = getClass().getResourceAsStream("/com/projetdiver/views/session/session-modify-create-view.fxml");
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fxmlStream);

            SessionModifyCreateController sessionCreateController = loader.getController();
            sessionCreateController.setSessionCreate();

            Stage modalStage = new Stage();
            modalStage.setTitle("Session create");

            Scene scene = new Scene(root, 600, 600);
            scene.setUserData(this);

            modalStage.setScene(scene);
            modalStage.initOwner(((Button) event.getSource()).getScene().getWindow());
            modalStage.initModality(Modality.WINDOW_MODAL);

            modalStage.showAndWait();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Initialize the session list
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSessionListView();
        setSessionMenuHBox();
    }
}
