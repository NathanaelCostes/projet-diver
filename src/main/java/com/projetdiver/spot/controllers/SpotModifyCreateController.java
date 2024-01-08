package com.projetdiver.spot.controllers;

import com.projetdiver.ControllerHelper;
import com.projetdiver.spot.Spot;
import com.projetdiver.spot.SpotFacade;
import com.projetdiver.spot.exceptions.NotConnectedException;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.InputStream;
import java.sql.Date;

public class SpotModifyCreateController implements ControllerHelper {

    /**
     * The instance of the SpotFacade
     */
    private final SpotFacade facade;

    /**
     * VBox to hold the information about the spot
     */
    @FXML
    private VBox spotModifyListVBox;

    /**
     * Label to hold the title of the window
     */
    @FXML
    private Label spotModifyLabel;

    /**
     * Default constructor
     */
    public SpotModifyCreateController() {
        this.facade = SpotFacade.getInstance();
    }

    /**
     * Initialize the spot list
     */
    @FXML
    public void initialize() {
    }

    /**
     * Set the modify view of a spot
     * @param spot the spot to modify
     */
    public void setSpotModify(Spot spot) {
        spotModifyListVBox.getChildren().clear();

        spotModifyListVBox.setSpacing(5);

        String[] errorMsg = {""};
        Label spotErrorLabel = createLabel(errorMsg[0]);
        spotErrorLabel.setStyle(spotErrorLabel.getStyle() + "-fx-text-fill: red;");
        spotModifyListVBox.getChildren().add(spotErrorLabel);
        
        spotModifyListVBox.getChildren().add(createLabel("Name: "));
        spotModifyListVBox.getChildren().add(createTextField(spot.getName()));

        spotModifyListVBox.getChildren().add(createLabel("Latitude: "));
        spotModifyListVBox.getChildren().add(createTextField(spot.getLatitude().toString()));

        spotModifyListVBox.getChildren().add(createLabel("Longitude: "));
        spotModifyListVBox.getChildren().add(createTextField(spot.getLongitude().toString()));

        spotModifyListVBox.getChildren().add(createLabel("Max depth: "));
        spotModifyListVBox.getChildren().add(createTextField(spot.getMaxDepth().toString()));

        spotModifyListVBox.getChildren().add(createLabel("Type: "));
        spotModifyListVBox.getChildren().add(createTextField(spot.getType().toString()));

        spotModifyListVBox.getChildren().add(createLabel("Point of interest: "));
        spotModifyListVBox.getChildren().add(createTextField(spot.getLongitude().toString()));

        spotModifyListVBox.getChildren().add(createLabel("Level: "));
        spotModifyListVBox.getChildren().add(createTextField(spot.getLevel().toString()));
        
        HBox spotHBox = new HBox();
        spotHBox.setSpacing(25);
        spotHBox.setAlignment(javafx.geometry.Pos.CENTER);
        spotHBox.setStyle("-fx-padding: 25px 0px 0px 0px;");

        Button spotSaveButton = createButton("Save", "green");
        spotSaveButton.setOnAction(event -> { try {
            spot.setName(((TextField) spotModifyListVBox.getChildren().get(2)).getText());
            
            spot.setLatitude(Float.valueOf(((TextField) spotModifyListVBox.getChildren().get(4)).getText()));

            spot.setLongitude(Float.valueOf(((TextField) spotModifyListVBox.getChildren().get(6)).getText()));

            spot.setMaxDepth(Integer.valueOf(((TextField) spotModifyListVBox.getChildren().get(8)).getText()));

            spot.setType(((TextField) spotModifyListVBox.getChildren().get(10)).getText());

            spot.setPoi(((TextField) spotModifyListVBox.getChildren().get(12)).getText());

            spot.setLevel(((TextField) spotModifyListVBox.getChildren().get(14)).getText());

            
            facade.updateSpot(spot);
            spotSaveButton.getScene().getWindow().hide();
        } catch (NumberFormatException e) {
            errorMsg[0] = "Error: Duration, temperature and depth must be numbers";
            spotErrorLabel.setText(errorMsg[0]);
        }
        });
        spotHBox.getChildren().add(spotSaveButton);
        
        Button spotDeleteButton = createButton("Delete", "red");
        spotDeleteButton.setOnAction(event -> {
            facade.deleteSpot(spot);
            spotDeleteButton.getScene().getWindow().hide();
        });
        spotHBox.getChildren().add(spotDeleteButton);
        spotModifyListVBox.getChildren().add(spotHBox);

    }

