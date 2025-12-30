package com.fragilemind.main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // FXML Dosyasını Yükle
        // Not: Dosya yolu '/com/fragilemind/view/game_view.fxml' olmalı
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fragilemind/view/game_view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1024, 768); // Pencere Boyutu
        
        stage.setTitle("Fragile Mind: Echo Chamber");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}