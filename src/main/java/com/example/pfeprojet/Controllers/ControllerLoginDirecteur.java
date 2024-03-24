package com.example.pfeprojet.Controllers;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ControllerLoginDirecteur {
    @FXML
    private Button LoginButton;

    @FXML
    private Text label;

    private static String cmp = ControllerFstWindow.getCmp();

    public static String getCmp(){
        return cmp;
    }

    @FXML
    private AnchorPane anchorPane;
    public void initialize(){
        label.setText(cmp);

        Platform.runLater(() -> {
            // Set initial X-coordinate to center the label
            double centerX = (anchorPane.getWidth() - label.getLayoutBounds().getWidth()) / 2;
            AnchorPane.setLeftAnchor(label, centerX);
        });
    }


    @FXML
    public void onMouseEntered(MouseEvent event) {
        LoginButton.setStyle("-fx-background-color: #59A8A4; -fx-text-fill: white; -fx-background-radius: 5em;");
        enlargeButton(LoginButton);
    }

    @FXML
    public void onMouseExited(MouseEvent event) {
        LoginButton.setStyle("-fx-background-color: white; -fx-background-radius: 5em;");
        restoreButtonSize(LoginButton);
    }

    private static final double ENLARGE_FACTOR = 1.1;
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
