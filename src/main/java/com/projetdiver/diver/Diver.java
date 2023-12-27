package com.projetdiver.diver;

import com.projetdiver.login.exceptions.WrongPasswordException;

/**
 * Diver is a class that represents a diver
 * <ul>
 *     <li> You can login a diver </li>
 * </ul>
 */
public class Diver {

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
     * @param email
     * @param password
     */
    public Diver(String prenom, String nom, String email, String password) {
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
     * @return the email of the diver
     */
    public String getEmail() {
    	return this.email;
    }

    /**
     * @return the name of the user
     */
    public String getLastName() {
        return nom;
    }

    /**
     * @return the password of the user
     */
    public String getFirstName() {
        return prenom;
    }

    /**
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Will set the first name of the user
     * @param prenom the first name of the user
     */
    public void setFirstName(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Will set the last name of the user
     * @param nom the last name of the user
     */
    public void setLastName(String nom) {
        this.nom = nom;
    }

    /**
     * Will set the email of the user
     * @param email the email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Will set the password of the user
     * @param password the password of the user
     */
    public void setPassword(String password) {
    	this.password = password;
    }

    /**
     * @return the user as a string
     */
    public String toString(){
    	return "First name: " + this.prenom + "\n" +
                "Last name: " + this.nom + "\n" +
                "Email: " + this.email + "\n" +
                "Password: " + this.password + "\n";
    }

}