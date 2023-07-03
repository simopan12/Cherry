package com.example.cherrymanagement;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class InfoController {
    private Stage stage;

    @FXML public Label nomeLabel=new Label();
    @FXML public Label cognomeLabel=new Label();
    @FXML public Label nomeAziendaLabel=new Label();
    @FXML public Label usernameLabel=new Label();

    @FXML public PieChart pieChart =new PieChart();

    public void initialize(){
        ObservableList<Ciliegia> pieChartData =MenuApplication.getDatabase().getPieChartData();
        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();

        pieChartData.forEach(data ->
                chartData.add(new PieChart.Data(data.getQualita(), Double.parseDouble(data.getKgVenduti())))
        );

        pieChart = new PieChart(chartData);
        // Aggiungi pieChart alla tua scena o a un nodo appropriato nella tua interfaccia grafica
    }

    public void showInfo(){
        Utente utente =MenuApplication.getDatabase().exportInfoFromDatabase();
        nomeLabel.textProperty().set(utente.getNomeUtente());
        cognomeLabel.textProperty().set(utente.getCognomeUtente());
        nomeAziendaLabel.textProperty().set(utente.getAzienda());
        usernameLabel.textProperty().set(utente.getUsarname());
        initialize();
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    private void goBack() {
        MenuController.navigateToMenuPage();
    }
}
