package com.example.pfeprojet.Controllers;


import com.example.pfeprojet.ChangingWindows;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.IOException;

public class ControllerFstWindow {


   mouseEvents ms = new mouseEvents();

   private static final double ENLARGE_FACTOR = 1.1;


   @FXML
   private Button LoginButton;

   @FXML
   private Button SignUpButton;

   //Switching to SignUp window
   public void switchToSignUp(ActionEvent event) throws IOException {
      ChangingWindows ch = new ChangingWindows();
      ch.switchWindow(event,"SingUp.fxml");
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
      ms.onMouseEntered2(event, SignUpButton);
   }

   @FXML
   public void onMouseExited2(MouseEvent event) {
      ms.onMouseExited2(event, SignUpButton);

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