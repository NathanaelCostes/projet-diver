package com.projetdiver.session.exceptions;

public class InvitationAlreadyExistsException extends Exception {

        /**
        * Default constructor
        */
        public InvitationAlreadyExistsException() {
            super("Invitation already exists");
        }

        /**
        * @param message the message of the exception
        */
        public InvitationAlreadyExistsException(String message) {
            super(message);
        }
}
