package com.example.cherrymanagement;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

public class DipendentiEditController {
    @FXML
    private TextField cfField;
    @FXML private TextField nomeField;
    @FXML private TextField cognomeField;
    @FXML private TextField mansioneField;
    @FXML private  TextField pagaField;
    @FXML private Spinner<Double> oreSpinner;


    Dipendente dipendente;


    @FXML
    public void initialize(){
        cfField.textProperty().addListener((observable, oldValue, newValue) -> dipendente.cfProperty().set(newValue));
        nomeField.textProperty().addListener((observable, oldValue, newValue) -> dipendente.nomeProperty().set(newValue));
        cognomeField.textProperty().addListener((observable, oldValue, newValue) -> dipendente.cognomeProperty().set(newValue));
        mansioneField.textProperty().addListener((observable, oldValue, newValue) -> dipendente.mansioneProperty().set(newValue));
        pagaField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            boolean containsLetters = newValue.matches(".*[a-zA-Z].*");
            Platform.runLater(() -> {
                if (containsLetters) {
                    showAlert();
                    pagaField.textProperty().set("0");
                } else {
                    dipendente.pagaProperty().set(Double.parseDouble(newValue));
                }
            });
        });
        oreSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10000, 0, 1));
        oreSpinner.valueProperty().addListener((observable, oldValue, newValue) -> dipendente.setOre(newValue.doubleValue()));

    }

    public void showAlert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attenzione");
        alert.setHeaderText("Non puoi inserire delle lettere");
        alert.showAndWait();
    }


    void update() {
        cfField.textProperty().set(dipendente.getCf());
        nomeField.textProperty().set(dipendente.getNome());
        cognomeField.textProperty().set(dipendente.getCognome());
        mansioneField.textProperty().set(dipendente.getMansione());
        pagaField.textProperty().set(String.valueOf(dipendente.getPaga()));
        oreSpinner.setValueFactory( new SpinnerValueFactory.DoubleSpinnerValueFactory(0,10000,dipendente.getOre(),1));
    }


    public Dipendente getDipendente() {
        return dipendente;
    }
    public void setDipendente(Dipendente dipendente) {
        this.dipendente =dipendente;
    }
}
