package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.ChangingWindows;
import com.example.pfeprojet.Entreprise.Entreprise;
import com.example.pfeprojet.Entreprise.Gestionnaire;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;


//TODO : changement de mot de passe pour le personnel ( +direceteur )


import java.io.IOException;

public class ControllerDashboardGestionnaire {

    @FXML
    private Button aideBtn;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button deconnexionBtn;

    @FXML
    private AnchorPane inventaire;

    @FXML
    private Text label;

    @FXML
    private AnchorPane messagerieAnchor;

    @FXML
    private Text nbPersonnel;

    @FXML
    private AnchorPane personnel;

    ChangingWindows cw = new ChangingWindows();
    private static final double ENLARGE_FACTOR = 1.05;

    public static Gestionnaire gestio = ControllerLoginGestionnaire.getGestionnaire();


    private static final Entreprise e = ControllerFstWindow.getEntreprise();

    public void initialize(){
        label.setText("Gestionnaire '" +gestio.getNom() +"' de l'entreprise '" +e.getNomEntreprise()+"'");

        nbPersonnel.setText(String.valueOf(e.getNbPersonnel()));

        Platform.runLater(() -> {
            double centerX = (anchorPane.getWidth() - label.getLayoutBounds().getWidth()) / 2;
            AnchorPane.setLeftAnchor(label, centerX);
        });
    }
    @FXML
    void helpAndSupport(ActionEvent event) throws IOException {
        cw.switchWindow(event, "helpAndSupport.fxml");
    }

    @FXML
    void onMouseEnteredInventaire(MouseEvent event) {
        inventaire.setStyle("-fx-background-color : #D4D4D4; -fx-background-radius: 25;");
        enlargeButton(inventaire);
    }

    @FXML
    void onMouseEnteredMessagerie(MouseEvent event) {
        messagerieAnchor.setStyle("-fx-background-color : #D4D4D4; -fx-background-radius: 25;");
        enlargeButton(messagerieAnchor);
    }

    @FXML
    void onMouseEnteredPersonnel(MouseEvent event) {
        personnel.setStyle("-fx-background-color : #D4D4D4; -fx-background-radius: 25;");
        enlargeButton(personnel);
    }

    @FXML
    void onMouseExitedInventaire(MouseEvent event) {
        inventaire.setStyle("-fx-background-color : #EDEDED; -fx-background-radius: 25;");
        restoreButtonSize(inventaire);
    }

    @FXML
    void onMouseExitedMessagerie(MouseEvent event) {
        messagerieAnchor.setStyle("-fx-background-color : #EDEDED; -fx-background-radius: 25;");
        restoreButtonSize(messagerieAnchor);
    }

    @FXML
    void onMouseExitedPersonnel(MouseEvent event) {
        personnel.setStyle("-fx-background-color : #EDEDED; -fx-background-radius: 25;");
        restoreButtonSize(personnel);
    }

    public void enlargeButton(AnchorPane anchor) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(120), anchor);
        scaleTransition.setToX(ENLARGE_FACTOR);
        scaleTransition.setToY(ENLARGE_FACTOR);
        scaleTransition.play();
    }

    public void restoreButtonSize(AnchorPane anchor) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(120), anchor);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.play();
    }
    @FXML
    void restartApplication(ActionEvent event) {

    }

    @FXML
    void switchToInventaire(MouseEvent event) throws IOException {
        cw.switchWindowPane(event,"/com/example/pfeprojet/inventaire.fxml");
    }

    @FXML
    void switchToPersonnel(MouseEvent event) throws IOException {
        cw.switchWindowPane(event,"/com/example/pfeprojet/personnel.fxml");
    }

}
