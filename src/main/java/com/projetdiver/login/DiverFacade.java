package com.projetdiver.login;

import java.util.*;

/**
 * 
 */
public class DiverFacade {

    /**
     * Default constructor
     */
    private DiverFacade() {
    }

    //TODO Dire qui est le current driver

    private static DiverFacade instance;

    Diver diver;

    /**
     * @param email
     * @param password
     */
    public void login(String email, String password) {

        Diver diverFetched = (PostgreDAOFactory.getInstance().createDiverDAO().getDiver(email));
        if(this.diver == null && diverFetched!=null){

            boolean succeeded = diverFetched.login(email, password);
            if (succeeded) {
                this.diver = diverFetched;
                System.out.println("Diver fetched: " + this.diver);
            }
        } else {
            System.out.println("Diver already logged in");
            //TODO throw exception
        }
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

    public Diver getDiver() {
        return diver;
    }
}