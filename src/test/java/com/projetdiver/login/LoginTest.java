package com.projetdiver.login;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    @Test
    void driverAlreadyLoggedShouldThrowDiverAlreadyLoggedInException() {
        DiverFacade diverFacade = DiverFacade.getInstance();
        try {
            diverFacade.login("harry.potter@gmail.com", "1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}