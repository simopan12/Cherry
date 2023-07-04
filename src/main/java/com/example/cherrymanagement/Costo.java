package com.example.cherrymanagement;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Costo {

    private final StringProperty id;
    private final StringProperty tipo;
    private final DoubleProperty ammontare;

    public Costo(String id, String tipo, Double ammontare) {
        this.id = new SimpleStringProperty(id);
        this.tipo = new SimpleStringProperty(tipo);
        this.ammontare = new SimpleDoubleProperty(ammontare);
    }

    public Costo(Costo other){
        this.id = new SimpleStringProperty(other.getId());
        this.tipo = new SimpleStringProperty(other.getTipo());
        this.ammontare = new SimpleDoubleProperty(other.getAmmontare());
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getTipo() {
        return tipo.get();
    }

    public StringProperty tipoProperty() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo.set(tipo);
    }

    public Double getAmmontare() {
        return ammontare.get();
    }

    public DoubleProperty ammontareProperty() {
        return ammontare;
    }

    public void setAmmontare(Double ammontare) {
        this.ammontare.set(ammontare);
    }


}
