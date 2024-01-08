package com.projetdiver.spot;

import com.projetdiver.dao.PostgreDAOFactory;

import java.util.ArrayList;

public class SpotFacade {

    /**
     * the instance of the SpotFacade
     */
    private static SpotFacade instance;

    /**
     * the instance of the SpotDAO
     */
    private final static SpotDAO spotDAO = PostgreDAOFactory.getInstance().createSpotDAO();

    /**
     * Default constructor
     */
    private SpotFacade() {
    }

    /**
     * Get the instance of the SpotFacade
     *
     * @return the instance of the SpotFacade
     */

    public static SpotFacade getInstance() {
        if (instance == null) {
            instance = new SpotFacade();
        }
        return SpotFacade.instance;
    }

    /**
     * Get a spot by its id
     *
     * @param id the id of the spot
     * @return the spot
     */
    public Spot getSpotById(int id) {
        return spotDAO.getSpotById(id);
    }

    /**
     * Create a spot
     *
     * @param spot the spot to create
     * @return true if the spot was created, false otherwise
     */
    public boolean createSpot(Spot spot) {
        return spotDAO.createSpot(spot);
    }

    /**
     * Delete a spot
     *
     * @param spot the id of the spot
     * @return true if the spot was deleted, false otherwise
     */
    public boolean deleteSpot(Spot spot) {
        return spotDAO.deleteSpot(spot);
    }

    /**
     * Update a spot
     *
     * @param spot the spot to update
     * @return true if the spot was updated, false otherwise
     */

    public boolean updateSpot(Spot spot) {
        return spotDAO.updateSpot(spot);
    }

    /**
     * Get all spots
     *
     * @return all spots
     */
    public ArrayList<Spot> getAllSpots() {
        return spotDAO.getAllSpots();
    }
}