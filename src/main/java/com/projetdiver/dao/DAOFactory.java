package com.projetdiver.dao;

import com.projetdiver.diver.DiverDAO;

/**
 * Interface that defines the methods for the databases factories
 */
public interface DAOFactory {

    /**
     * Creates a DiverDAO
     */
    DiverDAO createDiverDAO();
}