package com.projetdiver.contact.exceptions;

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
