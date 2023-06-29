package com.example.cherrymanagement;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CiliegieController {
    private Stage stage;
    @FXML private TableView<Ciliegia> ciliegiaTable;
    @FXML private TableColumn<Ciliegia,String> qualitaColumn;
    @FXML private TableColumn<Ciliegia,String> kgVendutiColumn;
    @FXML private TableColumn<Ciliegia,String> descrizioneColumn;
    @FXML private TableColumn<Ciliegia,String> prezzomedioColumn;
    @FXML private TableColumn<Ciliegia,String> ricavoColumn;
    @FXML public Label ricavoTotaleLabel=new Label();

    public void showSumRicavi(TableColumn <Ciliegia,String>ricavoColumn) {
        ObservableList<Ciliegia> items = ricavoColumn.getTableView().getItems();
        ricavoTotaleLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            int totaleRicavi = items.stream().mapToInt(ciliegia -> Integer.parseInt(ciliegia.getRicavo())).sum();
            return String.valueOf(totaleRicavi);
        }));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize(){
        qualitaColumn.setCellValueFactory(new PropertyValueFactory<>("qualita"));
        kgVendutiColumn.setCellValueFactory(new PropertyValueFactory<>("kgVenduti"));
        descrizioneColumn.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        prezzomedioColumn.setCellValueFactory(new PropertyValueFactory<>("prezzomedio"));
        ricavoColumn.setCellValueFactory(new PropertyValueFactory<>("ricavo"));

        ciliegiaTable.setItems(getCiliegiaData());
        ciliegiaTable.getSelectionModel().selectedItemProperty();
        showSumRicavi(ricavoColumn);
    }

    ObservableList<Ciliegia> getCiliegiaData(){
        ObservableList<Ciliegia> ciliegie = FXCollections.observableArrayList();
        ciliegie.add(new Ciliegia("Giorgia", "500","Pessima", "7","2000"));
        showSumRicavi(ricavoColumn);
        return ciliegie;
    }
    @FXML
    public void handleNewCiliegia(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("CiliegieEditView.fxml"));
            DialogPane view = loader.load();
            CiliegieEditController controller = loader.getController();

            // Set an empty person into the controller
            controller.setCiliegia(new Ciliegia("", "" , "" ,"",""));

            // Create the dialog
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Nuova Qualità");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setDialogPane(view);

            // Show the dialog and wait until the user closes it
            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                if(controller.getCiliegia().getQualita() != "" && controller.getCiliegia().getKgVenduti()!=""
                && controller.getCiliegia().getDescrizione() != "" && controller.getCiliegia().getPrezzomedio()!= ""
                && controller.getCiliegia().getRicavo()!="") {
                    ciliegiaTable.getItems().add(controller.getCiliegia());
                    showSumRicavi(ricavoColumn);
                }else{
                    Alert alert= new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Attenzione");
                    alert.setHeaderText("Non puoi lasciare un campo vuoto");
                    alert.showAndWait();
                    handleNewCiliegia();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    int selectedIndex() {
        int selectedIndex = ciliegiaTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            throw new NoSuchElementException();
        }
        return selectedIndex;
    }
    @FXML
    public void handleEditCiliegia() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("CiliegieEditView.fxml"));
            DialogPane view = loader.load();
            CiliegieEditController controller = loader.getController();

            // Set the person into the controller.
            int selectedIndex = selectedIndex();
            controller.setCiliegia(new Ciliegia(ciliegiaTable.getItems().get(selectedIndex)));

            // Create the dialog
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Edit Qualita");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setDialogPane(view);

            DialogPane dialogPane = dialog.getDialogPane();

            Ciliegia selectedCiliegia = ciliegiaTable.getItems().get(selectedIndex);

            TextField qualita= (TextField)dialogPane.lookup("#qualitaField");
            qualita.setText(selectedCiliegia.getQualita());

            // Show the dialog and wait until the user closes it
            Optional<ButtonType> clickedButton = dialog.showAndWait();

            if (clickedButton.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                if(controller.getCiliegia().getQualita() != "" && controller.getCiliegia().getKgVenduti()!=""
                        && controller.getCiliegia().getDescrizione() != "" && controller.getCiliegia().getPrezzomedio()!= ""
                        && controller.getCiliegia().getRicavo()!="") {
                    ciliegiaTable.getItems().add(controller.getCiliegia());
                    showSumRicavi(ricavoColumn);
                }else{
                    Alert alert= new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Attenzione");
                    alert.setHeaderText("Non puoi lasciare un campo vuoto");
                    alert.showAndWait();
                    handleEditCiliegia();
                }
            }

        } catch (NoSuchElementException e) {
            showNoCiliegiaSelectedAlert();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteCiliegia() {
        try {
            int selectedIndex = selectedIndex();
            showConfirmationAlert(selectedIndex);
            showSumRicavi(ricavoColumn);
        } catch (NoSuchElementException e) {
            showNoCiliegiaSelectedAlert();
        }
    }
    public void showConfirmationAlert(int index){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma");
        alert.setHeaderText("Sei sicuro di voler eliminare questa qualità?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ciliegiaTable.getItems().remove(index);
        }
    }
    void showNoCiliegiaSelectedAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Nessuna selezione");
        alert.setHeaderText("Nessuna qualità è stata selezionata");
        alert.setContentText("Perfavore seleziona una qualità dalla tabella");
        alert.showAndWait();
    }


    @FXML
    private void goBack() {
        MenuController.navigateToMenuPage();
    }

}
