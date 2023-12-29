module com.projetdiver {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.dotenv;

    opens com.projetdiver to javafx.fxml;
    opens com.projetdiver.login to javafx.fxml;
    opens com.projetdiver.signup to javafx.fxml;
    opens com.projetdiver.profile to javafx.fxml;
    opens com.projetdiver.admin to javafx.fxml;

    exports com.projetdiver;
    exports com.projetdiver.login;
    exports com.projetdiver.login.exceptions;
    exports com.projetdiver.signup;
    exports com.projetdiver.signup.exceptions;
    exports com.projetdiver.profile;
    exports com.projetdiver.admin;

    opens com.projetdiver.login.exceptions to javafx.fxml;

    exports com.projetdiver.dao;
    opens com.projetdiver.dao to javafx.fxml;

    exports com.projetdiver.diver;
    opens com.projetdiver.diver to javafx.fxml;
    exports com.projetdiver.lesson;
    opens com.projetdiver.lesson to javafx.fxml;
    exports com.projetdiver.lesson.controllers;
    opens com.projetdiver.lesson.controllers to javafx.fxml;
}