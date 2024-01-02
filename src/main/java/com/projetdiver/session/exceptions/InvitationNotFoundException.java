package com.projetdiver.session.exceptions;

public class InvitationNotFoundException extends Exception {

            /**
            * Default constructor
            */
            public InvitationNotFoundException() {
                super("Invitation not found");
            }

            /**
            * @param message the message of the exception
            */
            public InvitationNotFoundException(String message) {
                super(message);
            }
}
