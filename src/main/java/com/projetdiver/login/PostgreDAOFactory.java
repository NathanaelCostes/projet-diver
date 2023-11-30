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
    public String port;

    /**
     * 
     */
    public DiverDAO createDiverDAO() {
        // TODO implement here
        return null;
    }

    public static PostgreDAOFactory getInstance(){
        if(PostgreDAOFactory.instance == null){
            //TODO
        } else {
            return PostgreDAOFactory.instance;
        }

        return null; // bouchon TODO
    }

}