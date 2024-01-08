package com.projetdiver.contact.exceptions;

/**
 * This exception is thrown when the contact is not found
 */
public class ContactNotFoundException extends Exception {

    /**
     * Default constructor
     */
    public ContactNotFoundException() {
        super("Contact not found");
    }

    /**
     * @param message the message of the exception
     */
    public ContactNotFoundException(String message) {
        super(message);
    }

}
