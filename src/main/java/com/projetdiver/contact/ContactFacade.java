package com.projetdiver.contact;

import java.util.List;

import com.projetdiver.contact.exceptions.ContactAlreadyExistException;
import com.projetdiver.contact.exceptions.ContactNotFoundException;
import com.projetdiver.dao.PostgreDAOFactory;
import com.projetdiver.diver.Diver;
import com.projetdiver.diver.DiverDAO;
import com.projetdiver.diver.DiverFacade;
import com.projetdiver.session.exceptions.DiverNotFoundException;
import com.projetdiver.session.exceptions.NotConnectedException;

public class ContactFacade {

    /** the instance of the ContactFacade */
    private static ContactFacade instance;

    /** the instance of the ContactDAO */
    private final static ContactDAO contactDAO = PostgreDAOFactory.getInstance().createContactDAO();

    /**
     * Default constructor
     */
    private ContactFacade() {}

    /**
     * get the instance of the ContactFacade
     */
    public static ContactFacade getInstance() {

        if(ContactFacade.instance == null){
            ContactFacade.instance = new ContactFacade();
        }

        return ContactFacade.instance;
    }

    /**
     * @return the current diver
     */
    public Diver getCurrentDiver() {
        return DiverFacade.getInstance().getCurrentDiver();
    }

    private void checkDiverIsConnected() throws NotConnectedException {
        if(getCurrentDiver() == null){
            throw new NotConnectedException();
        } else if(getCurrentDiver().getIdInteger() == null){
            throw new NotConnectedException();
        }
    }

    /**
     * Create a contact
     * @param receiverId id of the receiver
     * @throws NotConnectedException if the diver is not connected
     * @throws DiverNotFoundException if the diver is not found
     * @throws ContactAlreadyExistException if the contact already exist
     */
    public void createContact(int receiverId) throws NotConnectedException, DiverNotFoundException, ContactAlreadyExistException {
        checkDiverIsConnected();

        Contact contact = new Contact();

        Diver receiverFetched = DiverDAO.getInstance().getDiver(receiverId);

        if (receiverFetched == null) {
            throw new DiverNotFoundException();
        } else {
            contact.setReceiver(receiverFetched);
        }

        contact.setSender(getCurrentDiver());
        contact.setPending(true);

        Contact contactFetched = contactDAO.getContact(receiverId, getCurrentDiver().getIdInteger());

        if (contactFetched != null) {
            throw new ContactAlreadyExistException();
        }

        contactDAO.create(contact);
    }

    /**
     * Accept a contact
     * @param senderId id of the sender
     * @throws NotConnectedException if the diver is not connected
     * @throws ContactNotFoundException if the contact is not found
     */
    public void acceptContact(int senderId) throws NotConnectedException, ContactNotFoundException {
        checkDiverIsConnected();

        Contact contact = contactDAO.getContact(getCurrentDiver().getIdInteger(), senderId);

        if (contact == null) {
            throw new ContactNotFoundException();
        } else {
            contact.setPending(false);
        }

        contactDAO.update(contact);
    }

    /**
     * Refuse a contact (delete it)
     * @param senderId id of the sender
     * @throws NotConnectedException if the diver is not connected
     * @throws ContactNotFoundException if the contact is not found
     */
    public void refuseContact(int senderId) throws NotConnectedException, ContactNotFoundException {
        checkDiverIsConnected();

        Contact contact = contactDAO.getContact(getCurrentDiver().getIdInteger(), senderId);

        if (contact == null) {
            throw new ContactNotFoundException();
        } else {
            contactDAO.delete(contact);
        }
    }

    /**
     * Get a contact
     * @param receiverId id of the receiver
     * @param senderId id of the sender
     * @return the contact
     */
    public Contact getContact(int receiverId, int senderId) throws ContactNotFoundException {
        Contact contact = contactDAO.getContact(receiverId, senderId);

        if (contact == null) {
            throw new ContactNotFoundException();
        } else {
            return contact;
        }
    }

    /**
     * Get all the contact of a diver (if he is the sender or the receiver)
     * @param id of the diver
     * @return the contact
     */
    public List<Contact> getAllContact(int id) {
        return contactDAO.getAllContact(id);
    }

    /**
     * Get if they are in contact (request accepted)
     * @param id of the one of the divers (sender or receiver)
     * @param id2 of the one of the divers (sender or receiver)
     * @return the contact
     */
    public boolean isInContact(int id, int id2) {
        return contactDAO.isInContact(id, id2);
    }

}