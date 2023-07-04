package com.example.cherrymanagement;

import javafx.beans.property.*;

public class Ciliegia {
    private final StringProperty qualita;
    private final DoubleProperty kgVenduti;
    private final StringProperty descrizione;
    private final DoubleProperty ricavo;


    public Ciliegia(String qualita, Double kgVenduti, String descrizione, Double ricavo) {
        this.qualita = new SimpleStringProperty(qualita);
        this.kgVenduti = new SimpleDoubleProperty(kgVenduti);
        this.descrizione = new SimpleStringProperty(descrizione);
        this.ricavo = new SimpleDoubleProperty(ricavo);
    }

    public Ciliegia(Ciliegia other){
        this.qualita = new SimpleStringProperty(other.getQualita());
        this.kgVenduti = new SimpleDoubleProperty(other.getKgVenduti());
        this.descrizione = new SimpleStringProperty(other.getDescrizione());
        this.ricavo = new SimpleDoubleProperty(other.getRicavo());
    }

    public String getQualita() {
        return qualita.get();
    }

    public StringProperty qualitaProperty() {
        return qualita;
    }

    public void setQualita(String qualita) {
        this.qualita.set(qualita);
    }

    public Double getKgVenduti() {
        return kgVenduti.get();
    }

    public DoubleProperty kgVendutiProperty() {
        return kgVenduti;
    }

    public void setKgVenduti(Double kgVenduti) {
        this.kgVenduti.set(kgVenduti);
    }

    public String getDescrizione() {
        return descrizione.get();
    }

    public StringProperty descrizioneProperty() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione.set(descrizione);
    }

    public Double getPrezzomedio() {
        Double pm = getRicavo() / getKgVenduti();
        return Math.round(pm * 100.0) / 100.0;
    }

    public Double getRicavo() {
        return ricavo.get();
    }

    public DoubleProperty ricavoProperty() {
        return ricavo;
    }

    public void setRicavo(Double ricavo) {
        this.ricavo.set(ricavo);
    }

}

