package com.projetdiver.diver.exceptions;

/**
 * DiverEmailNotFoundException is an exception that is raised when a diver is not found in the database
 */
public class DiverEmailNotFoundException extends Exception{

    /**
     * Creates a DiverEmailNotFoundException
     * @param errorMessage
     */
    public DiverEmailNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
