package com.example.cherrymanagement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.ResultSet;


public class SignInController {

    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public static Stage getStage() {
        return stage;
    }

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private static Stage stage;


    public void setStage(Stage stage){
        this.stage=stage;
    }

    @FXML public void tryNavigateToMenuPage(){
        MenuApplication.getDatabase().SignIn(getUsernameField(),getPasswordField());
    }
    @FXML
    public void navigateToSignUpPage(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(MenuController.class.getResource("SignUpPage.fxml"));
            Parent root = fxmlLoader.load();


            SignUpController signUpController =fxmlLoader.getController();
            signUpController.setStage(stage);


            Scene scene = new Scene(root);
            stage.setScene(scene);
        }catch (Exception e){
            System.out.println("Errore");
        }
    }

}
