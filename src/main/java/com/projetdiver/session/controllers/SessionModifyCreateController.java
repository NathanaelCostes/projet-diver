package com.projetdiver.session.controllers;

import java.io.InputStream;
import java.sql.Date;

import com.projetdiver.session.Session;
import com.projetdiver.session.SessionFacade;
import com.projetdiver.session.exceptions.DiverNotFoundException;
import com.projetdiver.session.exceptions.InvitationNotFoundException;
import com.projetdiver.session.exceptions.NotConnectedException;
import com.projetdiver.session.exceptions.SessionAlreadyExistsException;
import com.projetdiver.session.exceptions.SessionNotFoundException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SessionModifyCreateController implements ControllerHelper {

    /**
     * The instance of the SessionFacade
     */
    private final SessionFacade facade;

    /**
     * VBox to hold the information about the session
     */
    @FXML
    private VBox sessionModifyListVBox;

    /**
     * Label to hold the title of the window
     */
    @FXML
    private Label sessionModifyLabel;

    /**
     * Default constructor
     */
    public SessionModifyCreateController() {
        this.facade = SessionFacade.getInstance();
    }

    /**
     * Initialize the session list
     */
    @FXML
    public void initialize() {
    }

    public void setSessionModify(Session session) {
        sessionModifyListVBox.getChildren().clear();

        sessionModifyListVBox.setSpacing(5);

        String[] errorMsg = {""};
        Label sessionErrorLabel = createLabel(errorMsg[0]);
        sessionErrorLabel.setStyle(sessionErrorLabel.getStyle() + "-fx-text-fill: red;");
        sessionModifyListVBox.getChildren().add(sessionErrorLabel);
        
        sessionModifyListVBox.getChildren().add(createLabel("Title: "));
        sessionModifyListVBox.getChildren().add(createTextField(session.getTitle()));

        sessionModifyListVBox.getChildren().add(createLabel("Date: "));
        DatePicker datePicker = new DatePicker();
        if (session.getDate() != null) {
            datePicker.setValue(session.getDate().toLocalDate());
        }
        sessionModifyListVBox.getChildren().add(datePicker);
        
        sessionModifyListVBox.getChildren().add(createLabel("Comment: "));
        TextField commentTextField;
        if (session.getComment() != null) {
            commentTextField = createTextField(session.getComment());
        } else {
            commentTextField = createTextField("");
        }
        sessionModifyListVBox.getChildren().add(commentTextField);

        sessionModifyListVBox.getChildren().add(createLabel("Duration: "));
        TextField durationTextField;
        if (session.getDuration() != null) {
            durationTextField = createTextField(session.getDuration().toString());
        } else {
            durationTextField = createTextField("");
        }
        sessionModifyListVBox.getChildren().add(durationTextField);

        sessionModifyListVBox.getChildren().add(createLabel("Temperature: "));
        TextField tempTextField;
        if (session.getTemp() != null) {
            tempTextField = createTextField(session.getTemp().toString());
        } else {
            tempTextField = createTextField("");
        }
        sessionModifyListVBox.getChildren().add(tempTextField);

        sessionModifyListVBox.getChildren().add(createLabel("Depth: "));
        TextField depthTextField;
        if (session.getDepth() != null) {
            depthTextField = createTextField(session.getDepth().toString());
        } else {
            depthTextField = createTextField("");
        }
        sessionModifyListVBox.getChildren().add(depthTextField);
        
        HBox sessionHBox = new HBox();
        sessionHBox.setSpacing(25);
        sessionHBox.setAlignment(javafx.geometry.Pos.CENTER);
        sessionHBox.setStyle("-fx-padding: 25px 0px 0px 0px;");

        Button sessionSaveButton = createButton("Save", "green");
        sessionSaveButton.setOnAction(event -> { try {
            session.setTitle(((TextField) sessionModifyListVBox.getChildren().get(2)).getText());
            
            DatePicker datePicker1 = (DatePicker) sessionModifyListVBox.getChildren().get(4);
            Date date;
            if (datePicker1.getValue() != null) {
                date = Date.valueOf(datePicker1.getValue());
            } else {
                date = null;
            }
            session.setDate(date);

            session.setComment(((TextField) sessionModifyListVBox.getChildren().get(6)).getText());

            String durationString = ((TextField) sessionModifyListVBox.getChildren().get(8)).getText();
            Float duration;
            if (durationString != null && !durationString.isEmpty()) {
                duration = Float.valueOf(durationString);
            } else {
                duration = null;
            }
            session.setDuration(duration);

            String tempString = ((TextField) sessionModifyListVBox.getChildren().get(10)).getText();
            Integer temp;
            if (tempString != null && !tempString.isEmpty()) {
                temp = Integer.valueOf(tempString);
            } else {
                temp = null;
            }
            session.setTemp(temp);

            String depthString = ((TextField) sessionModifyListVBox.getChildren().get(12)).getText();
            Integer depth;
            if (depthString != null && !depthString.isEmpty()) {
                depth = Integer.valueOf(depthString);
            } else {
                depth = null;
            }
            session.setDepth(depth);
            
            facade.modifySession(session);
            sessionSaveButton.getScene().getWindow().hide();
        } catch (NumberFormatException e) {
            errorMsg[0] = "Error: Duration, temperature and depth must be numbers";
            sessionErrorLabel.setText(errorMsg[0]);
        } catch (NotConnectedException | SessionNotFoundException | SessionAlreadyExistsException e) {
            errorMsg[0] = "Error: " + e.getMessage();
            sessionErrorLabel.setText(errorMsg[0]);
        } });
        sessionHBox.getChildren().add(sessionSaveButton);
        
        Button sessionDeleteButton = createButton("Delete", "red");
        sessionDeleteButton.setOnAction(event -> { try {
            facade.getAllInvitationsToSession(session).forEach(invitation -> {
                try {
                    facade.deleteInvitation(invitation);
                } catch (NotConnectedException | SessionNotFoundException | DiverNotFoundException | InvitationNotFoundException e) {
                    e.printStackTrace();
                }
            });
            facade.deleteSession(session);
            sessionDeleteButton.getScene().getWindow().hide();
        } catch (NotConnectedException | SessionNotFoundException e) {
            errorMsg[0] = "Error: " + e.getMessage();
            sessionErrorLabel.setText(errorMsg[0]);
        } });
        sessionHBox.getChildren().add(sessionDeleteButton);

        Button sessionInviteButton = createButton("Invite", "blue");
        sessionInviteButton.setOnAction(event -> {
            openInvitationSession(event, session); 
        });
        sessionHBox.getChildren().add(sessionInviteButton);

        sessionModifyListVBox.getChildren().add(sessionHBox);

    }

    public void setSessionCreate(){

        sessionModifyLabel.setText("Create a session");
        sessionModifyListVBox.getChildren().clear();

        sessionModifyListVBox.setSpacing(5);

        String[] errorMsg = {""};
        Label sessionErrorLabel = createLabel(errorMsg[0]);
        sessionErrorLabel.setStyle(sessionErrorLabel.getStyle() + "-fx-text-fill: red;");
        sessionModifyListVBox.getChildren().add(sessionErrorLabel);
        
        sessionModifyListVBox.getChildren().add(createLabel("Title: "));
        sessionModifyListVBox.getChildren().add(createTextField(""));

        sessionModifyListVBox.getChildren().add(createLabel("Date: "));
        DatePicker datePicker = new DatePicker();
        sessionModifyListVBox.getChildren().add(datePicker);
        
        sessionModifyListVBox.getChildren().add(createLabel("Comment: "));
        sessionModifyListVBox.getChildren().add(createTextField(""));

        sessionModifyListVBox.getChildren().add(createLabel("Duration: "));
        sessionModifyListVBox.getChildren().add(createTextField(""));

        sessionModifyListVBox.getChildren().add(createLabel("Temperature: "));
        sessionModifyListVBox.getChildren().add(createTextField(""));

        sessionModifyListVBox.getChildren().add(createLabel("Depth: "));
        sessionModifyListVBox.getChildren().add(createTextField(""));
        
        HBox sessionHBox = new HBox();
        sessionHBox.setSpacing(25);
        sessionHBox.setAlignment(javafx.geometry.Pos.CENTER);
        sessionHBox.setStyle("-fx-padding: 25px 0px 0px 0px;");

        Button sessionCreateButton = createButton("Create", "green");
        sessionCreateButton.setOnAction(event -> { try {
            Session session = new Session();
            session.setTitle(((TextField) sessionModifyListVBox.getChildren().get(2)).getText());
            
            DatePicker datePicker1 = (DatePicker) sessionModifyListVBox.getChildren().get(4);
            Date date;
            if (datePicker1.getValue() != null) {
                date = Date.valueOf(datePicker1.getValue());
            } else {
                date = null;
            }
            session.setDate(date);

            session.setComment(((TextField) sessionModifyListVBox.getChildren().get(6)).getText());

            String durationString = ((TextField) sessionModifyListVBox.getChildren().get(8)).getText();
            Float duration;
            if (durationString != null && !durationString.isEmpty()) {
                duration = Float.valueOf(durationString);
            } else {
                duration = null;
            }
            session.setDuration(duration);

            String tempString = ((TextField) sessionModifyListVBox.getChildren().get(10)).getText();
            Integer temp;
            if (tempString != null && !tempString.isEmpty()) {
                temp = Integer.valueOf(tempString);
            } else {
                temp = null;
            }
            session.setTemp(temp);

            String depthString = ((TextField) sessionModifyListVBox.getChildren().get(12)).getText();
            Integer depth;
            if (depthString != null && !depthString.isEmpty()) {
                depth = Integer.valueOf(depthString);
            } else {
                depth = null;
            }
            session.setDepth(depth);

            session.setOwner(facade.getCurrentDiver());

            facade.createSession(session);
            sessionCreateButton.getScene().getWindow().hide();
        } catch (NumberFormatException e) {
            errorMsg[0] = "Error: Duration, temperature and depth must be numbers";
            sessionErrorLabel.setText(errorMsg[0]);
        } catch (NotConnectedException | SessionAlreadyExistsException e) {
            errorMsg[0] = "Error: " + e.getMessage();
            sessionErrorLabel.setText(errorMsg[0]);
        } });
        sessionHBox.getChildren().add(sessionCreateButton);

        sessionModifyListVBox.getChildren().add(sessionHBox);

    }

    @FXML
    public void openInvitationSession(Event event, Session session) {
        try {
            InputStream fxmlStream = getClass().getResourceAsStream("/com/projetdiver/views/session/session-invitation-view.fxml");
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fxmlStream);

            SessionInvitationController sessionInvitationController = loader.getController();
            sessionInvitationController.setSessionInvitation(session);

            Stage modalStage = new Stage();
            modalStage.setTitle("Session details");

            Scene scene = new Scene(root, 1000, 400);
            scene.setUserData(this);

            modalStage.setScene(scene);
            modalStage.initOwner(((Button) event.getSource()).getScene().getWindow());
            modalStage.initModality(Modality.WINDOW_MODAL);

            modalStage.showAndWait();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
