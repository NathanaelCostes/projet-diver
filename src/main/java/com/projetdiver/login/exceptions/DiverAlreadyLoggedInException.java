package com.projetdiver.login.exceptions;

/**
 * DiverAlreadyLoggedInException is an exception that is raised when a diver is already logged in
 */
public class DiverAlreadyLoggedInException extends Exception{
    /**
     * Creates a DiverAlreadyLoggedInException
     * @param errorMessage
     */
    public DiverAlreadyLoggedInException(String errorMessage) {
        super(errorMessage);
    }
}
