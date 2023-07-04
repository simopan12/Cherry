package com.example.cherrymanagement;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class CiliegieEditController {

    @FXML private TextField qualitaField;
    @FXML private TextField kgVendutiField;
    @FXML private TextField descrizioneField;
    @FXML private TextField ricavoField;
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
                    ciliegia.kgVendutiProperty().set(Double.parseDouble(newValue));
                }
            });
        });

        descrizioneField.textProperty().addListener((observable, oldValue, newValue) -> ciliegia.descrizioneProperty().set(newValue));

        ricavoField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean containsLetters = newValue.matches(".*[a-zA-Z].*");
            Platform.runLater(() -> {
                if (containsLetters) {
                    showAlert();
                    ricavoField.textProperty().set("0");
                } else {
                    ciliegia.ricavoProperty().set(Double.parseDouble(newValue));
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
        qualitaField.textProperty().set(ciliegia.getQualita());
        kgVendutiField.textProperty().set(String.valueOf(ciliegia.getKgVenduti()));
        descrizioneField.textProperty().set(ciliegia.getDescrizione());
        ricavoField.textProperty().set(String.valueOf(ciliegia.getRicavo()));
    }


    public Ciliegia getCiliegia() {
        return ciliegia;
    }
    public void setCiliegia(Ciliegia ciliegia) {
        this.ciliegia = ciliegia;
    }
}
