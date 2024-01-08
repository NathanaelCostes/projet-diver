package com.projetdiver.spot;


import com.projetdiver.diver.DiverFacade;
import com.projetdiver.diver.exceptions.DiverAlreadyLoggedInException;
import com.projetdiver.diver.exceptions.DiverEmailNotFoundException;
import com.projetdiver.diver.exceptions.WrongPasswordException;
import com.projetdiver.session.Session;
import com.projetdiver.session.SessionFacade;
import com.projetdiver.session.exceptions.SessionAlreadyExistsException;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Class SessionTest is the class that tests the Session class
 */
public class SpotTest {

    /**
     * Test the create method of a Spot
     */
    @Test
    void createSpot() {

            Spot spot = new Spot();
            spot.setName("Spot de test 3");
            spot.setLatitude(1F);
            spot.setLongitude(1F);
            spot.setMaxDepth(1);
            spot.setType("test");
            spot.setPoi("test");
            spot.setLevel("test");

            SpotFacade spotFacade = SpotFacade.getInstance();

            assertTrue(spotFacade.createSpot(spot));

    }
}