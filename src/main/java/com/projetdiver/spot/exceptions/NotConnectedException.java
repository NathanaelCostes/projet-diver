package com.projetdiver.spot.exceptions;

public class NotConnectedException extends Exception {
        /**
        * Default constructor
        */
        public NotConnectedException() {
            super("Diver is not connected");
        }

        /**
        * @param message the message of the exception
        */
        public NotConnectedException(String message) {
            super(message);
        }
}
