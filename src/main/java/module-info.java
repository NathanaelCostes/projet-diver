module com.example.projetdiver {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.projetdiver to javafx.fxml;
    exports com.example.projetdiver;
}