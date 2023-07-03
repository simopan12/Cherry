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
import java.sql.SQLException;
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
        ObservableList<Dipendente> dipendenti = MenuApplication.getDatabase().getDipendenti(MenuApplication.getDatabase().getUsername_utente());;
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

    @FXML
    public void handleNewDipendente(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("DipendentiEditView.fxml"));
            DialogPane view = loader.load();
            DipendentiEditController controller = loader.getController();

            controller.setDipendente(new Dipendente("", "" , "" ,"",1,0));

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Nuovo Dipendente");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setDialogPane(view);

            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                if(!controller.getDipendente().getCf().equals("")  && !controller.getDipendente().getNome().equals("")
                      && !controller.getDipendente().getCognome().equals("") && !controller.getDipendente().getMansione().equals("")
                        && controller.getDipendente().getPaga()>0) {

                      MenuApplication.getDatabase().executeUpdate("INSERT INTO Dipendenti(CF,Nome,Cognome,Mansione,Paga,Ore,Username_utente) VALUES(?,?,?,?,?,?,?)",
                            controller.getDipendente().getCf(),controller.getDipendente().getNome(),controller.getDipendente().getCognome(),
                            controller.getDipendente().getMansione(),controller.getDipendente().getPaga(),
                            controller.getDipendente().getOre(),MenuApplication.getDatabase().getUsername_utente());

                    dipendenteTable.setItems(getDipendenteData());
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void handleEditDipendente() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("DipendentiEditView.fxml"));
            DialogPane view = loader.load();
            DipendentiEditController controller = loader.getController();

            int selectedIndex = selectedIndex();
            controller.setDipendente(new Dipendente(dipendenteTable.getItems().get(selectedIndex)));

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Edit Dipendente");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setDialogPane(view);

            DialogPane dialogPane = dialog.getDialogPane();

            Dipendente selectedDipendente = dipendenteTable.getItems().get(selectedIndex);

            TextField cf = (TextField)dialogPane.lookup("#cfField");
            cf.setText(selectedDipendente.getCf());
            TextField nome = (TextField)dialogPane.lookup("#nomeField");
            nome.setText(selectedDipendente.getNome());
            TextField cognome = (TextField)dialogPane.lookup("#cognomeField");
            cognome.setText(selectedDipendente.getCognome());
            TextField mansione = (TextField)dialogPane.lookup("#mansioneField");
            mansione.setText(selectedDipendente.getMansione());
            TextField paga = (TextField)dialogPane.lookup("#pagaField");
            paga.setText(String.valueOf(selectedDipendente.getPaga()));
            Spinner ore = (Spinner)dialogPane.lookup("#oreSpinner");
            ore.setValueFactory( new SpinnerValueFactory.DoubleSpinnerValueFactory(0,10000,selectedDipendente.getOre(),1));

            Optional<ButtonType> clickedButton = dialog.showAndWait();

            if (clickedButton.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                if(!controller.getDipendente().getCf().equals("") && !controller.getDipendente().getNome().equals("")
                        && !controller.getDipendente().getCognome().equals("") && !controller.getDipendente().getMansione().equals("")
                        && controller.getDipendente().getPaga()>0) {

                    MenuApplication.getDatabase().executeUpdate("DELETE FROM Dipendenti WHERE CF = ?",selectedDipendente.getCf());
                    MenuApplication.getDatabase().executeUpdate("INSERT INTO Dipendenti(CF,Nome,Cognome,Mansione,Paga,Ore,Username_utente) VALUES(?,?,?,?,?,?,?)",
                            controller.getDipendente().getCf(),controller.getDipendente().getNome(),controller.getDipendente().getCognome(),
                            controller.getDipendente().getMansione(),controller.getDipendente().getPaga(),
                            controller.getDipendente().getOre(),MenuApplication.getDatabase().getUsername_utente());

                    dipendenteTable.getItems().remove(selectedIndex);
                    dipendenteTable.setItems(getDipendenteData());
                    showSumSpese(stipendioColumn);
                }else{
                    Alert alert= new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Attenzione");
                    alert.setHeaderText("Non puoi lasciare un campo vuoto");
                    alert.showAndWait();
                    handleEditDipendente();
                }
            }

        } catch (NoSuchElementException e) {
            showNoDipendenteSelectedAlert();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleDeleteDipendente() {
        try {
            int selectedIndex = selectedIndex();
            showConfirmationAlert(selectedIndex);
            showSumSpese(stipendioColumn);
        } catch (NoSuchElementException e) {
            showNoDipendenteSelectedAlert();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showConfirmationAlert(int index) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma");
        alert.setHeaderText("Sei sicuro di voler eliminare questo dipendente?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            MenuApplication.getDatabase().executeUpdate("DELETE FROM Dipendenti WHERE CF = ?",getDipendenteData().get(index).getCf());
            dipendenteTable.getItems().remove(index);
        }
    }
    void showNoDipendenteSelectedAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Nessuna selezione");
        alert.setHeaderText("Nessun dipendente è stata selezionata");
        alert.setContentText("Perfavore seleziona un dipendente dalla tabella");
        alert.showAndWait();
    }

    @FXML
    private void goBack() {
        MenuController.navigateToMenuPage();
    }
}
