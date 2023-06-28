package com.example.cherrymanagement;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class MenuApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MenuApplication.class.getResource("MenuPage.fxml"));
        Parent root= fxmlLoader.load();
        stage.getIcons().add(new Image("/image/cherryicon.png"));

        //Ottenere il controller dalla scena principale
        MenuController menuController = fxmlLoader.getController();
        menuController.setStage(stage);

        //mostra la scena principale
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }

}