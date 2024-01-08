package com.projetdiver.contact.controllers;

import com.projetdiver.contact.ContactDAO;
import com.projetdiver.contact.ContactFacade;
import com.projetdiver.contact.exceptions.ContactAlreadyExistException;
import com.projetdiver.diver.DiverDAO;
import com.projetdiver.diver.DiverFacade;
import com.projetdiver.ControllerHelper;
import com.projetdiver.session.exceptions.DiverNotFoundException;
import com.projetdiver.session.exceptions.NotConnectedException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class is the controller of the contact create view
 */
public class ContactCreateController implements ControllerHelper {
    /**
     * The instance of the SessionFacade
     */
    private final ContactFacade facade;

    /**
     * Default constructor
     */
    public ContactCreateController() {
        this.facade = ContactFacade.getInstance();
    }

    /**
     * VBox to hold all diver
     */
    @FXML
    VBox VBoxAddContact;

    /**
     * Is called when the user clicks on the add contact button.
     * Represents the add contact view with all the diver that can be added as contact
     */
    public void setContactCreateController() {
        VBoxAddContact.getChildren().clear();

        DiverDAO.getInstance().getAllDivers().forEach(diver -> {
            if (diver.getId() != DiverFacade.getInstance().getCurrentDiver().getId()) {
                if(!facade.isInContact(diver.getId(), DiverFacade.getInstance().getCurrentDiver().getId())) {
                    if(ContactDAO.getInstance().getContact(diver.getId(), DiverFacade.getInstance().getCurrentDiver().getId()) == null &&
                            ContactDAO.getInstance().getContact(DiverFacade.getInstance().getCurrentDiver().getId(), diver.getId()) == null) {
                        HBox diverHBox = new HBox();

                        diverHBox.setSpacing(5);
                        diverHBox.setAlignment(javafx.geometry.Pos.CENTER);

                        Label diverLabel = new Label(diver.getFirstName() + " " + diver.getLastName());
                        diverHBox.getChildren().add(diverLabel);

                        diverHBox.getChildren().add(separator());

                        Button diverButton = createButton("add", getColor("validation"));
                        diverButton.setOnAction(event -> {
                            try {
                                facade.createContact(diver.getId());
                            } catch (NotConnectedException | DiverNotFoundException | ContactAlreadyExistException e) {
                                e.printStackTrace();
                            }
                            VBoxAddContact.getChildren().remove(diverHBox);
                        });
                        diverHBox.getChildren().add(diverButton);

                        VBoxAddContact.getChildren().add(diverHBox);
                    }
                }
            }
        });
    }
}
