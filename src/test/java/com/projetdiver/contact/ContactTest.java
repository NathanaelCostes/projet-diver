package com.projetdiver.contact;

import com.projetdiver.contact.exceptions.ContactNotFoundException;
import com.projetdiver.diver.Diver;
import com.projetdiver.diver.DiverDAO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Class test of the Contact use case
 */
public class ContactTest {
    @Test
    void isInContactTest() {
        assertTrue(ContactFacade.getInstance().isInContact(1,2));
    }

    @Test
    void getContactTest() {
        assertThrows(ContactNotFoundException.class,
                () ->  {
            ContactFacade.getInstance().getContact(1,123);
                });
    }

}
