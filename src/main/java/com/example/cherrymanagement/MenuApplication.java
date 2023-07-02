package com.example.cherrymanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class MenuApplication extends Application {
    private static Database database;

    public static Database getDatabase() {
        return database;
    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MenuApplication.class.getResource("SignInPage.fxml"));
        Parent root= fxmlLoader.load();
        stage.getIcons().add(new Image("/image/cherryicon.png"));

        //Ottenere il controller dalla scena principale
        SignInController signInController = fxmlLoader.getController();
        signInController.setStage(stage);

        //mostra la scena principale
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) throws SQLException {
        database =new Database("jdbc:sqlserver://localhost:1433;database=CherryManagementDB","sa","Nicolo23.");
        launch();
    }

}