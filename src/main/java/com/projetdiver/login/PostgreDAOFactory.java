package com.projetdiver.login;

/**
 * 
 */
public class PostgreDAOFactory implements DAOFactory {

    private static PostgreDAOFactory instance;

    /**
     * Default constructor
     */
    public PostgreDAOFactory() {
    }

    /**
     * 
     */
    public DiverDAO createDiverDAO() {
        return new DiverDAOPostgre();
    }

    /**
     * Pas thread safe
     */
    public static PostgreDAOFactory getInstance(){
        if(PostgreDAOFactory.instance == null){
            PostgreDAOFactory.instance = new PostgreDAOFactory();
        }
        return PostgreDAOFactory.instance;
    }

}