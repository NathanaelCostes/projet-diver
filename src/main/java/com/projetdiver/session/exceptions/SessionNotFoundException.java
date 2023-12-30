package com.projetdiver.session.exceptions;

public class SessionNotFoundException extends Exception {

        /**
        * Default constructor
        */
        public SessionNotFoundException() {
            super("Session not found");
        }

        /**
        * @param message the message of the exception
        */
        public SessionNotFoundException(String message) {
            super(message);
        }
}
