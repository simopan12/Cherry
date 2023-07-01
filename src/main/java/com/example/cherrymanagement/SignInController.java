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

public class SignInController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private static Stage stage;


    public void setStage(Stage stage){
        this.stage=stage;
    }

/* tryToNavigateToMenuPage va messa come #onAction in scene builder sul tasto Accedi nella pagina di login
    @FXML public void tryNavigateToMenuPage(){
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Effettua la connessione al database
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nome_database", "username", "password")) {
            // Query per verificare le credenziali di accesso
            String query = "SELECT * FROM utenti WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

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
*/
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