    /**
     * Set the create view of a spot
     */
    public void setSpotCreate(){

        spotModifyLabel.setText("Create a spot");
        spotModifyListVBox.getChildren().clear();

        spotModifyListVBox.setSpacing(5);

        String[] errorMsg = {""};
        Label spotErrorLabel = createLabel(errorMsg[0]);
        spotErrorLabel.setStyle(spotErrorLabel.getStyle() + "-fx-text-fill: red;");
        spotModifyListVBox.getChildren().add(spotErrorLabel);
        
        spotModifyListVBox.getChildren().add(createLabel("Name: "));
        spotModifyListVBox.getChildren().add(createTextField(""));

        spotModifyListVBox.getChildren().add(createLabel("Latitude: "));
        spotModifyListVBox.getChildren().add(createTextField(""));

        spotModifyListVBox.getChildren().add(createLabel("Longitude: "));
        spotModifyListVBox.getChildren().add(createTextField(""));

        spotModifyListVBox.getChildren().add(createLabel("Max depth: "));
        spotModifyListVBox.getChildren().add(createTextField(""));

        spotModifyListVBox.getChildren().add(createLabel("Type: "));
        spotModifyListVBox.getChildren().add(createTextField(""));

        spotModifyListVBox.getChildren().add(createLabel("Point of interest: "));
        spotModifyListVBox.getChildren().add(createTextField(""));

        spotModifyListVBox.getChildren().add(createLabel("Level: "));
        spotModifyListVBox.getChildren().add(createTextField(""));
        
        HBox spotHBox = new HBox();
        spotHBox.setSpacing(25);
        spotHBox.setAlignment(javafx.geometry.Pos.CENTER);
        spotHBox.setStyle("-fx-padding: 25px 0px 0px 0px;");

        Button spotCreateButton = createButton("Create", "green");
        spotCreateButton.setOnAction(event -> { try {
            Spot spot = new Spot();
            spot.setName(((TextField) spotModifyListVBox.getChildren().get(2)).getText());

            spot.setLatitude(Float.valueOf(((TextField) spotModifyListVBox.getChildren().get(4)).getText()));

            spot.setLongitude(Float.valueOf(((TextField) spotModifyListVBox.getChildren().get(6)).getText()));

            spot.setMaxDepth(Integer.valueOf(((TextField) spotModifyListVBox.getChildren().get(8)).getText()));

            spot.setType(((TextField) spotModifyListVBox.getChildren().get(10)).getText());

            spot.setPoi(((TextField) spotModifyListVBox.getChildren().get(12)).getText());

            spot.setLevel(((TextField) spotModifyListVBox.getChildren().get(14)).getText());

            facade.createSpot(spot);
            spotCreateButton.getScene().getWindow().hide();
        } catch (NumberFormatException e) {
            errorMsg[0] = "Error: Duration, temperature and depth must be numbers";
            spotErrorLabel.setText(errorMsg[0]);
        }
        });
        spotHBox.getChildren().add(spotCreateButton);

        spotModifyListVBox.getChildren().add(spotHBox);

    }

    /**
     * Show the spot invitation view
     * @param event the event
     * @param spot the spot
     */
    @FXML
    public void openInvitationSpot(Event event, Spot spot) {
        try {
            InputStream fxmlStream = getClass().getResourceAsStream("/com/projetdiver/views/spot/spot-invitation-view.fxml");
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fxmlStream);

            Stage modalStage = new Stage();
            modalStage.setTitle("Spot details");

            Scene scene = new Scene(root, 1000, 400);
            scene.setUserData(this);

            modalStage.setScene(scene);
            modalStage.initOwner(((Button) event.getSource()).getScene().getWindow());
            modalStage.initModality(Modality.WINDOW_MODAL);

            modalStage.showAndWait();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
