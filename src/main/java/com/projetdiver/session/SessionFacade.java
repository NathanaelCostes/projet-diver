package com.projetdiver.session;

import com.projetdiver.dao.PostgreDAOFactory;
import com.projetdiver.diver.Diver;
import com.projetdiver.diver.DiverFacade;
import com.projetdiver.login.exceptions.DiverAlreadyLoggedInException;
import com.projetdiver.login.exceptions.DiverEmailNotFoundException;

public class SessionFacade {

    /**
     * Default constructor
     */
    private SessionFacade() {}

    /** the instance of the SessionFacade */
    private static SessionFacade instance;

    /** currentDiver the current diver using the application */
    private Diver currentDiver;

    /**
     * Login the diver and set the current diver to the diver that logged in
     * @param email the email of the diver
     * @param password the password of the diver
     * @throws Exception an undescribed exception
     * @throws DiverAlreadyLoggedInException if the diver is already logged in
     * @throws DiverEmailNotFoundException if the email of the diver is not found
     */
    public void login(String email, String password) throws Exception,
            DiverAlreadyLoggedInException,
            DiverEmailNotFoundException {

        Diver diverFetched = (PostgreDAOFactory.getInstance().createDiverDAO().getDiver(email));

        // If the diver is not logged in and the diver is found in the database
        if(this.currentDiver == null && diverFetched!=null){

            boolean succeeded = diverFetched.login(email, password);
            if (succeeded) {
                this.currentDiver = diverFetched;
                System.out.println("Diver fetched: " + this.currentDiver);
            }
        } else if(this.currentDiver != null) {
            throw new DiverAlreadyLoggedInException("Diver already logged in");
        } else if(diverFetched == null){
            throw new DiverEmailNotFoundException("There is no diver with this email");
        } else { // Useless because the diverFetched is null
            System.out.println("Unknown error");
            throw new Exception("There is no diver with this email");
        }
    }

    /**
     * get the instance of the SessionFacade
     */
    public static SessionFacade getInstance() {

        if(SessionFacade.instance == null){
            SessionFacade.instance = new SessionFacade();
        }

        return SessionFacade.instance;
    }

    /**
     * @return the current diver
     */
    public Diver getCurrentDiver() {
        return DiverFacade.getInstance().getCurrentDiver();
    }
}
