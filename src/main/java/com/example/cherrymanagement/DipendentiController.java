package com.example.cherrymanagement;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.DoubleSummaryStatistics;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    @FXML
    public void handleNewDipendente(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("DipendentiEditView.fxml"));
            DialogPane view = loader.load();
            DipendentiEditController controller = loader.getController();

            // Set an empty person into the controller
            controller.setDipendente(new Dipendente("", "" , "" ,"",1,0,0));

            // Create the dialog
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Nuovo Dipendente");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setDialogPane(view);

            // Show the dialog and wait until the user closes it
            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                if(!controller.getDipendente().getCf().equals("")  && !controller.getDipendente().getNome().equals("")
                      && !controller.getDipendente().getCognome().equals("") && !controller.getDipendente().getMansione().equals("")
                && controller.getDipendente().getPaga()>0) {
                    controller.getDipendente().setStipendio(calcoloStipendio(controller));
                    dipendenteTable.getItems().add(controller.getDipendente());
                    showSumSpese(stipendioColumn);
                }else{
                  Alert alert= new Alert(Alert.AlertType.WARNING);
                   alert.setTitle("Attenzione");
                  alert.setHeaderText("Non puoi lasciare un campo vuoto");
                   alert.showAndWait();
                   handleNewDipendente();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int selectedIndex() {
        int selectedIndex = dipendenteTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            throw new NoSuchElementException();
        }
        return selectedIndex;
    }



    public double calcoloStipendio(DipendentiEditController controller){
        return controller.getDipendente().getOre()*controller.getDipendente().getPaga();
    }
    @FXML
    private void goBack() {
        MenuController.navigateToMenuPage();
    }
}
