package com.example.pfeprojet;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ChangingWindows {


    public void switchWindow(ActionEvent event, String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));


        // Create a new stage for scene1
        Stage newStage = new Stage();
        Scene newScene = new Scene(root);

        // Load the icon for scene1
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Group7.png")));
        newStage.getIcons().add(icon);

        // Set the title for scene1
        newStage.setResizable(false);
        newStage.setTitle("FacturEase");

        // Set the scene and show the stage
        newStage.setScene(newScene);
        // Disable closing the new window
        newStage.setOnCloseRequest(event1 -> event1.consume());
        newStage.show();

        // Close the current (scene2) stage
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
