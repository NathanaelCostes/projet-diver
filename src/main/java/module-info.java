module com.projetdiver {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.dotenv;
    requires java.desktop;
    requires com.gluonhq.maps;

    opens com.projetdiver to javafx.fxml;
    opens com.projetdiver.diver.controllers to javafx.fxml;
    opens com.projetdiver.spot.controllers to javafx.fxml;
    //opens com.projetdiver.diver.exceptions to javafx.fxml;

    opens com.projetdiver.admin to javafx.fxml;

    exports com.projetdiver;
    exports com.projetdiver.diver.controllers;
    exports com.projetdiver.diver.exceptions;
    exports com.projetdiver.club.controllers;
    exports com.projetdiver.contact.controllers;
    exports com.projetdiver.lesson.controllers;
    exports com.projetdiver.spot.controllers;
    exports com.projetdiver.session.controllers;
    exports com.projetdiver.map.controllers;
    exports com.projetdiver.review.controllers;

    exports com.projetdiver.admin;

    exports com.projetdiver.dao;
    opens com.projetdiver.dao to javafx.fxml;

    exports com.projetdiver.diver;
    opens com.projetdiver.diver to javafx.fxml;

    /** Session */
    exports com.projetdiver.session;
    exports com.projetdiver.session.exceptions;
    opens com.projetdiver.session to javafx.fxml;
    opens com.projetdiver.session.exceptions to javafx.fxml;
    opens com.projetdiver.session.controllers to javafx.fxml;

    exports com.projetdiver.lesson;
    opens com.projetdiver.lesson to javafx.fxml;
    opens com.projetdiver.lesson.controllers to javafx.fxml;

    exports com.projetdiver.review;
    opens com.projetdiver.review to javafx.fxml;
    opens com.projetdiver.review.controllers to javafx.fxml;

    exports com.fxrouter;
    opens com.fxrouter to javafx.fxml;
    exports com.projetdiver.spot;
    opens com.projetdiver.spot to javafx.fxml;
    opens com.projetdiver.map.controllers to javafx.fxml;


    /** Contact */
    exports com.projetdiver.contact;
    opens com.projetdiver.contact to javafx.fxml;
    opens com.projetdiver.contact.controllers to javafx.fxml;

    exports com.projetdiver.certification;
    opens com.projetdiver.certification to javafx.fxml;
    opens com.projetdiver.certification.controllers to javafx.fxml;
    exports com.projetdiver.certification.controllers;

}