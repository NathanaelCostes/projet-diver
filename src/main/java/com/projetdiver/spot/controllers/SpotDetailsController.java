package com.projetdiver.spot.controllers;

import com.projetdiver.spot.Spot;
import com.projetdiver.spot.SpotFacade;
import com.projetdiver.spot.controllers.ControllerHelper;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class SpotDetailsController implements ControllerHelper {
    /**
     * The instance of the SpotFacade
     */
    private final SpotFacade facade;

    /**
     * VBox to hold the information about the spot
     */
    @FXML
    private VBox spotDetailsListVBox;

    /**
     * Default constructor
     */
    public SpotDetailsController() {
        this.facade = SpotFacade.getInstance();
    }

    /**
     * Initialize the spot list
     */
    @FXML
    public void initialize() {
    }

    public void setSpotDetails(Spot spot) {
        spotDetailsListVBox.getChildren().clear();

        spotDetailsListVBox.setSpacing(5);
        spotDetailsListVBox.setAlignment(javafx.geometry.Pos.CENTER);
        spotDetailsListVBox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1px; -fx-border-radius: 5px;");

        try {
            spotDetailsListVBox.getChildren().add(createLabel(spot.getName()));
            if(spot.getLatitude() != null) {
                spotDetailsListVBox.getChildren().add(createLabel(spot.getLatitude().toString()));
            } else {
                spotDetailsListVBox.getChildren().add(createLabel("No date"));
            }
            if (spot.getLongitude() != null) {
                spotDetailsListVBox.getChildren().add(createLabel(spot.getLongitude().toString()));
            } else {
                spotDetailsListVBox.getChildren().add(createLabel("No comment"));
            }
            if (spot.getMaxDepth() != null) {
                spotDetailsListVBox.getChildren().add(createLabel(spot.getMaxDepth().toString()));
            } else {
                spotDetailsListVBox.getChildren().add(createLabel("No duration"));
            }

            if (spot.getType() != null) {
                spotDetailsListVBox.getChildren().add(createLabel(spot.getType().toString()));
            } else {
                spotDetailsListVBox.getChildren().add(createLabel("No temperature"));
            }

            if (spot.getPoi() != null) {
                spotDetailsListVBox.getChildren().add(createLabel(spot.getPoi().toString()));
            } else {
                spotDetailsListVBox.getChildren().add(createLabel("No depth"));
            }

            if (spot.getLevel() != null) {
                spotDetailsListVBox.getChildren().add(createLabel(spot.getLevel().toString()));
            } else {
                spotDetailsListVBox.getChildren().add(createLabel("No depth"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
