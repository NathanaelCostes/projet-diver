package com.projetdiver.contact.exceptions;

/**
 * This exception is thrown when the contact already exist
 */
public class ContactAlreadyExistException extends Exception {

    /**
     * Default constructor
     */
    public ContactAlreadyExistException() {
        super("Contact already exist");
    }

    /**
     * @param message of the exception
     */
    public ContactAlreadyExistException(String message) {
        super(message);
    }

}
