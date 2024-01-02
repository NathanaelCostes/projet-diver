package com.projetdiver.session.controllers;

import com.projetdiver.session.Session;
import com.projetdiver.session.SessionFacade;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class SessionDetailsController implements ControllerHelper {
    /**
     * The instance of the SessionFacade
     */
    private final SessionFacade facade;

    /**
     * VBox to hold the information about the session
     */
    @FXML
    private VBox sessionDetailsListVBox;

    /**
     * Default constructor
     */
    public SessionDetailsController() {
        this.facade = SessionFacade.getInstance();
    }

    /**
     * Initialize the session list
     */
    @FXML
    public void initialize() {
    }

    public void setSessionDetails(Session session) {
        sessionDetailsListVBox.getChildren().clear();

        sessionDetailsListVBox.setSpacing(5);
        sessionDetailsListVBox.setAlignment(javafx.geometry.Pos.CENTER);
        sessionDetailsListVBox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1px; -fx-border-radius: 5px;");

        try {
            sessionDetailsListVBox.getChildren().add(createLabel(session.getTitle()));
            if(session.getDate() != null) {
                sessionDetailsListVBox.getChildren().add(createLabel(session.getDate().toString()));
            } else {
                sessionDetailsListVBox.getChildren().add(createLabel("No date"));
            }
            if (session.getComment() != null) {
                sessionDetailsListVBox.getChildren().add(createLabel(session.getComment()));
            } else {
                sessionDetailsListVBox.getChildren().add(createLabel("No comment"));
            }
            if (session.getDuration() != null) {
                sessionDetailsListVBox.getChildren().add(createLabel(session.getDuration().toString()));
            } else {
                sessionDetailsListVBox.getChildren().add(createLabel("No duration"));
            }

            if (session.getTemp() != null) {
                sessionDetailsListVBox.getChildren().add(createLabel(session.getTemp().toString()));
            } else {
                sessionDetailsListVBox.getChildren().add(createLabel("No temperature"));
            }

            if (session.getDepth() != null) {
                sessionDetailsListVBox.getChildren().add(createLabel(session.getDepth().toString()));
            } else {
                sessionDetailsListVBox.getChildren().add(createLabel("No depth"));
            }

            sessionDetailsListVBox.getChildren().add(createLabel(session.getOwner().getLastName() + " " + session.getOwner().getFirstName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
