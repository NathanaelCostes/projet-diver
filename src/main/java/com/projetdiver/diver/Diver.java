package com.projetdiver.diver;

import com.projetdiver.login.exceptions.WrongPasswordException;

/**
 * Diver is a class that represents a diver
 * <ul>
 *     <li> You can login a diver </li>
 * </ul>
 */
public class Diver {

    /** Id of the diver */
    private int diverId;

    /** Email of the diver */
    private String email;
    /** Password of the user */
    private String password;

    /** Nom of the user */
    private String nom;

    /** Prenom of the user */
    private String prenom;

    /**
     * Creates a Diver
     * @param diverId
     * @param email
     * @param password
     */
    public Diver(int diverId, String email, String password, String nom, String prenom) {
        this.diverId = diverId;
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
    }

    /** Default Constructor */
    public Diver(){}

    /**
     * Login the user and print a message if the login is successful
     * If the login is not successful, raise an exception
     * @param email
     * @param pwd
     * @throws WrongPasswordException
     */
    public boolean login(String email, String pwd) throws WrongPasswordException {
        if(email.equals(this.email) && pwd.equals(this.password)) {
            System.out.println("Login success");
            //TODO redirect to the main page of the app
            return true;
        } else  if(!pwd.equals(this.password)){
            throw new WrongPasswordException("Wrong password");
        } else {
            System.out.println("Login failed");
            return false;
        }
    }

    /**
     * @return the id of the diver
     */
    public int getDiverId() {
        return this.diverId;
    }

    /**
     * @return the email of the diver
     */
    public String getEmail() {
    	return this.email;
    }

    /**
     * @return the name of the user
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return the password of the user
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @return the user as a string
     */
    public String toString() {
    	return this.email + " " + this.password + " " + this.nom + " " + this.prenom;
    }

}