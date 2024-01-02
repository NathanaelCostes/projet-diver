package com.projetdiver.session.exceptions;

public class SessionAlreadyExistsException extends Exception {

    /**
     * Default constructor
     */
    public SessionAlreadyExistsException() {
        super("Session already exists");
    }

    /**
     * Constructor with message
     * @param message the message of the exception
     */
    public SessionAlreadyExistsException(String message) {
        super(message);
    }
}
