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
     * 
     */
    public abstract void getDiver();

    /**
     * 
     */
    public  static DiverDAO getInstance() {
        if (DiverDAO.instance == null) {
            //TODO
        } else {
            return DiverDAO.instance;
        }

        return null; // bouchon
    }

}