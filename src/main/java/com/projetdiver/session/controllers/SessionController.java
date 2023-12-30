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

    private Label separator(){
        Label separator = new Label(" | ");
        separator.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        return separator;
    }

    private Button createButton(String text, String color){
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: 'Roboto';");
        return button;
    }

    /**
     * Initialize the session list
     */
    @FXML
    public void initialize() {
        try {
            facade.getAllSessions().forEach(session -> {
                HBox sessionHBox = new HBox();
                sessionHBox.setSpacing(5);
                sessionHBox.setAlignment(javafx.geometry.Pos.CENTER);
                sessionHBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width: 1px; -fx-border-radius: 5px;");

                Label sessionTitleLabel = new Label(session.getTitle());
                sessionTitleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: 'Roboto';");
                sessionHBox.getChildren().add(sessionTitleLabel);

                sessionHBox.getChildren().add(separator());

                Label sessionDateLabel = new Label(session.getDate().toString());
                sessionDateLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: 'Roboto';");
                sessionHBox.getChildren().add(sessionDateLabel);

                sessionHBox.getChildren().add(separator());

                Label sessionOwnerLabel = new Label(session.getOwner().getNom() + " " + session.getOwner().getPrenom());
                sessionOwnerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: 'Roboto';");
                sessionHBox.getChildren().add(sessionOwnerLabel);

                sessionHBox.getChildren().add(separator());

                if(session.getOwner().getDiverId() == facade.getCurrentDiver().getDiverId()) {
                    Button sessionModifyButton = createButton("Modify", "green");
                    //sessionModifyButton.setOnAction(event -> { modifySession(session); });
                    sessionHBox.getChildren().add(sessionModifyButton);
                }

                Button sessionInfoButton = createButton("Info", "blue");
                //sessionInfoButton.setOnAction(event -> { infoSession(session); });
                sessionHBox.getChildren().add(sessionInfoButton);

                sessionListVBox.setSpacing(10);
                sessionListVBox.getChildren().add(sessionHBox);
            });



            Button sessionCreateButton = createButton("Create", "green");
            //sessionCreateButton.setOnAction(event -> { createSession(); });
            sessionMenuHBox.getChildren().add(sessionCreateButton);

            Button sessionJoinButton = createButton("Join", "orange");
            //sessionJoinButton.setOnAction(event -> { joinSession(); });
            sessionMenuHBox.getChildren().add(sessionJoinButton);


        } catch (NotConnectedException e) {
            throw new RuntimeException(e);
        }
    }
}
