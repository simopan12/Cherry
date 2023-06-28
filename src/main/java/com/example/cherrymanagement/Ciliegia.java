package com.example.cherrymanagement;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Ciliegia {
    private final StringProperty qualita;
    private final StringProperty kgVenduti;
    private final StringProperty descrizione;
    private final StringProperty prezzomedio;
    private final StringProperty ricavo;


    public Ciliegia(String qualita, String kgVenduti, String descrizione, String prezzomedio, String ricavo) {
        this.qualita = new SimpleStringProperty(qualita);
        this.kgVenduti = new SimpleStringProperty(kgVenduti);
        this.descrizione = new SimpleStringProperty(descrizione);
        this.prezzomedio = new SimpleStringProperty(prezzomedio);
        this.ricavo = new SimpleStringProperty(ricavo);
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

    public String getKgVenduti() {
        return kgVenduti.get();
    }

    public StringProperty kgVendutiProperty() {
        return kgVenduti;
    }

    public void setKgVenduti(String kgVenduti) {
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

    public String getPrezzomedio() {
        return prezzomedio.get();
    }

    public StringProperty prezzomedioProperty() {
        return prezzomedio;
    }

    public void setPrezzomedio(String prezzomedio) {
        this.prezzomedio.set(prezzomedio);
    }

    public String getRicavo() {
        return ricavo.get();
    }

    public StringProperty ricavoProperty() {
        return ricavo;
    }

    public void setRicavo(String ricavo) {
        this.ricavo.set(ricavo);
    }
}

