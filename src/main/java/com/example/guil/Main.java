package com.example.guil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 240);
        stage.setTitle("XML Editor");
        stage.setScene(scene);
        stage.setHeight(700);
        stage.setWidth(520);
        stage.setResizable(true);
        stage.show();

    }

    public static void main(String[] args) {

        Main.launch(args);
    }
}