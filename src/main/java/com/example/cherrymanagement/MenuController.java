package com.example.cherrymanagement;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MenuController {


    public void handleButtonClick(ActionEvent actionEvent) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(MenuController.class.getResource("CiliegiePage.fxml"));
            Stage stageCiliegie = new Stage();
            stageCiliegie.show();
        }catch (Exception e){
            System.out.println("Errore");
        }
    }


}