package com.example.cherrymanagement;

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
import java.util.Optional;

public class CiliegieController {
    private Stage stage;
    @FXML private TableView<Ciliegia> ciliegiaTable;
    @FXML private TableColumn<Ciliegia,String> qualitaColumn;
    @FXML private TableColumn<Ciliegia,String> kgVendutiColumn;
    @FXML private TableColumn<Ciliegia,String> descrizioneColumn;
    @FXML private TableColumn<Ciliegia,String> prezzomedioColumn;
    @FXML private TableColumn<Ciliegia,String> ricavoColumn;




    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handleNewCiliegia(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("CiliegieEditView.fxml"));
            DialogPane view = loader.load();
            CiliegieEditController controller = loader.getController();

            // Set an empty person into the controller
            controller.setCiliegia(new Ciliegia("Qualita", "kg venduti" , "Descrizione" ,"€/kg","0"));

            // Create the dialog
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Nuova Qualità");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setDialogPane(view);

            // Show the dialog and wait until the user closes it
            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                ciliegiaTable.getItems().add(controller.getCiliegia());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void goBack() {
        MenuController.navigateToMenuPage();
    }

}
