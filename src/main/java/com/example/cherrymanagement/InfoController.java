package com.example.cherrymanagement;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class InfoController {
    private Stage stage;

    @FXML public Label nomeLabel = new Label();
    @FXML public Label cognomeLabel = new Label();
    @FXML public Label nomeAziendaLabel = new Label();
    @FXML public Label usernameLabel = new Label();
    @FXML public PieChart pieChartCiliegia = new PieChart();
    @FXML public PieChart pieChartDipendente = new PieChart();

    public void initialize(){
        ObservableList<Ciliegia> pieChartDataCiliegia = MenuApplication.getDatabase().getPieChartDataCiliegia();
        ObservableList<PieChart.Data> chartDataCiliegia = FXCollections.observableArrayList();

        pieChartDataCiliegia.forEach(data ->
                chartDataCiliegia.add(new PieChart.Data(data.getQualita() + " " + Double.parseDouble(data.getKgVenduti()) + " kg", Double.parseDouble(data.getKgVenduti())))
        );
        pieChartCiliegia.setData(chartDataCiliegia);


        ObservableList<Dipendente> pieChartDataDipendente = MenuApplication.getDatabase().getPieChartDataDipendente();
        ObservableList<PieChart.Data> chartDataDipendente = FXCollections.observableArrayList();

        pieChartDataDipendente.forEach(data ->
                chartDataDipendente.add(new PieChart.Data(data.getNome() + " " + data.getCognome() + " " + data.getOre() + "h", data.getOre()))
        );
        pieChartDipendente.setData(chartDataDipendente);

    }

    public void showInfo(){
        Utente utente = MenuApplication.getDatabase().exportInfoFromDatabase();
        nomeLabel.textProperty().set(utente.getNomeUtente());
        cognomeLabel.textProperty().set(utente.getCognomeUtente());
        nomeAziendaLabel.textProperty().set(utente.getAzienda());
        usernameLabel.textProperty().set(utente.getUsarname());
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    private void goBack() {
        MenuController.navigateToMenuPage();
    }
}
