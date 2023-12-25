package com.projetdiver.diver;

import com.projetdiver.dao.PostgreDAOFactory;
import com.projetdiver.login.exceptions.DiverAlreadyLoggedInException;
import com.projetdiver.login.exceptions.DiverEmailNotFoundException;
import com.projetdiver.signup.exceptions.CreatingAccountFailed;
import com.projetdiver.signup.exceptions.DiverAlreadyExisting;

/**
 *
 */
public class DiverFacade {

    /**
     * Default constructor
     */
    private DiverFacade() {}

    /** the instance of the DiverFacade */
    private static DiverFacade instance;

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
        } else {
            System.out.println("Unknown error");
            throw new Exception("There is no diver with this email");
        }
    }

    public void signup(String firstName, String lastName, String email, String password)
            throws Exception, DiverAlreadyExisting, CreatingAccountFailed
    {
        // Check if the diver is already existing
        Diver diverFetched = (PostgreDAOFactory.getInstance().createDiverDAO().getDiver(email));
        if(diverFetched == null) {
            // If the diver is not existing, create it
            Diver diver = new Diver(firstName, lastName, email, password);

            boolean isAdded = PostgreDAOFactory.getInstance().createDiverDAO().addDiver(diver);
            if(isAdded) {
                System.out.println("Diver added: " + diver);
            } else {
                throw new CreatingAccountFailed("Creating account failed. Please verify your information");
            }
        } else  {
            throw new DiverAlreadyExisting("Email already existing");
        }
    }

    /**
     * get the instance of the DiverFacade
     */
    public static DiverFacade getInstance() {

        if(DiverFacade.instance == null){
            DiverFacade.instance = new DiverFacade();
        }

        return DiverFacade.instance;
    }

    /**
     * @return the current diver
     */
    public Diver getCurrentDiver() {
        return this.currentDiver;
    }

}