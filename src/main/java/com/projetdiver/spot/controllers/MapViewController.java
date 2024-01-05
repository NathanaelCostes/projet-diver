package com.projetdiver.spot.controllers;

import com.fxrouter.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class MapViewController {
//    private final MapPoint polytechMtp = new MapPoint(43.632761, 3.862550);
//
//    @FXML
//    private MapView mapView;
//
//    @FXML
//    private TextField latitude;
//
//    @FXML
//    private TextField longitude;
//
//    private SpotMapLayer spotMapLayer;
//
//    public void initialize() {
//        mapView.setZoom(3);
//        mapView.flyTo(0.5, polytechMtp, 3);
//        mapView.setOnMapInitialized(() -> {
//            // Create SpotMapLayer only after the MapView is initialized
//            spotMapLayer = new SpotMapLayer(polytechMtp.getLatitude(), polytechMtp.getLongitude());
//            mapView.addLayer(spotMapLayer);
//        });
//    }
//
//    @FXML
//    public void addASpot(ActionEvent actionEvent) {
//        try {
//            double spotLatitude = Double.parseDouble(latitude.getText());
//            double spotLongitude = Double.parseDouble(longitude.getText());
//            spotMapLayer.updateSpot(spotLatitude, spotLongitude);
//        } catch (NumberFormatException e) {
//            // Handle the exception if needed
//            e.printStackTrace();
//        }
//    }
//
//    public static class SpotMapLayer extends MapLayer {
//        private final Node marker;
//
//        public SpotMapLayer(double latitude, double longitude) {
//            marker = new Circle(5, Color.BLUE);
//            getChildren().add(marker);
//            layoutLayer(latitude, longitude);
//        }
//
//        public void updateSpot(double latitude, double longitude) {
//            layoutLayer(latitude, longitude);
//        }
//
//        private void layoutLayer(double latitude, double longitude) {
//            Point2D point = getMapPoint(latitude, longitude);
//            marker.setTranslateX(point.getX());
//            marker.setTranslateY(point.getY());
//        }
//    }

    @FXML
    private void handleMapButton(ActionEvent event) {
        try {
            FXRouter.goTo("map");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleSessionsButton(ActionEvent event) {
        try {
            FXRouter.goTo("session");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleLessonsButton(ActionEvent event) {
        try {
            FXRouter.goTo("lesson");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleContactsButton(ActionEvent event) {
        try {
            FXRouter.goTo("contact");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleClubButton(ActionEvent event) {
        try {
            FXRouter.goTo("club");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleDiverButton(ActionEvent event) {
        try {
            FXRouter.goTo("profile");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleAdminButton(ActionEvent event) {
        try {
            FXRouter.goTo("admin-panel");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
