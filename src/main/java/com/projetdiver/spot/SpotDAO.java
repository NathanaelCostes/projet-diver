package com.projetdiver.spot;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a diving spot.
 * @author d'Amerval
 */
public class SpotDAO {

    private static SpotDAO instance;

    /**
     * Default constructor
     */
    public SpotDAO() {
    }

    /**
     * Get the instance of the SpotDAO or crear it if it doesn't exist
     *
     * @return the instance of the SpotDAO
     */
    public static SpotDAO getInstance() {
        if (instance == null) {
            instance = new SpotDAOPostgre();
        }
        return instance;
    }

    /**
     * Get a spot by its id
     *
     * @param id the id of the spot
     * @return the spot
     */
    public Spot getSpotById(int id) {
        return null;
    }

    /**
     * Create a spot
     *
     * @param spot the spot to create
     * @return true if the spot was created, false otherwise
     */
    public boolean createSpot(Spot spot) {
        return false;
    }

    /**
     * Delete a spot
     *
     * @param spotId the id of the spot
     * @return true if the spot was deleted, false otherwise
     */
    public boolean deleteSpot(int spotId) {
        return false;
    }

    /**
     * Update a spot
     *
     * @param spot the spot to update
     * @return true if the spot was updated, false otherwise
     */
    public boolean updateSpot(Spot spot) {
        return false;
    }

    /**
     * Get all the spots
     *
     * @return the list of all the spots
     */
    public ArrayList<Spot> getAllSpots() {
        return null;
    }

}

