package com.example.cherrymanagement;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    private static Stage stage;


    public void setStage(Stage stage){
        this.stage=stage;
    }


    public static void navigateToMenuPage() {
        try {
            FXMLLoader loader = new FXMLLoader(MenuController.class.getResource("MenuPage.fxml"));
            Parent root = loader.load();

            Scene menuScene = new Scene(root);
            stage.setScene(menuScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void navigateToCiliegiePage() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(MenuController.class.getResource("CiliegiePage.fxml"));
            Parent root = fxmlLoader.load();


            CiliegieController ciliegieController =fxmlLoader.getController();
            ciliegieController.setStage(stage);


            Scene scene = new Scene(root);
            stage.setScene(scene);
        }catch (Exception e){
            System.out.println("Errore");
        }
    }


    @FXML
    public void navigateToDipendentiPage() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(MenuController.class.getResource("DipendentiPage.fxml"));
            Parent root = fxmlLoader.load();


            DipendentiController dipendentiController =fxmlLoader.getController();
            dipendentiController.setStage(stage);


            Scene scene = new Scene(root);
            stage.setScene(scene);
        }catch (Exception e){
            System.out.println("Errore");
        }
    }


    @FXML
    public void navigateToCostiPage() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(MenuController.class.getResource("CostiPage.fxml"));
            Parent root = fxmlLoader.load();


            CostiController costiController =fxmlLoader.getController();
            costiController.setStage(stage);


            Scene scene = new Scene(root);
            stage.setScene(scene);
        }catch (Exception e){
            System.out.println("Errore");
        }
    }


    @FXML
    public void navigateToInfoPage() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(MenuController.class.getResource("InfoPage.fxml"));
            Parent root = fxmlLoader.load();


            InfoController infoController =fxmlLoader.getController();
            infoController.setStage(stage);


            Scene scene = new Scene(root);
            stage.setScene(scene);
        }catch (Exception e){
            System.out.println("Errore in menu controller");
        }
    }
}