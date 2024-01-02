package com.projetdiver.session.exceptions;

public class DiverNotFoundException extends Exception {

        /**
        * Default constructor
        */
        public DiverNotFoundException() {
            super("Diver not found");
        }

        /**
        * @param message the message of the exception
        */
        public DiverNotFoundException(String message) {
            super(message);
        }
}
