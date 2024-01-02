package com.projetdiver.diver.exceptions;

/**
 * Exception raised when the password is wrong
 */
public class WrongPasswordException extends Exception {
    /**
     * Creates a WrongPasswordException
     * @param errorMessage
     */
    public WrongPasswordException(String errorMessage) {
        super(errorMessage);
    }
}
