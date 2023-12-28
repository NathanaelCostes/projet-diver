package com.projetdiver.dao;

import com.projetdiver.diver.DiverDAO;
import com.projetdiver.session.SessionDAO;

/**
 * 
 */
public class PostgreDAOFactory implements DAOFactory {

    private static PostgreDAOFactory instance;

    /**
     * Default constructor
     */
    private PostgreDAOFactory() {
    }

    /**
     * 
     */
    public DiverDAO createDiverDAO() {
        return DiverDAO.getInstance();
    }

    public SessionDAO createSessionDAO() {
        return SessionDAO.getInstance();
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