package com.projetdiver.diver;

/**
 * Abstract class for the DiverDAO
 */
public abstract class DiverDAO {

    /** instance de DiverDAO Postgre */
    private static DiverDAO instance;

    /**
     * Default constructor
     */
    public DiverDAO() {}

    /**
     * Fetches a diver from the database using its email
     * @param email the email to find in the database
     * @return the diver if found, null otherwise
     */
    public abstract Diver getDiver(String email);

    /**
     * Add a diver to the database
     * @param diver the diver to add
     * @return true if the diver is added, false otherwise
     */
    public abstract boolean addDiver(Diver diver);

    /**
     * get the instance of the DiverDAO
     * Not thread safe
     */
    public  static DiverDAO getInstance() {
        if (DiverDAO.instance == null) {
            return new DiverDAOPostgre();
        } else {
            return DiverDAO.instance;
        }
    }

}