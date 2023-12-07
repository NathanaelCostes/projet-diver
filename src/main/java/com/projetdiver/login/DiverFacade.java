package com.projetdiver.login;

import com.projetdiver.dao.PostgreDAOFactory;
import com.projetdiver.diver.Diver;
import com.projetdiver.login.exceptions.DiverAlreadyLoggedInException;

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
    public void login(String email, String password) throws Exception {

        Diver diverFetched = (PostgreDAOFactory.getInstance().createDiverDAO().getDiver(email));
        if(this.diver == null && diverFetched!=null){

            boolean succeeded = diverFetched.login(email, password);

            if (succeeded) {
                this.diver = diverFetched;
                System.out.println("Diver fetched: " + this.diver);
            }
        } else if(this.diver != null) {
            System.out.println("Diver already logged in");
            throw new DiverAlreadyLoggedInException("Diver already logged in");

        } else if(diverFetched == null){
            System.out.println("There is no diver with this email");
            throw new Exception("There is no diver with this email");

        } else {
            System.out.println("Unknown error");
            throw new Exception("There is no diver with this email");
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