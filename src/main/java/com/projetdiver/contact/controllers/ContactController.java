package com.projetdiver.contact.controllers;

import com.projetdiver.contact.Contact;
import com.projetdiver.contact.ContactDAO;
import com.projetdiver.contact.ContactFacade;
import com.projetdiver.contact.exceptions.ContactNotFoundException;
import com.projetdiver.diver.DiverFacade;
import com.projetdiver.diver.Diver;
import com.projetdiver.session.controllers.ControllerHelper;
import com.projetdiver.session.exceptions.NotConnectedException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import static com.fxrouter.FXRouter.goTo;

public class ContactController implements Initializable, ControllerHelper {
    /**
     * The instance of the SessionFacade
     */
    private final ContactFacade facade;

    /**
     * VBox to hold the contact of the diver
     */
    @FXML
    private VBox VBoxYourContact;

    /**
     * VBox to hold the request of the diver
     */
    @FXML
    private VBox VBoxRequestContact;

    /**
     * HBox to hold menu buttons
     */
    @FXML
    private HBox contactMenuHBox;

    /**
     * Default constructor
     */
    public ContactController() {
        this.facade = ContactFacade.getInstance();
    }

    @FXML
    public void setSessionMenuHBox(){
        contactMenuHBox.getChildren().clear();

        Button sessionCreateButton = createButton("add contact", getColor("validation"));
        sessionCreateButton.setOnAction(event -> {
            openCreateContact(event);
            setVBoxYourContact();
            setVBoxRequestContact();
        });
        contactMenuHBox.getChildren().add(sessionCreateButton);

    }

    @FXML
    private void openCreateContact(ActionEvent event) {
        try {
            InputStream fxmlStream = getClass().getResourceAsStream("/com/projetdiver/views/contact/contact-create-view.fxml");
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fxmlStream);

            ContactCreateController contactCreateController = loader.getController();
            contactCreateController.setContactCreateController();

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
    public void goToMain(ActionEvent event) {
        try {
            goTo("main");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    public void setVBoxYourContact(){
        VBoxYourContact.getChildren().clear();

        Label label = new Label("Your contact");
        label.setStyle(label.getStyle() + "-fx-font-size: 25px;");
        VBoxYourContact.getChildren().add(label);

        for (Contact contact : facade.getAllContact(DiverFacade.getInstance().getCurrentDiver().getId())) {

            if(!contact.isPending() || contact.getSender().getId() == DiverFacade.getInstance().getCurrentDiver().getId()) {

                Label invitationInfoLabel = null;
                if (contact.isPending() && contact.getSender().getId() == DiverFacade.getInstance().getCurrentDiver().getId()) {
                    invitationInfoLabel = createLabel("Invitation pending");
                    invitationInfoLabel.setStyle(invitationInfoLabel.getStyle() + "-fx-text-fill: orange;");

                } else if (!contact.isPending()) {
                    invitationInfoLabel = createLabel("Invitation accepted");
                    invitationInfoLabel.setStyle(invitationInfoLabel.getStyle() + "-fx-text-fill: green;");
                }

                Diver contactDiver;
                if (contact.getReceiver().getId() == DiverFacade.getInstance().getCurrentDiver().getId()) {
                    contactDiver = contact.getSender();
                } else {
                    contactDiver = contact.getReceiver();
                }

                HBox yourContactHBox = new HBox();
                yourContactHBox.setAlignment(javafx.geometry.Pos.CENTER);
                yourContactHBox.setSpacing(5);

                Label contactLabel = new Label(contactDiver.getFirstName() + " " + contactDiver.getLastName());
                yourContactHBox.getChildren().add(contactLabel);

                yourContactHBox.getChildren().add(separator());

                yourContactHBox.getChildren().add(invitationInfoLabel);

                yourContactHBox.getChildren().add(separator());

                Button contactButton = createButton("Delete", getColor("suppression"));
                contactButton.setOnAction(event -> {
                    ContactDAO.getInstance().delete(contact);
                    setVBoxYourContact();
                });
                yourContactHBox.getChildren().add(contactButton);

                VBoxYourContact.getChildren().add(yourContactHBox);
            }
        }
    }

    @FXML
    public void setVBoxRequestContact(){
        VBoxRequestContact.getChildren().clear();

        Label label = new Label("Request");
        label.setStyle(label.getStyle() + "-fx-font-size: 25px;");
        VBoxRequestContact.getChildren().add(label);

        for (Contact contact : facade.getAllContact(DiverFacade.getInstance().getCurrentDiver().getId())) {
            if(contact.isPending() && contact.getReceiver().getId() == DiverFacade.getInstance().getCurrentDiver().getId()) {
                Diver contactDiver = contact.getSender();

                HBox yourContactHBox = new HBox();
                yourContactHBox.setAlignment(javafx.geometry.Pos.CENTER);
                yourContactHBox.setSpacing(5);

                Label contactLabel = new Label(contactDiver.getFirstName() + " " + contactDiver.getLastName());
                yourContactHBox.getChildren().add(contactLabel);

                yourContactHBox.getChildren().add(separator());

                Button contactButton = createButton("Accept", getColor("validation"));
                contactButton.setOnAction(event -> {
                    try {
                        facade.acceptContact(contact.getSender().getId());
                    } catch (NotConnectedException | ContactNotFoundException e) {
                        System.err.println(e.getMessage());
                    }
                    setVBoxRequestContact();
                    setVBoxYourContact();
                });
                yourContactHBox.getChildren().add(contactButton);

                Button contactButton2 = createButton("Decline", getColor("suppression"));
                contactButton2.setOnAction(event -> {
                    try {
                        facade.refuseContact(contact.getSender().getId());
                    } catch (NotConnectedException | ContactNotFoundException e) {
                        System.err.println(e.getMessage());
                    }
                    setVBoxRequestContact();
                });
                yourContactHBox.getChildren().add(contactButton2);

                VBoxRequestContact.getChildren().add(yourContactHBox);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setSessionMenuHBox();
        setVBoxYourContact();
        setVBoxRequestContact();
    }
}
