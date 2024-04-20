package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.ChangingWindows;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.IOException;

public class ControllerRoleSpecification {

    @FXML
    private Text label;

    @FXML
    private Button btnDirecteur;


    @FXML
    private Button btnGestionnaire;

    @FXML
    private Button btnResponsable;

    @FXML
    private Button btnVendeur;



    @FXML
    private AnchorPane anchorPane;

    private static final double ENLARGE_FACTOR = 1.05;

    private static String cmp = ControllerFstWindow.getEntreprise().getNomEntreprise();

    public void initialize(){
        label.setText(cmp);

        Platform.runLater(() -> {
            // Set initial X-coordinate to center the label
            double centerX = (anchorPane.getWidth() - label.getLayoutBounds().getWidth()) / 2;
            AnchorPane.setLeftAnchor(label, centerX);
        });
    }

    public void directeurLogin(ActionEvent event) throws IOException {
        if(!cmp.equalsIgnoreCase("")){
            ChangingWindows cw = new ChangingWindows();
            cw.switchWindow(event, "DirecteurLogin.fxml");
        }else{
            System.out.println("cmp is null");
        }
    }



    public void onMouseEntered(){
        btnDirecteur.setStyle("-fx-background-color: #2C9690 ; -fx-background-radius: 20");
        enlargeButton(btnDirecteur);
    }
    public void onMouseExited(){
        btnDirecteur.setStyle("-fx-background-color: #3DC3BC; -fx-background-radius: 20");
        restoreButtonSize(btnDirecteur);
    }

    public void onMouseEntered2(){
        btnResponsable.setStyle("-fx-background-color: #FF2C2C ; -fx-background-radius: 20");
        enlargeButton(btnResponsable);
    }
    public void onMouseExited2(){
        btnResponsable.setStyle("-fx-background-color: #FF5D5D; -fx-background-radius: 20");
        restoreButtonSize(btnResponsable);
    }

    public void onMouseEntered3(){
        btnGestionnaire.setStyle("-fx-background-color: #892DFF ; -fx-background-radius: 20");
        enlargeButton(btnGestionnaire);
    }
    public void onMouseExited3(){
        btnGestionnaire.setStyle("-fx-background-color: #9D51FF; -fx-background-radius: 20");
        restoreButtonSize(btnGestionnaire);
    }

    public void onMouseEntered4(){
        btnVendeur.setStyle("-fx-background-color: #EEFF2B ; -fx-background-radius: 20");
        enlargeButton(btnVendeur);
    }
    public void onMouseExited4(){
        btnVendeur.setStyle("-fx-background-color: #F1FF54; -fx-background-radius: 20");
        restoreButtonSize(btnVendeur);
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
