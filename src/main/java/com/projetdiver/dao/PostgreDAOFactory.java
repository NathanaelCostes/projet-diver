package com.projetdiver.dao;

import com.projetdiver.diver.DiverDAO;
import com.projetdiver.lesson.LessonDAO;

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

    public LessonDAO createLessonDAO() { return LessonDAO.getInstance(); }

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