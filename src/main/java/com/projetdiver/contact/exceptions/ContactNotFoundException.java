package com.projetdiver.contact.exceptions;

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
