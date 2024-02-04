package com.example.pfeprojet;


import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Button ;
import javafx.util.Duration;


import java.io.IOException;
import java.util.Objects;

public class ControllerFstWindow {


   private static final double ENLARGE_FACTOR = 1.1;


   @FXML
   private Button LoginButton;

   @FXML
   private Button SignUpButton;

   //Switching to SignUp window
   public void switchToSignUp(ActionEvent event) throws IOException {
      Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SingUp.fxml")));

      // Create a new stage for scene1
      Stage newStage = new Stage();

      Scene newScene = new Scene(root);
      // Load the icon for scene1
      Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo.png")));
      newStage.getIcons().add(icon);

      // Set the title for scene1
      newStage.setResizable(false);
      newStage.setTitle("Sign Up");

      // Set the scene and show the stage
      newStage.setScene(newScene);
      newStage.show();

      // Close the current (scene2) stage
      Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      currentStage.close();
   }


   @FXML
   public void onMouseEntered(MouseEvent event) {
      LoginButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-background-radius: 5em;");
      enlargeButton(LoginButton);

   }

   @FXML
   public void onMouseExited(MouseEvent event) {
      LoginButton.setStyle("-fx-background-color: #59A8A4; -fx-background-radius: 5em;");
      restoreButtonSize(LoginButton);


   }

   @FXML
   public void onMouseEntered2(MouseEvent event) {
      SignUpButton.setStyle("-fx-background-color: #59A8A4; -fx-text-fill: white; -fx-background-radius: 5em;");
      enlargeButton(SignUpButton);
   }

   @FXML
   public void onMouseExited2(MouseEvent event) {
      SignUpButton.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 5em;");
      restoreButtonSize(SignUpButton);

   }

   private void enlargeButton(Button button) {
      ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(120), button);
      scaleTransition.setToX(ENLARGE_FACTOR);
      scaleTransition.setToY(ENLARGE_FACTOR);
      scaleTransition.play();
   }

   private void restoreButtonSize(Button button) {
      ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(120), button);
      scaleTransition.setToX(1.0);
      scaleTransition.setToY(1.0);
      scaleTransition.play();
   }



}