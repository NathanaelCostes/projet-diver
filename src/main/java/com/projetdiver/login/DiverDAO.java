package com.projetdiver.login;

/**
 * 
 */
public abstract class DiverDAO {

    private static DiverDAO instance;

    /**
     * Default constructor
     */
    public DiverDAO() {
    }

    /**
     * Fetches a diver from the database using its email
     * @param email the email to find in the database
     * @return the diver if found, null otherwise
     */
    public abstract Diver getDiver(String email);

    /**
     * 
     */
    public  static DiverDAO getInstance() {
        if (DiverDAO.instance == null) {
            return new DiverDAOPostgre();
        } else {
            return DiverDAO.instance;
        }
    }

}