module com.projetdiver {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.dotenv;

    opens com.projetdiver to javafx.fxml;
    opens com.projetdiver.login to javafx.fxml;
    exports com.projetdiver;
    exports com.projetdiver.login;
    exports com.projetdiver.login.exceptions;
    opens com.projetdiver.login.exceptions to javafx.fxml;
    exports com.projetdiver.dao;
    opens com.projetdiver.dao to javafx.fxml;
    exports com.projetdiver.diver;
    opens com.projetdiver.diver to javafx.fxml;

    exports com.projetdiver.session;
    opens com.projetdiver.session to javafx.fxml;
    exports com.projetdiver.session.exceptions;
    opens com.projetdiver.session.exceptions to javafx.fxml;
    exports com.projetdiver.session.controllers;
    opens com.projetdiver.session.controllers to javafx.fxml;
}