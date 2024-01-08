package com.projetdiver.map.controllers;

import com.fxrouter.FXRouter;
import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.Map;

public class MapViewController {

    //'Blue Hole', 17.3199, -87.5354
    private final MapPoint BlueHole = new MapPoint(17.3199, -87.5354);

    //'Great Barrier Reef', -16.286389, 146.686111
    private final MapPoint GreatBarrierReef = new MapPoint(-16.286389, 146.686111);

    //Silfra Fissure', 64.926, -21.119
    private final MapPoint SilfraFissure = new MapPoint(64.926, -21.119);

    @FXML
    private MapView mapView;

    public void initialize() {
        mapView.setPrefSize(500, 400);
        mapView.addLayer(new CustomMapLayer());
        mapView.setCenter(BlueHole);
        mapView.setZoom(1.7);
    }


//    private MapView createMapView() {
//        MapView mapView = new MapView();
//        mapView.setPrefSize(500, 400);
//        mapView.addLayer(new CustomMapLayer());
//        mapView.setZoom(3);
//        return mapView;
//    }

    private class CustomMapLayer extends MapLayer {
        private final Node marker;

        private final Node marker2;

        private final Node marker3;

        public CustomMapLayer() {
            marker = new Circle(5, Color.RED);
            marker2 = new Circle(5, Color.BLUE);
            marker3 = new Circle(5, Color.GREEN);
            getChildren().add(marker);
            getChildren().add(marker2);
            getChildren().add(marker3);
        }

        @Override
        protected void layoutLayer() {
            Point2D point = getMapPoint(BlueHole.getLatitude(), BlueHole.getLongitude());
            Point2D point2 = getMapPoint(GreatBarrierReef.getLatitude(), GreatBarrierReef.getLongitude());
            Point2D point3 = getMapPoint(SilfraFissure.getLatitude(), SilfraFissure.getLongitude());
            marker.setTranslateX(point.getX());
            marker.setTranslateY(point.getY());
            marker2.setTranslateX(point2.getX());
            marker2.setTranslateY(point2.getY());
            marker3.setTranslateX(point3.getX());
            marker3.setTranslateY(point3.getY());
        }
    }

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

    @FXML
    private void handleSpotButton(ActionEvent event) {
        try {
            FXRouter.goTo("spot");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
