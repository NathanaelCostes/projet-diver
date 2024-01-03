package com.projetdiver.contact;

import java.util.List;

/**
 * This class is the DAO of the Contact
 * @author Boudier
 */
public abstract class ContactDAO {

    /** instance of ContactDAO Postgre */
    private static ContactDAO instance;

    /**
     * Default constructor
     */
    public ContactDAO() {}

    /**
     * get the instance of the ContactDAO
     * Not thread safe
     */
    public  static ContactDAO getInstance() {
        if (ContactDAO.instance == null) {
            return new ContactDAOPostgre();
        } else {
            return ContactDAO.instance;
        }
    }

    /**
     * @param contact to create
     */
    public abstract void create(Contact contact);

    /**
     * @param contact to update
     */
    public abstract void update(Contact contact);

    /**
     * @param contact to delete
     */
    public abstract void delete(Contact contact);

    /**
     * Get contact, order is not important
     * @param receiver id of the receiver (diver)
     * @param sender id of the sender (diver)
     * @return the contact
     */
    public abstract Contact getContact(int receiver, int sender);

    /**
     * Get all the contact of a diver (if he is the sender or the receiver)
     * @param id of the diver
     * @return the contact
     */
    public abstract List<Contact> getAllContact(int id);

    /**
     * Get if they are in contact (request accepted)
     * @param id1 of the one of the divers (sender or receiver)
     * @param id2 of the one of the divers (sender or receiver)
     * @return the contact
     */
    public abstract boolean isInContact(int id1, int id2);

}
