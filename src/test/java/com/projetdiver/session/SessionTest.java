package com.projetdiver.session;


import com.projetdiver.diver.DiverFacade;
import com.projetdiver.diver.exceptions.DiverAlreadyLoggedInException;
import com.projetdiver.diver.exceptions.DiverEmailNotFoundException;
import com.projetdiver.diver.exceptions.WrongPasswordException;

import com.projetdiver.session.exceptions.SessionAlreadyExistsException;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Class SessionTest is the class that tests the Session class
 * @author Costes
 */
public class SessionTest {

    /**
     * Test the create method of a Session
     */
    @Test
    void createSessionAlreadyExistingShouldThrowSessionAlreadyExistsException() {
        
        Session session = new Session();
        session.setSessionId(1);
        session.setTitle("Session de test");
        session.setDate(Date.valueOf("2019-01-01"));

        /*try {
            DiverFacade.getInstance().login("zac.jungler@riotgames.com", "1234");
        } catch (DiverAlreadyLoggedInException | WrongPasswordException | DiverEmailNotFoundException e) {
            throw new RuntimeException(e);
        }*/

        session.setOwner(DiverFacade.getInstance().getCurrentDiver());

        SessionFacade sessionFacade = SessionFacade.getInstance();
        assertThrows(SessionAlreadyExistsException.class,
                () -> sessionFacade.createSession(session));
    }
}