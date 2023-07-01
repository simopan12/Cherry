module com.example.cherrymanagement {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.cherrymanagement to javafx.fxml;
    exports com.example.cherrymanagement;
}