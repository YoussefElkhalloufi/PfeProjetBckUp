package com.example.pfeprojet.Controllers;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class mouseEvents {

    private static final double ENLARGE_FACTOR = 1.05;

    public void onMouseEntered(javafx.scene.input.MouseEvent mouseEvent, Button btn) {
        btn.setStyle("-fx-background-color: #FF4545; -fx-text-fill: white; -fx-background-radius: 5em;");
        enlargeButton(btn);
    }

    public void onMouseExited(javafx.scene.input.MouseEvent mouseEvent, Button btn) {
        btn.setStyle("-fx-background-color:  white; -fx-background-radius: 5em;");
        restoreButtonSize(btn);
    }


    public void onMouseEntered2(javafx.scene.input.MouseEvent mouseEvent, Button btn) {
        btn.setStyle("-fx-background-color:  #59A8A4; -fx-text-fill: white; -fx-background-radius: 5em;");
        enlargeButton(btn);
    }

    public void onMouseExited2(javafx.scene.input.MouseEvent mouseEvent, Button btn) {
        btn.setStyle("-fx-background-color:  white; -fx-background-radius: 5em;");
        restoreButtonSize(btn);
    }

    public void enlargeButton(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(120), button);
        scaleTransition.setToX(ENLARGE_FACTOR);
        scaleTransition.setToY(ENLARGE_FACTOR);
        scaleTransition.play();
    }

    public void restoreButtonSize(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(120), button);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.play();
    }
}
