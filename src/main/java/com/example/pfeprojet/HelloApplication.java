package com.example.pfeprojet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FstWindow.fxml")));
        Scene scene = new Scene(root);
        //System.out.println("FstWindow.fxml is called successfully");

        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo.png")));
        //System.out.println("logo.png is called successfully");
        //Test test git
        //test 2 git
        //test douae
        //TEST YOUSSEF





        stage.getIcons().add(icon);
        stage.setResizable(false);

        stage.setTitle("FacturEase");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}