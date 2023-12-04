package com.projetdiver.login;

import java.util.*;

/**
 * 
 */
public class Diver {

    private String email;

    private String password;

    private String nom;

    private String prenom;


    /**
     * @param email
     * @param password
     */
    public Diver(String email, String password, String nom, String prenom) {
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Diver(){}

    /**
     * @param email
     * @param pwd
     */
    public boolean login(String email, String pwd) {
        if(email.equals(this.email) && pwd.equals(this.password)){
            System.out.println("Login success");
        } else {
            System.out.println("Login failed");
        }
    }

    public String getEmail() {
    	return this.email;
    }

    public String toString() {
    	return this.email + " " + this.password + " " + this.nom + " " + this.prenom;
    }

}