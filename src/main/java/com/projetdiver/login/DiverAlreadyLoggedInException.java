package com.projetdiver.login;

public class DiverAlreadyLoggedInException extends Exception{
    public DiverAlreadyLoggedInException(String errorMessage) {
        super(errorMessage);
    }
}
