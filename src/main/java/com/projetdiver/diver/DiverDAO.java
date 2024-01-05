package com.projetdiver.diver;

import java.util.List;

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
     * @param id the id to find in the database
     * @return the diver if found, null otherwise
     */
    public abstract Diver getDiver(int id);
  
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
     * Get all the divers from the database
     * @return the list of all the divers
     */
    public abstract List<Diver> getAllDivers();

    /**
     * Delete a diver from the database
     * @param email the email of the diver to delete
     */
    public abstract void deleteDiverByEmail(String email);

    /**
     * Get all the students of a diver (professor)
     * @param diverId the id of the professor diver
     */
    public abstract List<Diver> getAllStudents(int diverId);

    /**
     * get the instance of the DiverDAO
     * Not thread safe
     */
    public  static DiverDAO getInstance() {
        if (DiverDAO.instance == null) {
            instance = new DiverDAOPostgre();
            return instance;
        } else {
            return DiverDAO.instance;
        }
    }

}