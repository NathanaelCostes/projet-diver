package com.projetdiver.session.controllers;

import java.util.ArrayList;
import java.util.List;

import com.projetdiver.ControllerHelper;
import com.projetdiver.contact.ContactFacade;
import com.projetdiver.diver.Diver;
import com.projetdiver.diver.DiverDAO;
import com.projetdiver.session.Session;
import com.projetdiver.session.SessionFacade;
import com.projetdiver.session.exceptions.DiverNotFoundException;
import com.projetdiver.session.exceptions.InvitationAlreadyExistsException;
import com.projetdiver.session.exceptions.InvitationNotFoundException;
import com.projetdiver.session.exceptions.NotConnectedException;
import com.projetdiver.session.exceptions.SessionNotFoundException;
import com.projetdiver.session.Invitation;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//TODO: Implement this class with contact list not diver list
//TODO: Replace DAO with Facade

public class SessionInvitationController implements ControllerHelper {
    /**
     * The instance of the SessionFacade
     */
    private final SessionFacade facade;

    /**
     * The instance of the DiverFacade
     */
    private final DiverDAO diverDAO;

    /**
     * VBox to hold the information about the diver already invited
     */
    @FXML
    private VBox sessionAlreadyInvitedListVBox;

    /**
     * VBox to hold the information about the diver not invited yet
     */
    @FXML
    private VBox sessionNotInvitedListVBox;

    /**
     * Default constructor
     */
    public SessionInvitationController() {
        this.facade = SessionFacade.getInstance();
        this.diverDAO = DiverDAO.getInstance();
    }

    /**
     * Initialize the session list
     */
    @FXML
    public void initialize() {
    }

