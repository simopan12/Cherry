package com.example.cherrymanagement;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.lang.reflect.Field;

public class CiliegieEditController {

    @FXML private TextField qualitaField;
    @FXML private TextField kgVendutiField;
    @FXML private TextField descrizioneField;
    @FXML private TextField prezzomedioField;
    @FXML private TextField ricavoField;
    Ciliegia ciliegia;

    @FXML
    public void initialize(){
        qualitaField.textProperty().addListener((observable, oldValue, newValue) -> ciliegia.qualitaProperty().set(newValue));
        kgVendutiField.textProperty().addListener((observable, oldValue, newValue) -> ciliegia.kgVendutiProperty().set(newValue));
        descrizioneField.textProperty().addListener((observable, oldValue, newValue) -> ciliegia.descrizioneProperty().set(newValue));
        prezzomedioField.textProperty().addListener((observable, oldValue, newValue) -> ciliegia.prezzomedioProperty().set(newValue));
        ricavoField.textProperty().addListener((observable, oldValue, newValue) -> ciliegia.ricavoProperty().set(newValue));
    }

    void update() {
        qualitaField.textProperty().set(ciliegia.getQualita());
        kgVendutiField.textProperty().set(ciliegia.getKgVenduti());
        descrizioneField.textProperty().set(ciliegia.getDescrizione());
        prezzomedioField.textProperty().set(ciliegia.getPrezzomedio());
        ricavoField.textProperty().set(ciliegia.getRicavo());
    }

    public Ciliegia getCiliegia() {
        return ciliegia;
    }

    public void setCiliegia(Ciliegia ciliegia) {
        this.ciliegia = ciliegia;
    }


}
