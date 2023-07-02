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
        String username = getUsernameField().getText();
        String password = getPasswordField().getText();

        // Effettua la connessione al database
        try {
            // Query per verificare le credenziali di accesso
            String query = "SELECT * FROM utenti WHERE username = ? AND password = ?";

            ResultSet resultSet = MenuApplication.getDatabase().executeQuery(query,username,password);

            if (resultSet.next()) {
                // Login riuscito
                navigateToMenuPage();
            } else {
                // Login fallito
                showLoginError("Credenziali non valide.");
            }
        } catch (SQLException e) {
            System.out.println("Errore di connessione al database: " + e.getMessage());
            showLoginError("Errore di connessione al database.");
        }

    }

    private void showLoginError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore di login");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void navigateToMenuPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MenuController.class.getResource("MenuPage.fxml"));
            Parent root = fxmlLoader.load();

            MenuController menuController =fxmlLoader.getController();
            menuController.setStage(stage);

            Scene menuScene = new Scene(root);
            stage.setScene(menuScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
