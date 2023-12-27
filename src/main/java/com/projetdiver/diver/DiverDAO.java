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
     * Update the first name of the diver
     * @param diver the diver to update
     * @param firstName the new first name of the diver
     */
    public abstract void updateDiverFirstName(Diver diver, String firstName);

    /**
     * Update the last name of the diver
     * @param diver the diver to update
     * @param lastName the new last name of the diver
     */
    public abstract void updateDiverLastName(Diver diver, String lastName);

    /**
     * Update the email of the diver
     * @param diver the diver to update
     * @param email the new email of the diver
     */
    public abstract void updateDiverEmail(Diver diver, String email);

    /**
     * Update the password of the diver
     * @param diver the diver to update
     * @param password the new password of the diver
     */
    public abstract void updateDiverPassword(Diver diver, String password);

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