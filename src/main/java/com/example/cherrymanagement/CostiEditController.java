package com.example.cherrymanagement;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class CostiEditController {

    @FXML private TextField idField;
    @FXML private TextField tipoField;
    @FXML private TextField ammontareField;

    Costo costo;


    @FXML
    public void initialize(){
        idField.textProperty().addListener((observable, oldValue, newValue) -> costo.idProperty().set(newValue));
        tipoField.textProperty().addListener((observable, oldValue, newValue) -> costo.tipoProperty().set(newValue));
        ammontareField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            boolean containsLetters = newValue.matches(".*[a-zA-Z].*");
            Platform.runLater(() -> {
                if (containsLetters) {
                    showAlert();
                    ammontareField.textProperty().set("0");
                } else {
                    costo.ammontareProperty().set(Double.parseDouble(newValue));
                }
            });
        });
    }


    public void showAlert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attenzione");
        alert.setHeaderText("Non puoi inserire delle lettere");
        alert.showAndWait();
    }


    void update() {
        idField.textProperty().set(costo.getId());
        tipoField.textProperty().set(costo.getTipo());
        ammontareField.textProperty().set(String.valueOf(costo.getAmmontare()));
    }


    public Costo getCosto() {
        return costo;
    }
    public void setCosto(Costo costo) {
        this.costo = costo;
    }

}
