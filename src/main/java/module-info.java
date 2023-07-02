module com.example.cherrymanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.cherrymanagement to javafx.fxml;
    exports com.example.cherrymanagement;
}