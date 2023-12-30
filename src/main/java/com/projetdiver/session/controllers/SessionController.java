package com.projetdiver.session.controllers;

import com.projetdiver.session.exceptions.NotConnectedException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import com.projetdiver.session.SessionFacade;

public class SessionController {
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
     * Default constructor
     */
    public SessionController() {
        this.facade = SessionFacade.getInstance();
    }

    /**
     * Initialize the session list
     */
    @FXML
    public void initialize() {
        try {
            facade.getAllSessions().forEach(session -> {
                HBox sessionHBox = new HBox();
                sessionHBox.setSpacing(10);
                sessionHBox.setAlignment(javafx.geometry.Pos.CENTER);
                sessionHBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width: 1px; -fx-border-radius: 5px;");

                Label sessionTitleLabel = new Label(session.getTitle());
                sessionTitleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
                sessionHBox.getChildren().add(sessionTitleLabel);

                Label sessionDateLabel = new Label(session.getDate().toString());
                sessionDateLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
                sessionHBox.getChildren().add(sessionDateLabel);

                Label sessionOwnerLabel = new Label(session.getOwner().getNom() + " " + session.getOwner().getPrenom());
                sessionOwnerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
                sessionHBox.getChildren().add(sessionOwnerLabel);

                if(session.getOwner().getDiverId() == facade.getCurrentDiver().getDiverId()) {
                    Button sessionModifyButton = new Button("Modify");
                    sessionModifyButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");
                    //sessionModifyButton.setOnAction(event -> { modifySession(session); });
                    sessionHBox.getChildren().add(sessionModifyButton);
                }

                Button sessionInfoButton = new Button("Info");
                sessionInfoButton.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");
                //sessionInfoButton.setOnAction(event -> { infoSession(session); });
                sessionHBox.getChildren().add(sessionInfoButton);

                sessionListVBox.setSpacing(10);
                sessionListVBox.getChildren().add(sessionHBox);
            });
        } catch (NotConnectedException e) {
            throw new RuntimeException(e);
        }
    }
}
