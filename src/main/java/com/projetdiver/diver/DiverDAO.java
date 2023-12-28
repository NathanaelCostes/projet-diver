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
     * Fetches a diver from the database using its email
     * @param diverId the id to find in the database
     * @return the diver if found, null otherwise
     */
    public abstract Diver getDiver(int diverId);

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