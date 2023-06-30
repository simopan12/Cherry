package com.example.cherrymanagement;

import javafx.beans.property.*;

public class Dipendente {
    private final StringProperty cf;
    private final StringProperty nome;
    private final StringProperty cognome;
    private final StringProperty mansione;
    private final DoubleProperty paga;
    private final DoubleProperty ore;
    private final DoubleProperty stipendio;

    public Dipendente(String cf,String nome, String cognome,String mansione,double paga, double ore,double stipendio){
        this.cf=new SimpleStringProperty(cf);
        this.nome=new SimpleStringProperty(nome);
        this.cognome=new SimpleStringProperty(cognome);
        this.mansione=new SimpleStringProperty(mansione);
        this.paga=new SimpleDoubleProperty(paga);
        this.ore=new SimpleDoubleProperty(ore);
        this.stipendio=new SimpleDoubleProperty(stipendio);

    }

    public Dipendente(Dipendente other){
        this.cf=new SimpleStringProperty(other.getCf());
        this.nome=new SimpleStringProperty(other.getNome());
        this.cognome=new SimpleStringProperty(other.getCognome());
        this.mansione=new SimpleStringProperty(other.getMansione());
        this.paga= new SimpleDoubleProperty(other.getPaga());
        this.ore= new SimpleDoubleProperty(other.getOre());
        this.stipendio= new SimpleDoubleProperty(other.getStipendio());
    }

    public String getCf() {
        return cf.get();
    }

    public StringProperty cfProperty() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf.set(cf);
    }

    public String getNome() {
        return nome.get();
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getCognome() {
        return cognome.get();
    }

    public StringProperty cognomeProperty() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome.set(cognome);
    }

    public String getMansione() {
        return mansione.get();
    }

    public StringProperty mansioneProperty() {
        return mansione;
    }

    public void setMansione(String mansione) {
        this.mansione.set(mansione);
    }

    public double getPaga() {
        return paga.get();
    }

    public DoubleProperty pagaProperty() {
        return paga;
    }

    public void setPaga(double paga) {
        this.paga.set(paga);
    }

    public double getOre() {
        return ore.get();
    }

    public DoubleProperty oreProperty() {
        return ore;
    }

    public void setOre(double ore) {
        this.ore.set(ore);
    }

    public double getStipendio() {
        return stipendio.get();
    }

    public DoubleProperty stipendioProperty() {
        return stipendio;
    }

    public void setStipendio(double stipendio) {
        this.stipendio.set(stipendio);
    }
}
