package com.example.cherrymanagement;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.DoubleSummaryStatistics;
import java.util.NoSuchElementException;

public class DipendentiController {
    private Stage stage;

    @FXML private TableView<Dipendente> dipendenteTable;
    @FXML private TableColumn<Dipendente,String> cfColumn;
    @FXML private TableColumn<Dipendente,String> nomeColumn;
    @FXML private TableColumn<Dipendente,String> cognomeColumn;
    @FXML private TableColumn<Dipendente,String> mansioneColumn;
    @FXML private TableColumn<Dipendente,Double> pagaColumn;
    @FXML private TableColumn<Dipendente, Double> oreColumn;
    @FXML private TableColumn<Dipendente,Double> stipendioColumn;

    @FXML public Label spesaTotaleLabel=new Label();

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void showSumSpese(TableColumn <Dipendente,Double>stipendioColumn) {
        ObservableList<Dipendente> items = stipendioColumn.getTableView().getItems();
        DoubleBinding totaleSpeseBinding = Bindings.createDoubleBinding(() -> {
            double totaleSpese = 0.0;
            for (Dipendente dipendente : items) {
                totaleSpese += dipendente.getStipendio();
            }
            return totaleSpese;
        }, items);
        spesaTotaleLabel.textProperty().bind(Bindings.format("%.2f", totaleSpeseBinding));
    }

    public void initialize(){

        cfColumn.setCellValueFactory(new PropertyValueFactory<>("cf"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cognomeColumn.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        mansioneColumn.setCellValueFactory(new PropertyValueFactory<>("mansione"));
        pagaColumn.setCellValueFactory(new PropertyValueFactory<>("paga"));
        oreColumn.setCellValueFactory(new PropertyValueFactory<>("ore"));
        stipendioColumn.setCellValueFactory(new PropertyValueFactory<>("stipendio"));



        dipendenteTable.setItems(getDipendenteData());
        dipendenteTable.getSelectionModel().selectedItemProperty();
        showSumSpese(stipendioColumn);
    }

    private ObservableList<Dipendente> getDipendenteData() {
        ObservableList<Dipendente> dipendenti = FXCollections.observableArrayList();
        showSumSpese(stipendioColumn);
        return dipendenti;
    }

    int selectedIndex() {
        int selectedIndex = dipendenteTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            throw new NoSuchElementException();
        }
        return selectedIndex;
    }



    public double calcoloStipendio(DipendentiEditController controller){
        return 0;
    }
    @FXML
    private void goBack() {
        MenuController.navigateToMenuPage();
    }
}
