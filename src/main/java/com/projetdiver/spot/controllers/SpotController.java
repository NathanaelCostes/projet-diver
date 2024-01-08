package com.projetdiver.spot.controllers;

import com.fxrouter.FXRouter;
import com.projetdiver.diver.DiverFacade;
import com.projetdiver.spot.Spot;
import com.projetdiver.spot.SpotFacade;
import com.projetdiver.ControllerHelper;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class SpotController implements Initializable, ControllerHelper {

    /**
     * The instance of the SpotFacade
     */
    private final SpotFacade facade;

    /**
     * VBox to hold the diver labels
     */
    @FXML
    private VBox spotListVBox;

    /**
     * HBoc to hold menu buttons
     */
    @FXML
    private HBox spotMenuHBox;

    /**
     * Default constructor
     */
    public SpotController() {
        this.facade = SpotFacade.getInstance();
    }


    //Javadoc for the SpotController

    /**
     * Set the menu buttons
     */
    @FXML
    public void setSpotMenuHBox(){
        spotMenuHBox.getChildren().clear();

        Button spotCreateButton = createButton("Create", "#3ebbbe");
        spotCreateButton.setOnAction(event -> { 
            openCreateSpot(event); 
            setSpotListView();
        });
        spotMenuHBox.getChildren().add(spotCreateButton);
    }

    /**
     * Open the invitation spot view
     * @param event the event
     */
    @FXML
    private void openInvitationSpot(ActionEvent event) {
        try {
            InputStream fxmlStream = getClass().getResourceAsStream("/com/projetdiver/views/spot/spot-invitation-view.fxml");
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fxmlStream);

            Stage modalStage = new Stage();
            modalStage.setTitle("Spot invitation");

            Scene scene = new Scene(root, 1500, 400);
            scene.setUserData(this);

            modalStage.setScene(scene);
            modalStage.initOwner(((Button) event.getSource()).getScene().getWindow());
            modalStage.initModality(Modality.WINDOW_MODAL);

            modalStage.showAndWait();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set the spot list
     */
    @FXML
    public void setSpotListView(){
        spotListVBox.getChildren().clear();
        facade.getAllSpots().forEach(spot -> {
            HBox spotHBox = new HBox();
            spotHBox.setSpacing(5);
            spotHBox.setAlignment(javafx.geometry.Pos.CENTER);
            spotHBox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1px; -fx-border-radius: 5px;");

            spotHBox.getChildren().add(createLabel(spot.getName()));

            spotHBox.getChildren().add(separator());

//            if (spot.getLatitude() != null) {
//                spotHBox.getChildren().add(createLabel(spot.getLatitude().toString()));
//
//                spotHBox.getChildren().add(separator());
//            }
//
//            if (spot.getLongitude() != null) {
//                spotHBox.getChildren().add(createLabel(spot.getLongitude().toString()));
//
//                spotHBox.getChildren().add(separator());
//            }
//
//            if (spot.getMaxDepth() != null) {
//                spotHBox.getChildren().add(createLabel(spot.getMaxDepth().toString()));
//
//                spotHBox.getChildren().add(separator());
//            }

            if (spot.getType() != null) {
                spotHBox.getChildren().add(createLabel(spot.getType().toString()));

                spotHBox.getChildren().add(separator());
            }

            if (spot.getPoi() != null) {
                spotHBox.getChildren().add(createLabel(spot.getPoi().toString()));

                spotHBox.getChildren().add(separator());
            }

            if (spot.getLevel() != null) {
                spotHBox.getChildren().add(createLabel(spot.getLevel().toString()));

                spotHBox.getChildren().add(separator());
            }

            Button spotModifyButton = createButton("Modify", "#BAB8B7");
            spotModifyButton.setOnAction(event -> { openModifySpot(event, spot); });

            //if Diver isAdmin = true then show modify button
            if(DiverFacade.getInstance().getCurrentDiver().isAdmin()){
                spotHBox.getChildren().add(spotModifyButton);
            }
            Button spotInfoButton = createButton("Info", "#2d95a1");
            spotInfoButton.setOnAction(event -> { openDetailsSpot(event, spot); });
            spotHBox.getChildren().add(spotInfoButton);

            spotListVBox.setSpacing(10);
            spotListVBox.getChildren().add(spotHBox);
        });
    }

    /**
     * Open the spot details view
     * @param event the event
     * @param spot the spot
     */
    @FXML
    public void openDetailsSpot(Event event, Spot spot) {
        try {
            InputStream fxmlStream = getClass().getResourceAsStream("/com/projetdiver/views/spot/spot-details-view.fxml");
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fxmlStream);

            SpotDetailsController spotDetailsController = loader.getController();
            spotDetailsController.setSpotDetails(spot);

            Stage modalStage = new Stage();
            modalStage.setTitle("Spot details");

            Scene scene = new Scene(root, 600, 400);
            scene.setUserData(this);

            modalStage.setScene(scene);
            modalStage.initOwner(((Button) event.getSource()).getScene().getWindow());
            modalStage.initModality(Modality.WINDOW_MODAL);

            modalStage.showAndWait();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Open the spot modify view
     * @param event the event
     * @param spot the spot
     */
    @FXML
    public void openModifySpot(Event event, Spot spot) {
        try {
            InputStream fxmlStream = getClass().getResourceAsStream("/com/projetdiver/views/spot/spot-modify-create-view.fxml");
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fxmlStream);

            SpotModifyCreateController spotModifyCreateController = loader.getController();
            spotModifyCreateController.setSpotModify(spot);

            Stage modalStage = new Stage();
            modalStage.setTitle("Spot modify");

            Scene scene = new Scene(root, 600, 600);
            scene.setUserData(this);

            modalStage.setScene(scene);
            modalStage.initOwner(((Button) event.getSource()).getScene().getWindow());
            modalStage.initModality(Modality.WINDOW_MODAL);

            modalStage.showAndWait();

            initialize(null, null);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Open the spot create view
     * @param event the event
     */
    @FXML
    public void openCreateSpot(Event event) {
        try {
            InputStream fxmlStream = getClass().getResourceAsStream("/com/projetdiver/views/spot/spot-modify-create-view.fxml");
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fxmlStream);

            SpotModifyCreateController spotCreateController = loader.getController();
            spotCreateController.setSpotCreate();

            Stage modalStage = new Stage();
            modalStage.setTitle("Spot create");

            Scene scene = new Scene(root, 600, 600);
            scene.setUserData(this);

            modalStage.setScene(scene);
            modalStage.initOwner(((Button) event.getSource()).getScene().getWindow());
            modalStage.initModality(Modality.WINDOW_MODAL);

            modalStage.showAndWait();

            initialize(null, null);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Button to go back to the main page
     */
    @FXML
    private void backToMainPage(){
        try {
            FXRouter.goTo("main");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Initialize the spot list
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSpotListView();
        setSpotMenuHBox();
    }
}
