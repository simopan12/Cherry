package com.example.cherrymanagement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {
    @FXML
    private TextField username;
    @FXML private TextField password;

    @FXML private TextField nomeUtente;
    @FXML private TextField cognomeUtente;
    @FXML private TextField azienda;

    private static Stage stage;


    public void setStage(Stage stage){
        this.stage=stage;
    }

    @FXML
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
    public void navigateToSignInPage(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(MenuController.class.getResource("SignInPage.fxml"));
            Parent root = fxmlLoader.load();


            SignInController signInController =fxmlLoader.getController();
            signInController.setStage(stage);


            Scene scene = new Scene(root);
            stage.setScene(scene);
        }catch (Exception e){
            System.out.println("Errore");
        }
    }
}
