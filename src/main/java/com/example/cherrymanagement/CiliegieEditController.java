package com.example.cherrymanagement;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class CiliegieEditController {

    @FXML private TextField qualitaField;
    @FXML private TextField kgVendutiField;
    @FXML private TextField descrizioneField;
    @FXML private TextField ricavoField;
    @FXML private DialogPane ciliegieDialog;
    Ciliegia ciliegia;


    @FXML
    public void initialize(){
        qualitaField.textProperty().addListener((observable, oldValue, newValue) -> ciliegia.qualitaProperty().set(newValue));

        kgVendutiField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            boolean containsLetters = newValue.matches(".*[a-zA-Z].*");
            Platform.runLater(() -> {
                if (containsLetters) {
                    showAlert();
                    kgVendutiField.textProperty().set("0");
                } else {
                    try {
                        ciliegia.kgVendutiProperty().set(Double.parseDouble(newValue));
                    } catch (NumberFormatException e) {
                        kgVendutiField.textProperty().set("0");
                    }
                }
            });
        });

        descrizioneField.textProperty().addListener((observable, oldValue, newValue) -> ciliegia.descrizioneProperty().set(newValue));

        ricavoField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean containsLetters = newValue.matches(".*[a-zA-Z].*");
            Platform.runLater(() -> {
                if (containsLetters) {
                    showAlert();
                    ricavoField.textProperty().set("");
                } else {
                    try {
                        ciliegia.ricavoProperty().set(Double.parseDouble(newValue));
                    } catch (NumberFormatException e) {
                        ricavoField.textProperty().set("");
                    }
                }
            });
        });

        Platform.runLater(() -> {
            Stage stage = (Stage) ciliegieDialog.getScene().getWindow();
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


    public Ciliegia getCiliegia() {
        return ciliegia;
    }
    public void setCiliegia(Ciliegia ciliegia) {
        this.ciliegia = ciliegia;
    }
}
