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

        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Logo.png")));

        stage.getIcons().add(icon);
        stage.setResizable(false);

        stage.setTitle("FacturEase");
        stage.setScene(scene);

        stage.show();
    }

    public static void restartApplication() {
        try {
            // Get the current Java executable
            String javaBin = System.getProperty("java.home") + "/bin/java";
            // Get the current application class name
            String className = HelloApplication.class.getName();

            // Build the command to restart the application
            ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", System.getProperty("java.class.path"), className);

            // Start the new process
            builder.start();

            // Close the current application
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {launch();}
}