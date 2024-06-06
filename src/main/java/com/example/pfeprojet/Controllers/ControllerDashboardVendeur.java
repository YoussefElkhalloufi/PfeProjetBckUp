package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.ChangingWindows;
import com.example.pfeprojet.Entreprise.Entreprise;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;

public class ControllerDashboardVendeur {
    private static final double ENLARGE_FACTOR = 1.05;


    @FXML
    private Button aideBtn;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private AnchorPane client;

    @FXML
    private Button deconnexionBtn;

    @FXML
    private AnchorPane facturation;

    @FXML
    private Text label;

    @FXML
    private AnchorPane messagerieAnchor;

    @FXML
    private Text nbrClt;

    ChangingWindows cw = new ChangingWindows();
    private static final Entreprise e = ControllerFstWindow.getEntreprise();
    public static Entreprise getEntreprise(){
        return e;
    }

    @FXML
    void helpAndSupport(ActionEvent event) throws IOException {
        cw.switchWindow(event, "helpAndSupport.fxml");
    }

    //TODO : initiliaze ( vendeur "xxx" ........ )
    @FXML
    void onMouseEnteredClient(MouseEvent event) {
        client.setStyle("-fx-background-color : #D4D4D4; -fx-background-radius: 25;");
        enlargeButton(client);
    }

    @FXML
    void onMouseEnteredFacturation(MouseEvent event) {
        facturation.setStyle("-fx-background-color : #D4D4D4; -fx-background-radius: 25;");
        enlargeButton(facturation);
    }

    @FXML
    void onMouseEnteredMessagerie(MouseEvent event) {
        messagerieAnchor.setStyle("-fx-background-color : #D4D4D4; -fx-background-radius: 25;");
        enlargeButton(messagerieAnchor);
    }

    @FXML
    void onMouseExitedClient(MouseEvent event) {
        client.setStyle("-fx-background-color : #EDEDED; -fx-background-radius: 25;");
        restoreButtonSize(client);
    }

    @FXML
    void onMouseExitedFacturation(MouseEvent event) {
        facturation.setStyle("-fx-background-color : #EDEDED; -fx-background-radius: 25;");
        restoreButtonSize(facturation);
    }

    @FXML
    void onMouseExitedMessagerie(MouseEvent event) {
        messagerieAnchor.setStyle("-fx-background-color : #EDEDED; -fx-background-radius: 25;");
        restoreButtonSize(messagerieAnchor);
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
    void switchToClient(MouseEvent event) throws IOException {
        cw.switchWindowPane(event,"/com/example/pfeprojet/clientsEntreprise.fxml");
    }

    @FXML
    void switchToFacturation(MouseEvent event) throws IOException {
        cw.switchWindowPane(event, "/com/example/pfeprojet/facturation.fxml");
    }

}
