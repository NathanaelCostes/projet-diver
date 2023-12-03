module com.projetdiver {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.projetdiver to javafx.fxml;
    opens com.projetdiver.login to javafx.fxml;
    exports com.projetdiver;
    exports com.projetdiver.login;
}