package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.ChangingWindows;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class ControllerRoleSpecification extends mouseEvents{

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


    ChangingWindows cw = new ChangingWindows();
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
            cw.switchWindow(event, "LoginDirecteur.fxml");
        }else{
            System.out.println("cmp is null");
        }
    }

    @FXML
    void responsableLogin(ActionEvent event) throws IOException {
        cw.switchWindow(event, "LoginResponsable.fxml");
    }

    @FXML
    void gestionnaireLogin(ActionEvent event) throws IOException {
        cw.switchWindow(event, "LoginGestionnaire.fxml");
    }

    @FXML
    void vendeurLogin(ActionEvent event) throws IOException {
        cw.switchWindow(event, "LoginVendeur.fxml");
    }

    public void onMouseEntered(){
        btnDirecteur.setStyle("-fx-background-color: #2C9690 ; -fx-background-radius: 20");
         enlargeButtonMS(btnDirecteur);
    }
    public void onMouseExited(){
        btnDirecteur.setStyle("-fx-background-color: #3DC3BC; -fx-background-radius: 20");
         restoreButtonSizeMS(btnDirecteur);
    }

    public void onMouseEntered2(){
        btnResponsable.setStyle("-fx-background-color: #FF2C2C ; -fx-background-radius: 20");
         enlargeButtonMS(btnResponsable);
    }
    public void onMouseExited2(){
        btnResponsable.setStyle("-fx-background-color: #FF5D5D; -fx-background-radius: 20");
         restoreButtonSizeMS(btnResponsable);
    }

    public void onMouseEntered3(){
        btnGestionnaire.setStyle("-fx-background-color: #892DFF ; -fx-background-radius: 20");
         enlargeButtonMS(btnGestionnaire);
    }
    public void onMouseExited3(){
        btnGestionnaire.setStyle("-fx-background-color: #9D51FF; -fx-background-radius: 20");
         restoreButtonSizeMS(btnGestionnaire);
    }

    public void onMouseEntered4(){
        btnVendeur.setStyle("-fx-background-color: #EEFF2B ; -fx-background-radius: 20");
         enlargeButtonMS(btnVendeur);
    }
    public void onMouseExited4(){
        btnVendeur.setStyle("-fx-background-color: #F1FF54; -fx-background-radius: 20");
         restoreButtonSizeMS(btnVendeur);
    }
    

}
