package com.example.cherrymanagement;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Utente {

    private final StringProperty usarname;
    private final StringProperty password;
    private final StringProperty nomeUtente;
    private final StringProperty cognomeUtente;
    private final StringProperty azienda;

    public Utente(String usarname, String password, String nomeUtente, String cognomeUtente, String azienda) {
        this.usarname = new SimpleStringProperty(usarname);
        this.password = new SimpleStringProperty(password);
        this.nomeUtente = new SimpleStringProperty(nomeUtente);
        this.cognomeUtente = new SimpleStringProperty(cognomeUtente);
        this.azienda = new SimpleStringProperty(azienda);
    }

    public Utente(Utente other){
        this.usarname=new SimpleStringProperty(other.getUsarname());
        this.password=new SimpleStringProperty(other.getPassword());
        this.nomeUtente=new SimpleStringProperty(other.getNomeUtente());
        this.cognomeUtente=new SimpleStringProperty(other.getCognomeUtente());
        this.azienda=new SimpleStringProperty(other.getAzienda());
    }

    public String getUsarname() {
        return usarname.get();
    }

    public StringProperty usarnameProperty() {
        return usarname;
    }

    public void setUsarname(String usarname) {
        this.usarname.set(usarname);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getNomeUtente() {
        return nomeUtente.get();
    }

    public StringProperty nomeUtenteProperty() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente.set(nomeUtente);
    }

    public String getCognomeUtente() {
        return cognomeUtente.get();
    }

    public StringProperty cognomeUtenteProperty() {
        return cognomeUtente;
    }

    public void setCognomeUtente(String cognomeUtente) {
        this.cognomeUtente.set(cognomeUtente);
    }

    public String getAzienda() {
        return azienda.get();
    }

    public StringProperty aziendaProperty() {
        return azienda;
    }

    public void setAzienda(String azienda) {
        this.azienda.set(azienda);
    }
}
