package com.example.cherrymanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class CiliegieController {
    private Stage stage;

    private TableView<Ciliegia> ciliegiaTable;

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
