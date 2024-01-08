package com.projetdiver.diver;

import com.projetdiver.diver.exceptions.DiverAlreadyLoggedInException;
import com.projetdiver.diver.exceptions.DiverEmailNotFoundException;
import com.projetdiver.diver.exceptions.WrongPasswordException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class LoginTest is the class that tests the login method of the DiverFacade
 */
public class LoginTest {

    /**
     * Test the login method of the DiverFacade when the diver is already logged in
     */
    @Test
    void driverAlreadyLoggedShouldThrowDiverAlreadyLoggedInException() {
        DiverFacade diverFacade = DiverFacade.getInstance();
        assertThrows(DiverAlreadyLoggedInException.class,
                () -> {
                    diverFacade.login("yasuo.midlaner@riotgames.com", "1234");
                    diverFacade.login("yasuo.midlaner@riotgames.com", "1234");
                });

    }

    /**
     * Test the login method of the DiverFacade with a wrong email
     */
    @Test
    void driverEmailNotFoundShouldThrowDiverEmailNotFoundException() {
        DiverFacade diverFacade = DiverFacade.getInstance();
        assertThrows(DiverEmailNotFoundException.class,
                () -> {
                    diverFacade.login("yasuo.inter@riotgames.com", "1234");
                });
    }

    /**
     * Test the login method of the DiverFacade with a wrong password
     */
    @Test
    void WrongPasswordShouldThrowWrongPasswordException() {
        DiverFacade diverFacade = DiverFacade.getInstance();
        assertThrows(WrongPasswordException.class,
                () -> {
                    diverFacade.login("yasuo.midlaner@riotgames.com", "12345");
                });
    }


}