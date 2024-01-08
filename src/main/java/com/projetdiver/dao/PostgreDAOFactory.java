package com.projetdiver.dao;

import com.projetdiver.contact.ContactDAO;
import com.projetdiver.diver.DiverDAO;
import com.projetdiver.review.ReviewDAO;
import com.projetdiver.session.SessionDAO;
import com.projetdiver.lesson.LessonDAO;
import com.projetdiver.spot.SpotDAO;

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
  
    public LessonDAO createLessonDAO() { 
        return LessonDAO.getInstance(); 
    }

    public SpotDAO createSpotDAO() { 
      return SpotDAO.getInstance(); 
    }

    public ContactDAO createContactDAO() {
        return ContactDAO.getInstance();
    }

    public ReviewDAO createReviewDAO() {
        return ReviewDAO.getInstance();
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