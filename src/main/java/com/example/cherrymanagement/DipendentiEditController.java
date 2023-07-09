package com.example.cherrymanagement;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class DipendentiEditController {
    @FXML
    private TextField cfField;
    @FXML private TextField nomeField;
    @FXML private TextField cognomeField;
    @FXML private TextField mansioneField;
    @FXML private  TextField pagaField;
    @FXML private Spinner<Double> oreSpinner;
    @FXML private DialogPane dipendentiDialog;


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
                    pagaField.textProperty().set("");
                } else {
                    try {
                        dipendente.pagaProperty().set(Double.parseDouble(newValue));
                    } catch (NumberFormatException e) {
                        pagaField.textProperty().set("");
                    }
                }
            });
        });
        oreSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10000, 0, 1));
        oreSpinner.valueProperty().addListener((observable, oldValue, newValue) -> dipendente.setOre(newValue));

        Platform.runLater(() -> {
            Stage stage = (Stage) dipendentiDialog.getScene().getWindow();
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


    public Dipendente getDipendente() {
        return dipendente;
    }
    public void setDipendente(Dipendente dipendente) {
        this.dipendente =dipendente;
    }
}
