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

public class SignUpController {
    @FXML
    private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML private TextField nomeUtenteField;
    @FXML private TextField cognomeUtenteField;
    @FXML private TextField aziendaField;

    private static Stage stage;


    public void setStage(Stage stage){
        this.stage=stage;
    }

/* questa va messa come #onAction sul pulsante Registrati nella pagina di registrazione
    private void handleRegisterButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String nomeUtente = nomeUtenteField.getText();
        String cognomeUtente = cognomeUtenteField.getText();
        String azienda = aziendaField.getText();

        // Effettua la connessione al database
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nome_database", "username", "password")) {
            // Verifica se l'utente esiste già
            if (checkUserExists(connection, username)) {
                showRegistrationError("L'utente esiste già.");
                return;
            }

            // Esegui l'inserimento dell'utente nel database
            String insertQuery = "INSERT INTO utenti (username, password, nome, cognome, azienda) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, nomeUtente);
            statement.setString(4, cognomeUtente);
            statement.setString(5, azienda);
            statement.executeUpdate();

            // Registrazione riuscita
            showRegistrationSuccess();
        } catch (SQLException e) {
            System.out.println("Errore di connessione al database: " + e.getMessage());
            showRegistrationError("Errore di connessione al database.");
        }
    }
    */

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
    /*
    private boolean checkUserExists(Connection connection, String username) throws SQLException {
        String query = "SELECT * FROM utenti WHERE username = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        return statement.executeQuery().next();
    }

     */

    private void showRegistrationSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registrazione riuscita");
        alert.setHeaderText(null);
        alert.setContentText("La registrazione è avvenuta con successo. Effettua il login.");
        alert.showAndWait();

        navigateToSignInPage();
    }
    private void showRegistrationError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore di registrazione");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
