package com.example.cherrymanagement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class Database {
    private String url;
    private String username;
    private String password;
    private Connection connection;

    public Database(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.connection=null;
        connect();
    }
    private void checkConnection() {
        if (this.connection == null)connect();
    }

    public void connect() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println(connection);
        } catch(SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public ResultSet executeQuery(String query, Object... parameters) throws SQLException {
        checkConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        setParameters(statement, parameters);
        return statement.executeQuery();
    }

    public int executeUpdate(String query, Object... parameters) throws SQLException {
        checkConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        setParameters(statement, parameters);
        return statement.executeUpdate();
    }

    private void setParameters(PreparedStatement statement, Object... parameters) throws SQLException {
        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }
        }
    }

    public void SignUp(TextField usernameField, TextField passwordField,TextField nomeUtenteField,TextField cognomeUtenteField,TextField aziendaField){
        String username = usernameField.getText();
        String password = passwordField.getText();
        String nomeUtente = nomeUtenteField.getText();
        String cognomeUtente = cognomeUtenteField.getText();
        String azienda = aziendaField.getText();

        // Effettua la connessione al database
        try{
            // Verifica se l'utente esiste già
            if (checkUserExists(connection, username)) {
                showRegistrationError("L'utente esiste già.");
                return;
            }

            // Esegui l'inserimento dell'utente nel database
            String query = "INSERT INTO Utenti (Username, Password, Nome_Utente, Cognome_Utente, Azienda) VALUES (?, ?, ?, ?, ?)";
            ResultSet resultSet = MenuApplication.getDatabase().executeQuery(query,username,password,nomeUtente,cognomeUtente,azienda);

            // Registrazione riuscita
            showRegistrationSuccess();
        } catch (SQLException e) {
            System.out.println("Errore di connessione al database: " + e.getMessage());
            showRegistrationError("Errore di connessione al database.");
        }
    }

    private boolean checkUserExists(Connection connection, String username) throws SQLException {
        String query = "SELECT * FROM Utenti WHERE Username = ?";
        ResultSet resultSet=MenuApplication.getDatabase().executeQuery(query,username);
        return resultSet.next();
    }


    private void showRegistrationSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registrazione riuscita");
        alert.setHeaderText(null);
        alert.setContentText("La registrazione è avvenuta con successo. Effettua il login.");
        alert.showAndWait();
        navigateToSignInPage();
    }

    public void navigateToSignInPage(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(MenuController.class.getResource("SignInPage.fxml"));
            Parent root = fxmlLoader.load();


            SignInController signInController =fxmlLoader.getController();
            signInController.setStage(SignInController.getStage());


            Scene scene = new Scene(root);
            SignInController.getStage().setScene(scene);
        }catch (Exception e){
            System.out.println("Errore");
        }
    }
    private void showRegistrationError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore di registrazione");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
