package com.example.pfeprojet.Controllers;

import com.almasb.fxgl.entity.action.Action;
import com.example.pfeprojet.ChangingWindows;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class ControllerDbCreation {

    @FXML
    private Button ExitButton;
    @FXML
    private Button NextButton;


    @FXML
    private CheckBox tableProduit;
    @FXML
    private CheckBox idProduit;
    @FXML
    private CheckBox libelleProduit;
    @FXML
    private CheckBox prixUnitaire;
    @FXML
    private CheckBox categorie;




    @FXML
    private CheckBox tableService;
    @FXML
    private CheckBox idService;
    @FXML
    private CheckBox typeService;
    @FXML
    private CheckBox cout_heure;
    @FXML
    private CheckBox personnel;


    public void initialize() {

        tableProduit.setOnAction(event -> {
            if (tableProduit.isSelected()) {
                idProduit.setOpacity(1.0);
                idProduit.setDisable(false);
                libelleProduit.setOpacity(1.0);
                libelleProduit.setDisable(false);
                prixUnitaire.setOpacity(1.0);
                prixUnitaire.setDisable(false);
                categorie.setOpacity(1.0);
                categorie.setDisable(false);
            } else {
                idProduit.setOpacity(0.5);
                idProduit.setSelected(false);
                idProduit.setDisable(true);
                libelleProduit.setOpacity(0.5);
                libelleProduit.setSelected(false);
                libelleProduit.setDisable(true);
                prixUnitaire.setOpacity(0.5);
                prixUnitaire.setSelected(false);
                prixUnitaire.setDisable(true);
                categorie.setOpacity(0.5);
                categorie.setSelected(false);
                categorie.setDisable(true);
            }
        });

        tableService.setOnAction(event -> {
            if (tableService.isSelected()) {
                idService.setOpacity(1.0);
                idService.setDisable(false);
                typeService.setOpacity(1.0);
                typeService.setDisable(false);
                cout_heure.setOpacity(1.0);
                cout_heure.setDisable(false);
                personnel.setOpacity(1.0);
                personnel.setDisable(false);
            } else {
                idService.setOpacity(0.5);
                idService.setSelected(false);
                idService.setDisable(true);
                typeService.setOpacity(0.5);
                typeService.setSelected(false);
                typeService.setDisable(true);
                cout_heure.setOpacity(0.5);
                cout_heure.setSelected(false);
                cout_heure.setDisable(true);
                personnel.setOpacity(0.5);
                personnel.setSelected(false);
                personnel.setDisable(true);
            }
        });
    }



    public void tablesCreation(ActionEvent event){
        String req = null ;
        if (tableProduit.isSelected() && !tableService.isSelected()){
            req = "produit";
            //TODO : checking the children checkboxes of PRODUIT
        }else if (!tableProduit.isSelected() && tableService.isSelected()){
            req = "service";
            //TODO: checking the children checkboxes of SERVICE
        }else if (tableProduit.isSelected() && tableService.isSelected()){
            req = "Produit + service ";
            //TODO: checking the children checkboxes of PRODUIT+SERVICE
        }else {
            req = "nothingggg";
            System.out.println("vous devez coché au minimum une table !!!");
            //TODO : ShowAlert !!!
        }

        System.out.println(req);
    }



    private static final double ENLARGE_FACTOR = 1.1;
    public void onMouseEntered(javafx.scene.input.MouseEvent mouseEvent) {
        ExitButton.setStyle("-fx-background-color: #FF4545; -fx-text-fill: white; -fx-background-radius: 5em;");
        enlargeButton(ExitButton);
//        ControllerSignUp c = new ControllerSignUp();
//        System.out.println(ControllerSignUp.getCmp());
    }

    public void onMouseExited(javafx.scene.input.MouseEvent mouseEvent) {
        ExitButton.setStyle("-fx-background-color:  white; -fx-background-radius: 5em;");
        restoreButtonSize(ExitButton);
    }


    public void onMouseEntered2(javafx.scene.input.MouseEvent mouseEvent) {
        NextButton.setStyle("-fx-background-color:  #59A8A4; -fx-text-fill: white; -fx-background-radius: 5em;");
        enlargeButton(NextButton);
    }

    public void onMouseExited2(javafx.scene.input.MouseEvent mouseEvent) {
        NextButton.setStyle("-fx-background-color:  white; -fx-background-radius: 5em;");
        restoreButtonSize(NextButton);
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

    public void switchToLogin(ActionEvent event) throws IOException {
        ChangingWindows ch = new ChangingWindows();
        ch.switchWindow(event, "FstWindow.fxml");
    }
}
