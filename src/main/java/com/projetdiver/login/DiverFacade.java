package com.projetdiver.login;

import java.util.*;

/**
 * 
 */
public class DiverFacade {

    /**
     * Default constructor
     */
    public DiverFacade() {
    }

    //TODO Dire qui est le current driver

    private static DiverFacade instance;

    Diver diver;

    /**
     * @param email
     * @param password
     */
    public void login(String email, String password) {
        PostgreDAOFactory.getInstance().createDiverDAO().getDiver(email);
        this.getCurrentDiver().login(email, password);
    }

    /**
     *
     */
    public static DiverFacade getInstance() {

        if(DiverFacade.instance == null){
            DiverFacade.instance = new DiverFacade();
        }

        return DiverFacade.instance;
    }

    /**
     * 
     */
    public Diver getCurrentDiver() {
        return this.diver;
    }

}