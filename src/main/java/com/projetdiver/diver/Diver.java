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
    private Integer id;

    /** Email of the diver */
    private String email;
    /** Password of the user */
    private String password;

    /** LastName of the user */
    private String lastName;

    /** FirstName of the user */
    private String firstName;

    /** true if the user is an admin, false otherwise */
    private boolean isAdmin;

    /**
     * Creates a Diver
     * @param email the email of the diver
     * @param password the password of the diver
     */
    public Diver(int id, String email, String password, String lastName, String firstName) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.isAdmin = false;
    }

    /** Default Constructor */
    public Diver(){}

    /**
     * Login the user and print a message if the login is successful
     * If the login is not successful, raise an exception
     * @param email the email of the user
     * @param pwd the password of the user
     * @throws WrongPasswordException if the password is wrong
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
    public int getId() {
        return id;
    }

    /**
     * @return true if the diver is an admin, false otherwise
     */
    public boolean isAdmin() {
        return this.isAdmin;
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
        return this.lastName;
    }

    /**
     * @return the password of the user
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * @return the user as a string
     */
    public String toString() {
    	return this.email + " " + this.password + " " + this.lastName + " " + this.firstName;
    }


}