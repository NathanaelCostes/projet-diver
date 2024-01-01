package com.projetdiver.diver;

import com.projetdiver.FXRouter;
import com.projetdiver.dao.PostgreDAOFactory;
import com.projetdiver.diver.exceptions.*;

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
    public void login(String email, String password) throws DiverAlreadyLoggedInException,
            DiverEmailNotFoundException, WrongPasswordException {

        Diver diverFetched = (PostgreDAOFactory.getInstance().createDiverDAO().getDiver(email));
        System.out.println("Diver fetched: \n" + diverFetched);
        // If the diver is not logged in and the diver is found in the database
        System.out.println("Current diver: " + this.currentDiver);
        if(this.currentDiver == null && diverFetched!=null){

            boolean succeeded = diverFetched.login(email, password);

            if (succeeded) {
                this.currentDiver = diverFetched;
                try {
                    FXRouter.goTo("main");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Diver fetched: " + this.currentDiver);
            }
        } else if(this.currentDiver != null) {
            throw new DiverAlreadyLoggedInException("Diver already logged in");
        } else if(diverFetched == null){
            System.out.println("There is no diver with this email");
            throw new DiverEmailNotFoundException("There is no diver with this email");
        }
    }

    /**
     * Create a diver account and add it to the database
     * @param firstName the first name of the diver
     * @param lastName the last name of the diver
     * @param email the email of the diver
     * @param password the password of the diver
     * @throws Exception an undescribed exception
     * @throws DiverAlreadyExisting if a diver with the same email is already existing
     * @throws CreatingAccountFailed if the account creation failed for any reason (SQL error, ...)
     */
    public void signup(String firstName, String lastName, String email, String password)
            throws Exception, DiverAlreadyExisting, CreatingAccountFailed
    {
        // Check if the diver is already existing
        Diver diverFetched = (PostgreDAOFactory.getInstance().createDiverDAO().getDiver(email));
        if(diverFetched == null) {
            // If the diver is not existing, create it
            Diver diver = new Diver(null, email, password, lastName, firstName );

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
     * Update the first name of the current diver
     * @param firstName the new first name of the diver
     */
    public void updateDiverFirstName(String firstName) {
        PostgreDAOFactory.getInstance().createDiverDAO().updateDiverFirstName(this.currentDiver, firstName);
        this.currentDiver.setFirstName(firstName);
    }

    /**
     * Update the last name of the current diver
     * @param lastName the new last name of the diver
     */
    public void updateDiverLastName(String lastName) {
        PostgreDAOFactory.getInstance().createDiverDAO().updateDiverLastName(this.currentDiver, lastName);
        this.currentDiver.setLastName(lastName);
    }

    /**
     * Update the email of the current diver
     * @param email the new email of the diver
     */
    public void updateDiverEmail(String email) {
        PostgreDAOFactory.getInstance().createDiverDAO().updateDiverEmail(this.currentDiver, email);
        this.currentDiver.setEmail(email);
    }

    /**
     * Update the password of the current diver
     * @param password the new password of the diver
     */
    public void updateDiverPassword(String password) {
        PostgreDAOFactory.getInstance().createDiverDAO().updateDiverPassword(this.currentDiver, password);
        this.currentDiver.setPassword(password);
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