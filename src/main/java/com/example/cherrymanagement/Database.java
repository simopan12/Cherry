package com.example.cherrymanagement;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Database {
    private String url;
    private String username;
    private String password;
    private Connection connection;
    private String username_utente;

    public String getUsername_utente() {
        return username_utente;
    }

    public void setUsername_utente(String username_utente) {
        this.username_utente = username_utente;
    }

    public Database(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.connection = null;
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
            int risultato = executeUpdate(query,username,password,nomeUtente,cognomeUtente,azienda);

            // Registrazione riuscita
            showRegistrationSuccess();
        } catch (SQLException e) {
            System.out.println("Errore di connessione al database: " + e.getMessage());
            showRegistrationError("Errore di connessione al database.");
        }
    }

    public void SignIn(TextField usernameField, PasswordField passwordField){
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Effettua la connessione al database
        try {
            // Query per verificare le credenziali di accesso
            String query = "SELECT * FROM Utenti WHERE Username = ? AND password = ?";

            ResultSet resultSet = executeQuery(query,username,password);

            if (resultSet.next()) {
                setUsername_utente(username);
                // Login riuscito
                MenuController.navigateToMenuPage();
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


    private boolean checkUserExists(Connection connection, String username) throws SQLException {
        String query = "SELECT * FROM Utenti WHERE Username = ?";
        ResultSet resultSet = executeQuery(query,username);
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


    public ObservableList<Ciliegia> getCiliegie(String uuid) {
        checkConnection();
        try {
            final String query = "SELECT * FROM Ciliegie JOIN Utenti ON Ciliegie.Username_utente = Utenti.Username WHERE Ciliegie.Username_utente = ?";

            ResultSet resultSet = executeQuery(query,uuid);

            Ciliegia ciliegia = null;
            List<Ciliegia> ciliegie = new ArrayList<>();

            if (resultSet.next()) {
                do {
                    ciliegia = new Ciliegia(resultSet.getString("Qualità"),
                            resultSet.getDouble("Kg_Venduti"),
                            resultSet.getString("Descrizione"),
                            resultSet.getDouble("Ricavo"));
                    ciliegie.add(ciliegia);
                } while (resultSet.next());
            }

            resultSet.close();
            return FXCollections.observableList(ciliegie);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public ObservableList<Costo> getCosti(String uuid) {
        checkConnection();
        try {
            final String query = "SELECT * FROM Costi JOIN Utenti ON Costi.Username_utente = Utenti.Username WHERE Costi.Username_utente = ?";

            ResultSet resultSet = executeQuery(query,uuid);

            Costo costo = null;
            List<Costo> costi = new ArrayList<>();

            if (resultSet.next()) {
                do {
                    costo = new Costo(resultSet.getString("ID"),
                            resultSet.getString("Tipo"),
                            resultSet.getDouble("Ammontare"));
                    costi.add(costo);
                } while (resultSet.next());
            }

            resultSet.close();
            return FXCollections.observableList(costi);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ObservableList<Dipendente> getDipendenti(String uuid) {
        checkConnection();
        try {
            final String query = "SELECT * FROM Dipendenti JOIN Utenti ON Dipendenti.Username_utente = Utenti.Username WHERE Dipendenti.Username_utente = ?";

            ResultSet resultSet = executeQuery(query,uuid);

            Dipendente dipendente = null;
            List<Dipendente> dipendenti = new ArrayList<>();

            if (resultSet.next()) {
                do {
                    dipendente = new Dipendente(resultSet.getString("CF"),
                            resultSet.getString("Nome"),
                            resultSet.getString("Cognome"),
                            resultSet.getString("Mansione"),
                            resultSet.getDouble("Paga"),
                            resultSet.getDouble("Ore"));
                    dipendenti.add(dipendente);
                } while (resultSet.next());
            }

            resultSet.close();
            return FXCollections.observableList(dipendenti);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Utente exportInfoFromDatabase(){
        checkConnection();
        try {
            final String query= "SELECT * FROM Utenti WHERE Utenti.Username=?";
            ResultSet resultSet= executeQuery(query,username_utente);

            Utente utente =null;

            if(resultSet.next()){
                utente = new Utente(resultSet.getString("Username"),
                        resultSet.getString("Password"),
                        resultSet.getString("Nome_Utente"),
                        resultSet.getString("Cognome_Utente"),
                        resultSet.getString("Azienda"));
            }
            resultSet.close();
            return utente;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Ciliegia> getPieChartDataCiliegia(){
        checkConnection();
        try {
            final String query="SELECT * FROM Ciliegie WHERE Ciliegie.Username_utente = ?";
            ResultSet resultSet =executeQuery(query,username_utente);

            Ciliegia ciliegia=null;
            ObservableList<Ciliegia> pieChartData = FXCollections.observableArrayList();
            if(resultSet.next()){
                do {
                    ciliegia = new Ciliegia(resultSet.getString("Qualità"),
                            resultSet.getDouble("Kg_Venduti"),
                            resultSet.getString("Descrizione"),
                            resultSet.getDouble("Ricavo"));
                    pieChartData.add(ciliegia);
                }while (resultSet.next());
            }
            resultSet.close();
            return pieChartData;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getNomeAziendaData(){
        checkConnection();
        try {
            final String query = "SELECT Utenti.Azienda FROM Utenti WHERE Utenti.Username = ?";
            ResultSet resultSet = executeQuery(query,username_utente);

            String nomeAzienda = null;
            if(resultSet.next()){
                nomeAzienda = resultSet.getString("Azienda");
            }

            resultSet.close();
            return nomeAzienda;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Dipendente> getPieChartDataDipendente(){
        checkConnection();
        try {
            final String query="SELECT * FROM Dipendenti WHERE Dipendenti.Username_utente = ?";
            ResultSet resultSet =executeQuery(query,username_utente);

            Dipendente dipendente=null;
            ObservableList<Dipendente> pieChartData = FXCollections.observableArrayList();
            if(resultSet.next()){
                do {
                    dipendente = new Dipendente(resultSet.getString("CF"),
                            resultSet.getString("Nome"),
                            resultSet.getString("Cognome"),
                            resultSet.getString("Mansione"),
                            resultSet.getDouble("Paga"),
                            resultSet.getDouble("Ore"));
                    pieChartData.add(dipendente);
                }while (resultSet.next());
            }
            resultSet.close();
            return pieChartData;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public double getCiliegieRicavi(){
        ObservableList<Ciliegia> ciliegie = getCiliegie(username_utente);
        double ricavi = 0;
        for(Ciliegia ciliegia : ciliegie){
            ricavi += ciliegia.getRicavo();
        }
        return ricavi;
    }

    public double getDipendentiStipendi(){
        ObservableList<Dipendente> dipendenti = getDipendenti(username_utente);
        double stipendi = 0;
        for(Dipendente dipendente : dipendenti){
            stipendi += dipendente.getStipendio();
        }
        return stipendi;
    }

    public double getCostiAmmontare(){
        ObservableList<Costo> costi = getCosti(username_utente);
        double ammontare = 0;

        for(Costo costo : costi){
            ammontare += costo.getAmmontare();
        }

        return ammontare;
    }

    public void showConfirmationAlertUtente() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma");
        alert.setHeaderText("Sei sicuro di voler eliminare il tuo account?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            executeUpdate("DELETE FROM Ciliegie WHERE Username_utente = ?",username_utente);
            executeUpdate("DELETE FROM Costi WHERE Username_utente = ?",username_utente);
            executeUpdate("DELETE FROM Dipendenti WHERE Username_utente = ?",username_utente);
            executeUpdate("DELETE FROM Utenti WHERE Username = ?",username_utente);
            navigateToSignInPage();
        }
    }
}
