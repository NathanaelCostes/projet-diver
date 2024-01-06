package com.projetdiver.admin;

import com.projetdiver.dao.PostgreDAOFactory;
import com.projetdiver.diver.Diver;

import java.util.List;

public class AdminFacade {

    /**
    * Default constructor
     */
    private AdminFacade() {}


    /**
     * The instance of the AdminFacade
     */
    private static AdminFacade instance;

    /**
     * The list of all the divers
     */
    private List<Diver> divers;

    /**
     * Get the list of all the divers
     * @return the list of all the divers
     */
    public List<Diver> getDivers() {
        this.divers = PostgreDAOFactory.getInstance().createDiverDAO().getAllDivers();
        return this.divers;
    }

    /**
     * Delete a diver by his email
     * @param email the email of the diver to delete
     */
    public void deleteDiverByEmail(String email) {
        PostgreDAOFactory.getInstance().createDiverDAO().deleteDiverByEmail(email);
        // Update the list of divers
        this.divers = PostgreDAOFactory.getInstance().createDiverDAO().getAllDivers();
    }

    /**
     * Delete a diver by his id
     * @param id the id of the diver to delete
     */
    public void deleteDiverById(int id) {
        PostgreDAOFactory.getInstance().createDiverDAO().deleteDiverById(id);
        // Update the list of divers
        this.divers = PostgreDAOFactory.getInstance().createDiverDAO().getAllDivers();
    }

    /**
     * Get the instance of the AdminFacade
     * @return the instance of the AdminFacade
     */
    public static AdminFacade getInstance() {
        if (AdminFacade.instance == null) {
            AdminFacade.instance = new AdminFacade();
        }
        return AdminFacade.instance;
    }
}
