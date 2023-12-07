package com.projetdiver.login;

import com.projetdiver.login.exceptions.DiverEmailNotFoundException;
import com.projetdiver.login.exceptions.WrongPasswordException;
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

    @Test
    void driverEmailNotFoundShouldThrowDiverEmailNotFoundException() {
        DiverFacade diverFacade = DiverFacade.getInstance();
        assertThrows(DiverEmailNotFoundException.class,
                () -> {
                    diverFacade.login("harry.pooper@gmail.com", "1234");
                });
    }

    @Test
    void WrongPasswordShouldThrowWrongPasswordException() {
        DiverFacade diverFacade = DiverFacade.getInstance();
        assertThrows(WrongPasswordException.class,
                () -> {
                    diverFacade.login("harry.potter@gmail.com", "12345");
                });
    }


}