    public void setSessionInvitation(Session session){
        sessionAlreadyInvitedListVBox.getChildren().clear();
        sessionNotInvitedListVBox.getChildren().clear();

        sessionAlreadyInvitedListVBox.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-border-radius: 5px;");
        sessionNotInvitedListVBox.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-border-radius: 5px;");

        sessionAlreadyInvitedListVBox.setAlignment(javafx.geometry.Pos.CENTER);
        sessionNotInvitedListVBox.setAlignment(javafx.geometry.Pos.CENTER);

        sessionAlreadyInvitedListVBox.setSpacing(5);
        sessionNotInvitedListVBox.setSpacing(5);

        try {
            ArrayList<Invitation> invitations = facade.getAllInvitationsToSession(session);
            ArrayList<Diver> diversInSession = new ArrayList<>();
            
            invitations.forEach(invitation -> {
                Diver diver = diverDAO.getDiver(invitation.getReceiver().getId());
                diversInSession.add(diver);
                Label invitationInfoLabel;
                if(invitation.isPending()) {
                    invitationInfoLabel = createLabel("Invitation pending");
                    invitationInfoLabel.setStyle(invitationInfoLabel.getStyle() + "-fx-text-fill: orange;");
                    
                } else {
                    invitationInfoLabel = createLabel("Invitation accepted");
                    invitationInfoLabel.setStyle(invitationInfoLabel.getStyle() + "-fx-text-fill: green;");
                }
                HBox invitationHBox = new HBox();
                invitationHBox.setSpacing(5);
                invitationHBox.setAlignment(javafx.geometry.Pos.CENTER);
                invitationHBox.getChildren().add(createLabel(diver.getLastName()));
                invitationHBox.getChildren().add(separator());
                invitationHBox.getChildren().add(createLabel(diver.getFirstName()));
                invitationHBox.getChildren().add(separator());
                invitationHBox.getChildren().add(invitationInfoLabel);
                invitationHBox.getChildren().add(separator());
                
                Button deleteButton = createButton("Delete", getColor("suppression"));
                deleteButton.setOnAction(event -> {
                    try {
                        facade.deleteInvitation(invitation);
                    } catch (NotConnectedException | SessionNotFoundException | DiverNotFoundException | InvitationNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    setSessionInvitation(session);
                });
                invitationHBox.getChildren().add(deleteButton);

                sessionAlreadyInvitedListVBox.getChildren().add(invitationHBox);
            });

            List<Diver> diversInContact = new ArrayList<>(ContactFacade.getInstance().
                    getAllContact(facade.getCurrentDiver().getId())
                    .stream().filter(contact -> !contact.isPending())
                    .map(contact -> {
                        if (contact.getReceiver().getId() == facade.getCurrentDiver().getId()) {
                            return contact.getSender();
                        } else {
                            return contact.getReceiver();
                        }
                    }).toList());
            // Remove the diver already in the session
            diversInContact.removeAll(diversInSession);
            // Remove the current diver
            diversInContact.remove(facade.getCurrentDiver());

            diversInContact.forEach(diver -> {
                HBox invitationHBox = new HBox();
                invitationHBox.setSpacing(5);
                invitationHBox.setAlignment(javafx.geometry.Pos.CENTER);
                invitationHBox.getChildren().add(createLabel(diver.getLastName()));
                invitationHBox.getChildren().add(separator());
                invitationHBox.getChildren().add(createLabel(diver.getFirstName()));
                invitationHBox.getChildren().add(separator());

                Button inviteButton = createButton("Invite", getColor("information"));
                inviteButton.setOnAction(event -> {
                    try {
                        facade.createInvitation(new Invitation(session, diver, true));
                    } catch (NotConnectedException | SessionNotFoundException | DiverNotFoundException | InvitationAlreadyExistsException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    setSessionInvitation(session);
                });
                invitationHBox.getChildren().add(inviteButton);

                sessionNotInvitedListVBox.getChildren().add(invitationHBox);
            });

        } catch (NotConnectedException | SessionNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void setSessionInvitation(Diver currentDiver) {
        sessionAlreadyInvitedListVBox.getChildren().clear();
        sessionNotInvitedListVBox.getChildren().clear();

        sessionAlreadyInvitedListVBox.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-border-radius: 5px;");
        sessionNotInvitedListVBox.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-border-radius: 5px;");

        sessionAlreadyInvitedListVBox.setAlignment(javafx.geometry.Pos.CENTER);
        sessionNotInvitedListVBox.setAlignment(javafx.geometry.Pos.CENTER);

        sessionAlreadyInvitedListVBox.setSpacing(5);
        sessionNotInvitedListVBox.setSpacing(5);

        try {
            ArrayList<Invitation> invitations = facade.getAllInvitationsToDiver();
            
            List<Invitation> invitationsAccepted = invitations.stream().filter(invitation -> !invitation.isPending()).toList();

            invitationsAccepted.forEach(invitation -> {

                try {
                    Session session = facade.getSession(invitation.getSession().getSessionId());
                
                    Label invitationInfoLabel;
                    if(invitation.isPending()) {
                        invitationInfoLabel = createLabel("Invitation pending");
                        invitationInfoLabel.setStyle(invitationInfoLabel.getStyle() + "-fx-text-fill: orange;");
                        
                    } else {
                        invitationInfoLabel = createLabel("Invitation accepted");
                        invitationInfoLabel.setStyle(invitationInfoLabel.getStyle() + "-fx-text-fill: green;");
                    }
                    HBox invitationHBox = new HBox();
                    invitationHBox.setAlignment(javafx.geometry.Pos.CENTER);
                    invitationHBox.getChildren().add(createLabel(session.getTitle()));
                    invitationHBox.getChildren().add(separator());
                    invitationHBox.getChildren().add(createLabel(session.getDate().toString()));
                    invitationHBox.getChildren().add(separator());
                    invitationHBox.getChildren().add(invitationInfoLabel);
                    invitationHBox.getChildren().add(separator());
                    invitationHBox.getChildren().add(createLabel(session.getOwner().getLastName() + " " + session.getOwner().getFirstName()));
                    invitationHBox.getChildren().add(separator());
                    
                    Button deleteButton = createButton("Delete", getColor("suppression"));
                    deleteButton.setOnAction(event -> {
                        try {
                            facade.deleteInvitation(invitation);
                        } catch (NotConnectedException | SessionNotFoundException | DiverNotFoundException | InvitationNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        setSessionInvitation(currentDiver);
                    });
                    invitationHBox.getChildren().add(deleteButton);

                    sessionAlreadyInvitedListVBox.getChildren().add(invitationHBox);
                } catch (NotConnectedException | SessionNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });

            List<Invitation> invitationsNotAccepted = invitations.stream().filter(invitation -> invitation.isPending()).toList();
            List<Session> sessionsNotInvited = invitationsNotAccepted.stream().map(invitation -> invitation.getSession()).toList();

            sessionsNotInvited.forEach(session -> {
                HBox invitationHBox = new HBox();
                invitationHBox.setAlignment(javafx.geometry.Pos.CENTER);
                invitationHBox.getChildren().add(createLabel(session.getTitle()));
                invitationHBox.getChildren().add(separator());
                invitationHBox.getChildren().add(createLabel(session.getDate().toString()));
                invitationHBox.getChildren().add(separator());
                invitationHBox.getChildren().add(createLabel(session.getOwner().getLastName() + " " + session.getOwner().getFirstName()));
                invitationHBox.getChildren().add(separator());

                Button inviteButton = createButton("Accept", getColor("validation"));
                inviteButton.setOnAction(event -> {
                    try {
                        facade.acceptInvitation(facade.getInvitation(session, currentDiver));
                    } catch (NotConnectedException | SessionNotFoundException | DiverNotFoundException | InvitationNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    setSessionInvitation(currentDiver);
                });
                invitationHBox.getChildren().add(inviteButton);

                invitationHBox.getChildren().add(separator());

                Button refuseButton = createButton("Refuse", getColor("suppression"));
                refuseButton.setOnAction(event -> {
                    try {
                        facade.deleteInvitation(facade.getInvitation(session, currentDiver));
                    } catch (NotConnectedException | SessionNotFoundException | DiverNotFoundException | InvitationNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    setSessionInvitation(currentDiver);
                });
                invitationHBox.getChildren().add(refuseButton);

                sessionNotInvitedListVBox.getChildren().add(invitationHBox);
            });

        } catch (NotConnectedException e) {
            e.printStackTrace();
        }

    }

        
}
