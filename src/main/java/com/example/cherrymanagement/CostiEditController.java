package com.example.cherrymanagement;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class CostiEditController {

    @FXML private TextField idField;
    @FXML private TextField tipoField;
    @FXML private TextField ammontareField;
    @FXML private DialogPane costiDialog;

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
                    ammontareField.textProperty().set("");
                } else {
                    try {
                        costo.ammontareProperty().set(Double.parseDouble(newValue));
                    } catch (NumberFormatException e) {
                        ammontareField.textProperty().set("");
                    }
                }
            });
        });

        Platform.runLater(() -> {
            Stage stage = (Stage) costiDialog.getScene().getWindow();
            if (stage != null) {
                Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/cherryimage.jpg")));
                stage.getIcons().add(icon);
            }
        });
    }


    public void showAlert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attenzione");
        alert.setHeaderText("Non puoi inserire delle lettere");
        alert.showAndWait();
    }


    public Costo getCosto() {
        return costo;
    }
    public void setCosto(Costo costo) {
        this.costo = costo;
    }

}
