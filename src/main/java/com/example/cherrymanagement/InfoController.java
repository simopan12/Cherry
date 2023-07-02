package com.example.cherrymanagement;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class InfoController {
    private Stage stage;


    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    private void goBack() {
        MenuController.navigateToMenuPage();
    }
}
